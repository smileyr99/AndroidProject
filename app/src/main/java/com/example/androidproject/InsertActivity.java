package com.example.androidproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;


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
    ImageButton mapBtn;

    ListView listView;
    ListAdapter listAdapter;
    EditText editTextName;
    EditText editTextCalories;
    String[] menu_name = new String[3];
    String[] amount = new String[3];
    ImageButton menuPlusBtn;

    RatingBar ratingBar;
    TextView tv_ratingbar;

    EditText editMemo;
    String total_calorie;

    String Latitude;
    String Longitude;
    String location;
    TextView tv_location;
    MenuListItem item;
    ArrayList<MenuListItem> tmp_menuListItems;


    Button insertBtn;
    FoodDataList[] foodData = new FoodDataList[20];
    ArrayList<FoodDataList> foodDataLists = new ArrayList<>();
//--------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);


        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        recordTime = intent.getStringExtra("recordTime");


        tv_date = (TextView) findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);

        tv_recordTime = (TextView) findViewById(R.id.recordTime);
        tv_recordTime.setTypeface(null, Typeface.BOLD);
        tv_recordTime.setText(recordTime);


        //??????????????? ????????????
        foodData[0] = new FoodDataList("????????????", "300", "564");
        foodData[1] = new FoodDataList("??????", "1", "970");
        foodData[2] = new FoodDataList("???", "1", "300");
        foodData[3] = new FoodDataList("???????????????", "1", "89");
        foodData[4] = new FoodDataList("???????????????", "200", "508");
        foodData[5] = new FoodDataList("?????????", "200", "662");
        foodData[6] = new FoodDataList("??????", "1", "780");
        foodData[7] = new FoodDataList("??????", "1", "1161");
        foodData[8] = new FoodDataList("??????", "1", "2");
        foodData[9] = new FoodDataList("??????", "1", "153");
        foodData[10] = new FoodDataList("???", "1", "330");
        foodData[11] = new FoodDataList("??????", "1", "974");
        foodData[12] = new FoodDataList("?????????", "200", "480");
        foodData[13] = new FoodDataList("?????????", "1", "320");
        foodData[14] = new FoodDataList("??????", "1", "550");
        foodData[15] = new FoodDataList("?????????", "1", "642");
        foodData[16] = new FoodDataList("??????", "300", "639");
        foodData[17] = new FoodDataList("???????????????", "1", "300");
        foodData[18] = new FoodDataList("????????????", "300", "495");
        foodData[19] = new FoodDataList("????????????", "100", "165");

        foodDataLists.addAll(Arrays.asList(foodData).subList(0, 20));


        // foodList??? ??????
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        //?????? ?????? ??????
        timeBtn = (Button) findViewById(R.id.time);

        //????????? ????????????
        foodImg = findViewById(R.id.foodImg);
        imgBtn = findViewById(R.id.ImageBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
            }
        });

        listView = findViewById(R.id.list_view);
        editTextName = findViewById(R.id.edit_text_name);
        editTextCalories = findViewById(R.id.edit_text_calories);
        menuPlusBtn = findViewById(R.id.btn_add);

        listAdapter = new ListAdapter();
        //listAdapter.addItem(new MenuListItem("??????", "800"));
        listView.setAdapter(listAdapter);

        //TODO ?????? ?????????????????? ??????(??????)-> ???????????? ???????????????
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = (MenuListItem) listAdapter.getItem(position);
                Toast.makeText(getApplication(), listAdapter.getItemId(position) + " ????????????", Toast.LENGTH_SHORT).show();
            }
        });

        //?????? ?????? ????????? ?????? ?????? + ???????????? ?????????
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int count;
                count = listAdapter.getCount();
                item = (MenuListItem) listAdapter.getItem(position);
                if (count > 0) {
                    Log.v("test", "?????? ??????");
                    // ????????? ??????
                    listAdapter.delItem(item);
                    Log.v("test", "??????");
                    // listview ?????? ?????????.
                    listView.clearChoices();
                    Log.v("test", "?????????");
                    // listview ??????.
                    listAdapter.notifyDataSetChanged();
                    Log.v("test", "?????? ??????3");
                }
                Toast.makeText(getApplication(), item.getMenuName() + " ?????? ??????", Toast.LENGTH_SHORT).show();

                return true;
            }
        });


        // ?????? ???????????? ??????
        menuPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String calories = editTextCalories.getText().toString();

                listAdapter.addItem(new MenuListItem(name, calories));
                listAdapter.notifyDataSetChanged();
            }
        });

        // ??????
        ratingBar = findViewById(R.id.ratingBar);
        tv_ratingbar = findViewById(R.id.tv_ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tv_ratingbar.setText(String.valueOf(rating));
            }
        });

        tv_location = (TextView) findViewById(R.id.location);
        // googleMap API??? ??????
        mapBtn = (ImageButton) findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InsertActivity.this, MapsActivity.class);
                startActivityForResult(intent,1);
            }
        });

        // ????????????
        editMemo = findViewById(R.id.edit_memo);


        // ?????? ??? foodList??? ??????
        insertBtn = (Button) findViewById(R.id.insertBtn);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count, total = 0;
                count = listAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    item = (MenuListItem) listAdapter.getItem(i);
                    // ????????? ??????
                    for (FoodDataList foodData : foodDataLists) {
                        if (Objects.equals(item.getMenuName(), foodData.getMenu()) && Objects.equals(item.getAmount(), foodData.getAmount())) {
                            menu_name[i] = item.getMenuName();
                            amount[i] = item.getAmount();
                            total += Integer.parseInt(foodData.getCalorie());
                        }
                    }
                }

                total_calorie = Integer.toString(total);

                ContentValues addValues = new ContentValues();
                addValues.put(FoodContentProvider._DATE, date);
                addValues.put(FoodContentProvider.WHEN, recordTime);
                addValues.put(FoodContentProvider._TIME, time);
                addValues.put(FoodContentProvider.IMAGE, ImgUri.toString());
                addValues.put(FoodContentProvider.LOCATION, location);
                addValues.put(FoodContentProvider.LOCATION_X, Latitude);
                addValues.put(FoodContentProvider.LOCATION_Y, Longitude);
                addValues.put(FoodContentProvider.MENU_NAME1, menu_name[0]);
                addValues.put(FoodContentProvider.MENU_NAME2, menu_name[1]);
                addValues.put(FoodContentProvider.MENU_NAME3, menu_name[2]);
                addValues.put(FoodContentProvider.AMOUNT1, amount[0]);
                addValues.put(FoodContentProvider.AMOUNT2, amount[1]);
                addValues.put(FoodContentProvider.AMOUNT3, amount[2]);
                addValues.put(FoodContentProvider.SCORE, tv_ratingbar.getText().toString());
                addValues.put(FoodContentProvider.MEMO, editMemo.getText().toString());
                addValues.put(FoodContentProvider.TOTAL_CALORIE, total_calorie);
                getContentResolver().insert(FoodContentProvider.CONTENT_URI, addValues);
                Intent intent = new Intent(getApplicationContext(), FoodlistActivity.class);
                intent.putExtra("date", date);
                Toast.makeText(getBaseContext(), "?????????????????????.", Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

    }

