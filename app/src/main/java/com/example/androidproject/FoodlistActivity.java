package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class FoodlistActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageButton insertBtn;
    TextView tv_date;
    TextView tv_calorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String calorie = intent.getStringExtra("calorie");

        tv_date = (TextView)findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);

        tv_calorie = (TextView)findViewById(R.id.calorie);
        tv_calorie.setTypeface(null, Typeface.BOLD);
        tv_calorie.setText("하루 총 칼로리 " + calorie + " kcal");


        insertBtn = (ImageButton) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InsertPopupActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        // 메인으로 가기
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}