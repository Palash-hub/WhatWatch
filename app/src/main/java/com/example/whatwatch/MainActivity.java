package com.example.whatwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random;
    TextView mainText;
    Button whatchButton;
    TextView countText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainText = findViewById(R.id.main_text);
        random= new Random();
        whatchButton = findViewById(R.id.button3);
        countText = findViewById(R.id.countText);

        ArrayList arrayList = new ArrayList<>();



        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainText.setText(""+arrayList.get(random.nextInt(arrayList.size())));
                countText.setText(""+arrayList.size());
            }
        });

        whatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayList.remove(arrayList.indexOf(mainText.getText()));
            }
        });



        arrayList.add(getString(R.string.text_000));
        arrayList.add(getString(R.string.text_001));
        arrayList.add(getString(R.string.text_002));
        arrayList.add(getString(R.string.text_003));
        arrayList.add(getString(R.string.text_004));
        arrayList.add(getString(R.string.text_005));
        arrayList.add(getString(R.string.text_006));
        arrayList.add(getString(R.string.text_007));
        arrayList.add(getString(R.string.text_008));
        arrayList.add(getString(R.string.text_009));
        arrayList.add(getString(R.string.text_010));
        arrayList.add(getString(R.string.text_011));
        arrayList.add(getString(R.string.text_012));
        arrayList.add(getString(R.string.text_013));
        arrayList.add(getString(R.string.text_014));
        arrayList.add(getString(R.string.text_015));
        arrayList.add(getString(R.string.text_016));
        arrayList.add(getString(R.string.text_017));
        arrayList.add(getString(R.string.text_018));
        arrayList.add(getString(R.string.text_019));
        arrayList.add(getString(R.string.text_020));
        arrayList.add(getString(R.string.text_022));
        arrayList.add(getString(R.string.text_023));

    }
}