package com.example.androidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class InsertActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    ImageButton backBtn;
    TextView tv_date;
    TextView tv_recordTime;
    Button timeBtn;
    ImageView foodImg;
    Button imgBtn;

    ListView listView;
    ListAdapter listAdapter;
    EditText editTextName;
    EditText editTextCalories;
    Button menuPlusBtn;

    RatingBar ratingBar;
    TextView tv_ratingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        String recordTime = intent.getStringExtra("recordTime");


        tv_date = (TextView)findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);

        tv_recordTime = (TextView)findViewById(R.id.recordTime);
        tv_recordTime.setTypeface(null, Typeface.BOLD);
        tv_recordTime.setText(recordTime);

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

        //시간 설정 버튼
        timeBtn = (Button) findViewById(R.id.time);

        //이미지 불러오기
        foodImg = findViewById(R.id.foodImg);
        imgBtn = findViewById(R.id.ImageBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,0);
            }
        });

        listView = findViewById(R.id.list_view);
        editTextName = findViewById(R.id.edit_text_name);
        editTextCalories = findViewById(R.id.edit_text_calories);
        menuPlusBtn = findViewById(R.id.btn_add);

        listAdapter = new ListAdapter();
        //listAdapter.addItem(new MenuListItem("피자", "800"));
        listView.setAdapter(listAdapter);

        //메뉴 클릭시 토스트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuListItem item = (MenuListItem) listAdapter.getItem(position);
                Toast.makeText(getApplication(), item.getMenuName(), Toast.LENGTH_SHORT).show();
            }
        });

        //추가하기 버튼
        menuPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String calories = editTextCalories.getText().toString();

                listAdapter.addItem(new MenuListItem(name, calories));
                listAdapter.notifyDataSetChanged();
            }
        });

        ratingBar = findViewById(R.id.ratingBar);
        tv_ratingbar = findViewById(R.id.tv_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_ratingbar.setText(String.valueOf(rating));
            }
        });
    }


    // 리스트 어댑터 연결
    class ListAdapter extends BaseAdapter {

        ArrayList<MenuListItem> listItem = new ArrayList<>();

        public void addItem(MenuListItem item){
            listItem.add(item);
        }

        @Override
        public int getCount() {
            return listItem.size();
        }

        @Override
        public Object getItem(int position) {
            return listItem.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 화면에 보이지 않는 뷰를 다시 재사용할 수 있게 하여 데이터를 모두 생성할 필요 없게 한다.
            MenuListItemView listItemView = null;
            if(convertView == null)
                listItemView = new MenuListItemView(getApplicationContext());
            else
                listItemView = (MenuListItemView) convertView;  // convertView: 이전에 썼던 뷰

            MenuListItem item = listItem.get(position);

            listItemView.setName(item.getMenuName());
            listItemView.setCal(item.getCalories());

            return listItemView;
        }

    }




    //타임 다이얼로그 보여주기
    public void onClickTime(View view){
        DialogFragment df = new TimPickerDialog();
        df.show(getSupportFragmentManager(), "Time Picker");
    }


    //현재 시간 디폴트 설정과 설정한 시간 셋팅
    public void onTimeSet(TimePicker timePicker, int hour, int minute){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        SimpleDateFormat sdf = new SimpleDateFormat("KK시 mm분");
        String time = sdf.format(cal.getTime());
        timeBtn.setText(time);
    }

    // 이미지 불러오기 셋팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                Glide.with(getApplicationContext()).load(data.getData()).override(500,500).into(foodImg);
            }
        }
    }



}