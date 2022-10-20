package com.example.whatwatch;

import androidx.appcompat.app.AppCompatActivity;



import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.Random;


import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Random random;
    TextView mainText;
    TextView addText;
    Button addButton;
    Button JustwhatchButton;
    TextView countText;
    ArrayList<String> arrayList = new ArrayList<String>();

    ArrayList<String> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);
        mainText = findViewById(R.id.main_text);
        random = new Random();
        JustwhatchButton = findViewById(R.id.button3);
        countText = findViewById(R.id.countText);
        addText = findViewById(R.id.addText);
        addButton = findViewById(R.id.addButton);

        arrayList.clear();
        movieList = Paper.book().read("arrayList");
        arrayList.addAll(0,movieList);
//            if((arrayList.size() < movieList.size())){
//                movieList.clear();
//                movieList.addAll(0,arrayList);
//            }
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

//                if ((arrayList!= null)&(movieList!= null)){
//                    if((arrayList.size() > movieList.size())){
//                        arrayList.clear();
//                        arrayList.addAll(0,movieList);
//                    }
//                    if((arrayList.size() < movieList.size())){
//                        movieList.clear();
//                        movieList.addAll(0,arrayList);
//                    }
//                }

                movieList = Paper.book().read("arrayList");
                System.out.println("центр"+movieList);
                System.out.println("центр"+arrayList);
                mainText.setText(""+movieList.get(random.nextInt(movieList.size())));
                countText.setText(""+movieList.size());
            }
        });




//        arrayList.add(getString(R.string.text_000));
//        arrayList.add(getString(R.string.text_001));
//        arrayList.add(getString(R.string.text_002));
//        arrayList.add(getString(R.string.text_003));
//        arrayList.add(getString(R.string.text_004));
//        arrayList.add(getString(R.string.text_005));
//        arrayList.add(getString(R.string.text_006));
//        arrayList.add(getString(R.string.text_007));
//        arrayList.add(getString(R.string.text_008));
//        arrayList.add(getString(R.string.text_009));
//        arrayList.add(getString(R.string.text_010));
//        arrayList.add(getString(R.string.text_011));
//        arrayList.add(getString(R.string.text_012));
//        arrayList.add(getString(R.string.text_013));
//        arrayList.add(getString(R.string.text_014));
//        arrayList.add(getString(R.string.text_015));
//        arrayList.add(getString(R.string.text_016));
//        arrayList.add(getString(R.string.text_017));
//        arrayList.add(getString(R.string.text_018));
//        arrayList.add(getString(R.string.text_019));
//        arrayList.add(getString(R.string.text_020));
//        arrayList.add(getString(R.string.text_022));
//        arrayList.add(getString(R.string.text_023));

    }
}