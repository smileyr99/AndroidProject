package com.example.androidproject;

import static com.example.androidproject.FoodContentProvider.WHEN;
import static com.example.androidproject.FoodContentProvider._DATE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    ImageButton backBtn;
    String date;
    String when;
    String Latitude;
    String Longitude;

    TextView tv_date;
    TextView tv_when;
    TextView tv_time;
    ImageView foodImg;
    TextView tv_score;
    TextView tv_memo;
    TextView tv_location;
    TextView tv_menu1;
    TextView tv_menu2;
    TextView tv_menu3;
    TextView tv_total_calorie;

    RatingBar ratingBar;

    ImageButton locationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        when = intent.getStringExtra("when");

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(date);

        tv_when = (TextView) findViewById(R.id.tv_when);
        tv_when.setText(when);


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

        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_score = (TextView) findViewById(R.id.tv_score);
        foodImg = (ImageView) findViewById(R.id.foodImg);
        tv_memo = (TextView) findViewById(R.id.tv_memo);
        tv_memo.setMovementMethod(new ScrollingMovementMethod());
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_menu1 = (TextView) findViewById(R.id.tv_menu1);
        tv_menu2 = (TextView) findViewById(R.id.tv_menu2);
        tv_menu3 = (TextView) findViewById(R.id.tv_menu3);
        ratingBar = findViewById(R.id.ratingBar);
        tv_total_calorie = (TextView) findViewById(R.id.tv_total_calorie);
        String[] columns = new String[]{"_time", "image", "score", "location", "location_x","location_y","memo", "total_calorie", "menu_name1", "menu_name2", "menu_name3", "amount1", "amount2", "amount3"};
        String where = _DATE + "=" + "\"" + date + "\"" + " and " + WHEN + "=" + "\"" + when + "\"";
        Cursor c = getContentResolver().query(FoodContentProvider.CONTENT_URI, columns, where, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                tv_time.setText(c.getString(0));
                String image = c.getString(1);
                String score = c.getString(2);
                tv_score.setText(score);
                ratingBar.setRating(Float.parseFloat(score));
                String location= c.getString(3);
                tv_location.setText(location);
                Latitude = c.getString(4);
                Longitude =  c.getString(5);
                tv_memo.setText(c.getString(6));
                tv_total_calorie.setText(c.getString(7) + " kcal");
                tv_menu1.setText(c.getString(8) + "    " + c.getString(11) + "g/개");
                if (c.getString(9) != null) {
                    tv_menu2.setText(c.getString(9) + "    " + c.getString(12) + "g/개");
                }
                if (c.getString(10) != null) {
                    tv_menu3.setText(c.getString(10) + "    " + c.getString(13) + "g/개");
                }

                Glide.with(getApplicationContext()).load(image).override(500, 500).into(foodImg);
            }
            c.close();
        }

        // googleMap API로 가기
        locationBtn = (ImageButton) findViewById(R.id.locationBtn);
        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, MapsViewActivity.class);
                intent.putExtra("latitude", Latitude);
                intent.putExtra("longitude", Longitude);
                intent.putExtra("location", tv_location.getText().toString());
                startActivityForResult(intent,2);
            }
        });
    }

    // 구글맵에 위도 경도 받아서 위치 띄우기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
            }
        }

    }
}