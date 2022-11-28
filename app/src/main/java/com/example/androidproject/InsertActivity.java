package com.example.androidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

//--------------------------------------------------------------------------------------------------------------------------------------------

public class InsertActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    String date;
    String recordTime;

    ImageButton backBtn;
    TextView tv_date;
    TextView tv_recordTime;
    Button timeBtn;
    String time;
    ImageView foodImg;
    Uri ImgUri;
    Button imgBtn;

    ListView listView;
    ListAdapter listAdapter;
    EditText editTextName;
    EditText editTextCalories;
    String[] menu_name = new String[3];
    String[] calorie = new String[3];
    Button menuPlusBtn;

    RatingBar ratingBar;
    TextView tv_ratingbar;

    EditText editMemo;
    String total_calorie;

    Button insertBtn;

    SQLiteDatabase msqLiteDatabase;

//--------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        recordTime = intent.getStringExtra("recordTime");


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

        //메뉴 클릭시 삭제 기능 + 삭제완료 토스트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int count, checked;
                count = listAdapter.getCount();
                MenuListItem item = (MenuListItem) listAdapter.getItem(position);
                Toast.makeText(getApplication(), listAdapter.getItemId(position)+" 아이디값", Toast.LENGTH_SHORT).show();

            }
        });

        //메뉴 클릭시 삭제 기능 + 삭제완료 토스트
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int count, checked;
                count = listAdapter.getCount();
                MenuListItem item = (MenuListItem) listAdapter.getItem(position);
                if (count > 0){
                    Log.v("test", "삭제 점검");
                        // 아이템 삭제
                        listAdapter.delItem(item) ;
                        Log.v("test", "삭제");
                        // listview 선택 초기화.
                        listView.clearChoices();
                        Log.v("test", "뷰리셋");
                        // listview 갱신.
                        listAdapter.notifyDataSetChanged();
                        Log.v("test", "삭제 점검3");
                }
                Toast.makeText(getApplication(), item.getMenuName()+" 삭제 완료", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        // 메뉴 추가하기 버튼
        menuPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String calories = editTextCalories.getText().toString();

                listAdapter.addItem(new MenuListItem(name, calories));
                listAdapter.notifyDataSetChanged();
            }
        });

        // 평점
        ratingBar = findViewById(R.id.ratingBar);
        tv_ratingbar = findViewById(R.id.tv_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_ratingbar.setText(String.valueOf(rating));
            }
        });

        editMemo = findViewById(R.id.edit_memo);


        // 저장 후 foodList로 가기
        insertBtn = (Button) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count, total = 0;
                count = listAdapter.getCount();
                for(int i = 0; i<count; i++){
                    MenuListItem item = (MenuListItem) listAdapter.getItem(i);
                    menu_name[i] = item.getMenuName();
                    calorie[i] = item.getCalories();
                    total += Integer.parseInt(calorie[i]);
                }

                total_calorie = Integer.toString(total);

                ContentValues addValues = new ContentValues();
                addValues.put(FoodContentProvider._DATE, date);
                addValues.put(FoodContentProvider.WHEN, recordTime);
                addValues.put(FoodContentProvider._TIME, time);
                addValues.put(FoodContentProvider.IMAGE, ImgUri.toString());
                addValues.put(FoodContentProvider.MENU_NAME1, menu_name[0]);
                addValues.put(FoodContentProvider.MENU_NAME2, menu_name[1]);
                addValues.put(FoodContentProvider.MENU_NAME3, menu_name[2]);
                addValues.put(FoodContentProvider.CALORIE1, calorie[0]);
                addValues.put(FoodContentProvider.CALORIE2, calorie[1]);
                addValues.put(FoodContentProvider.CALORIE3, calorie[2]);
                addValues.put(FoodContentProvider.SCORE, tv_ratingbar.getText().toString());
                addValues.put(FoodContentProvider.MEMO, editMemo.getText().toString());
                addValues.put(FoodContentProvider.TOTAL_CALORIE, total_calorie);
                getContentResolver().insert(FoodContentProvider.CONTENT_URI, addValues);
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                Toast.makeText(getBaseContext(),"등록되었습니다.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    // 리스트 어댑터 연결
    class ListAdapter extends BaseAdapter {

        ArrayList<MenuListItem> listItem = new ArrayList<>();

        public void addItem(MenuListItem item){
            listItem.add(item);
        }

        public void delItem(Object o){
            listItem.remove(o);
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
        time = sdf.format(cal.getTime());
        timeBtn.setText(time);
    }

    // 이미지 불러오기 셋팅
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                Glide.with(getApplicationContext()).load(data.getData()).override(500,500).into(foodImg);
                ImgUri = data.getData();
            }
        }
    }


}