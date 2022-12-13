package com.example.whatwatch;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import io.paperdb.Paper;


public class MainActivity extends AppCompatActivity {
    String folderToSave = Environment.getExternalStorageDirectory().toString();
    Random random;
    TextView mainText;
    TextView addText;
    Button addButton;
    Button JustwhatchButton;
    TextView countText;
    ArrayList<String> arrayList = new ArrayList<String>();
    ImageView imageView;
    ArrayList<String> movieList;
    StorageReference storageRef;
    private Uri uploadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button = findViewById(R.id.button);

        Paper.init(this);

        mainText = findViewById(R.id.main_text);
        random = new Random();
        storageRef = FirebaseStorage.getInstance().getReference("ImageDB");
        JustwhatchButton = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView);
        countText = findViewById(R.id.countText);
        addText = findViewById(R.id.addText);
        addButton = findViewById(R.id.addButton);

        arrayList.clear();
        movieList = Paper.book().read("arrayList");
        if (movieList != null){arrayList.addAll(0,movieList);}

        System.out.println("создание"+movieList);
        System.out.println("создание"+arrayList);



        JustwhatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((arrayList!= null)&(movieList!= null)){
                    if((arrayList.size() > movieList.size())){
                        arrayList.clear();
                        movieList.addAll(0,arrayList);
                    }
                    if((arrayList.size() < movieList.size())){
                        movieList.clear();
                        arrayList.addAll(0,movieList);

                    }
                }
                if ((movieList.contains(mainText.getText().toString()))&(arrayList.contains(mainText.getText().toString()))){
                    arrayList.remove(arrayList.indexOf(mainText.getText().toString()));
                    Paper.book().write("arrayList", arrayList);
                }
                movieList = Paper.book().read("arrayList");
                mainText.setText(""+movieList.get(random.nextInt(movieList.size())));
                countText.setText(""+movieList.size());
                System.out.println("смотрено"+movieList);
                System.out.println("смотрено"+arrayList);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((arrayList!= null)&(movieList!= null)){
                    if((arrayList.size() > movieList.size())){
                        arrayList.clear();
                        movieList.addAll(0,arrayList);
                    }
                    if((arrayList.size() < movieList.size())){
                        movieList.clear();
                        arrayList.addAll(0,movieList);

                    }
                }


                arrayList.add(addText.getText().toString());
                Paper.book().write("arrayList", arrayList);
                System.out.println("добавить"+movieList);
                System.out.println("добавить"+arrayList);

            }
        });

        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                movieList = Paper.book().read("arrayList");
                System.out.println("центр"+movieList);
                System.out.println("центр"+arrayList);
                mainText.setText(""+movieList.get(random.nextInt(movieList.size())));
                countText.setText(""+movieList.size());
                storageRef.child(mainText.getText().toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(imageView);
                        System.out.println((uri));

                    }
                });
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && data != null && data.getData() != null){
            if(resultCode == RESULT_OK){
                Log.d("MyLog","Image URI : "+ data.getData());
                imageView.setImageURI(data.getData());
                upLoad();
            }
        }
    }

    private void getImage(){
        Intent intentChooser = new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    private void upLoad(){
        Bitmap bitmap =((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference myRef = storageRef.child((String) mainText.getText());
        UploadTask up = myRef.putBytes(byteArray);
        Task<Uri> task = up.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return myRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
            }
        });
    }

}