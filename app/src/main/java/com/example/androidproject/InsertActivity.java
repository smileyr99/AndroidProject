package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class InsertActivity extends AppCompatActivity {

    ImageButton backBtn;
    TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        tv_date = (TextView)findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);

        // foodList로 가기
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }
}