//--------------------------------------------------------------------------------------------------------------------------------------------

    // ????????? ????????? ??????
    class ListAdapter extends BaseAdapter {

        ArrayList<MenuListItem> listItem = new ArrayList<>();

        public void addItem(MenuListItem item) {
            listItem.add(item);
        }

        public void delItem(Object o) {
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
            // ????????? ????????? ?????? ?????? ?????? ???????????? ??? ?????? ?????? ???????????? ?????? ????????? ?????? ?????? ??????.
            MenuListItemView listItemView = null;
            if (convertView == null)
                listItemView = new MenuListItemView(getApplicationContext());
            else
                listItemView = (MenuListItemView) convertView;  // convertView: ????????? ?????? ???

            MenuListItem item = listItem.get(position);

            listItemView.setName(item.getMenuName());
            listItemView.setAmount(item.getAmount());

            return listItemView;
        }

    }


    //?????? ??????????????? ????????????
    public void onClickTime(View view) {
        DialogFragment df = new TimPickerDialog();
        df.show(getSupportFragmentManager(), "Time Picker");
    }


    //?????? ?????? ????????? ????????? ????????? ?????? ??????
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        timePicker.setIs24HourView(true);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        SimpleDateFormat sdf;
        if (hour > 12) {
            hour -= 12;
             sdf = new SimpleDateFormat("?????? KK??? mm???");
        } else {
             sdf = new SimpleDateFormat("?????? KK??? mm???");
        }
        time = sdf.format(cal.getTime());
        timeBtn.setText(time);
    }

    // ????????? ???????????? & ???????????? ????????? ????????????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Glide.with(getApplicationContext()).load(data.getData()).override(500, 500).into(foodImg);
                ImgUri = data.getData();
            }
        }else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Latitude = data.getStringExtra("latitude");
                Longitude = data.getStringExtra("longitude");
                location = data.getStringExtra("locationSearch");
                tv_location.setText(location);
                System.out.println(location);
            }
        }
    }


}