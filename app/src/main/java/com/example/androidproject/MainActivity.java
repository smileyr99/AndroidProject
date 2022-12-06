package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//TODO github readme 만들기 -> 보고서 내용 대략 적으로 적어 넣기 + 데이터 베이스 
public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView tv_date;
    TextView today;
    Button foodListBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // xml 연결
        calendar = findViewById(R.id.calendar);
        tv_date = findViewById(R.id.foodlistbtn);

        // Calendar 클릭 이벤트
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                tv_date.setText(year + "년 " + (month + 1) + "월 " + day + "일");
           }
        });

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd일");
        today = findViewById(R.id.today);
        today.setText(format.format(Calendar.getInstance().getTime()));


        // FoodlistActivity로 화면 전환
        foodListBtn = (Button) findViewById(R.id.foodlistbtn);
        foodListBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", tv_date.getText().toString());
                startActivity(intent);
            }
        });

    }


}