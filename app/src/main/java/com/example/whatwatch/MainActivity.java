package com.example.whatwatch;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.BufferedInputStream;
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

    ArrayList<String> movieList;

    static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });

        Paper.init(this);
        mainText = findViewById(R.id.main_text);
        random = new Random();
        JustwhatchButton = findViewById(R.id.button3);
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
                getImageBitmap(folderToSave+mainText.getText()+".jpg");
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        Bitmap bitmap = null;
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        switch(requestCode) {
            case GALLERY_REQUEST:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageBitmap(bitmap);

                    SavePicture(imageView,folderToSave);
                }
        }
    }


    private String SavePicture(ImageView iv, String folderToSave)
    {
        OutputStream fOut = null;

        try {
            File file = new File(folderToSave, mainText.getText()+".jpg"); // создать уникальное имя для файла основываясь на дате сохранения
            fOut = new FileOutputStream(file);

            Bitmap bitmap = ((BitmapDrawable) iv.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // сохранять картинку в jpeg-формате с 85% сжатия.
            fOut.flush();
            fOut.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(),  file.getName()); // регистрация в фотоальбоме
        }
        catch (Exception e) // здесь необходим блок отслеживания реальных ошибок и исключений, общий Exception приведен в качестве примера
        {
            return e.getMessage();
        }
        return "";
    }

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }
}