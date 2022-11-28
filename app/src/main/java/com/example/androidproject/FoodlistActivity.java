package com.example.androidproject;

import static com.example.androidproject.FoodContentProvider.WHEN;
import static com.example.androidproject.FoodContentProvider._DATE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodlistActivity extends AppCompatActivity {

    ImageButton backBtn;
    ImageButton insertBtn;
    TextView tv_date;
    TextView tv_calorie;
    String total_calorie;
    String date;
    String when;
    String calorie;
    ListView lvList;
    ListViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        calorie = intent.getStringExtra("calorie");

        tv_date = (TextView)findViewById(R.id.date);
        tv_date.setTypeface(null, Typeface.BOLD);
        tv_date.setText(date);

        tv_calorie = (TextView)findViewById(R.id.calorie);
        tv_calorie.setTypeface(null, Typeface.BOLD);

        lvList = (ListView)findViewById(R.id.lv_list);
        displayList();




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

    class ListViewAdapter extends BaseAdapter {

        ArrayList<DayListItem> list = new ArrayList<DayListItem>();

        @Override
        public int getCount() {
            return list.size(); //그냥 배열의 크기를 반환하면 됨
        }

        @Override
        public Object getItem(int i) {
            return list.get(i); //배열에 아이템을 현재 위치값을 넣어 가져옴
        }

        @Override
        public long getItemId(int i) {
            return i; //그냥 위치값을 반환해도 되지만 원한다면 아이템의 num 을 반환해도 된다.
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            final Context context = viewGroup.getContext();

            //리스트뷰에 아이템이 인플레이트 되어있는지 확인한후
            //아이템이 없다면 아래처럼 아이템 레이아웃을 인플레이트 하고 view객체에 담는다.
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.item_listview, viewGroup, false);
            }

            //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
            TextView tv_when = (TextView) view.findViewById(R.id.tv_when);
            TextView tv_total_calorie = (TextView) view.findViewById(R.id.tv_total_calorie);

            //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
            DayListItem listdata = list.get(i);

            //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
            tv_when.setText(listdata.getWhen_name());
            tv_total_calorie.setText(listdata.getTotal_calorie());

            return view;
        }

        //ArrayList로 선언된 list 변수에 목록을 채워주기 위함
        public void addItemToList(String when, String calorie){
            DayListItem listdata = new DayListItem();

            listdata.setWhen_name(when);
            listdata.setTotal_calorie(calorie);

            //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
            list.add(listdata);

        }
    }

    void displayList(){
        String[] columns = new String[]{"_when", "total_calorie"};
        String where = _DATE + "=" +  "\"" + date + "\"";
        int day_total_cal = 0;
        Cursor c = getContentResolver().query(FoodContentProvider.CONTENT_URI, columns,  where, null, null, null);
        if (c != null) {
            ListViewAdapter adapter = new ListViewAdapter();
            while(c.moveToNext()){
                day_total_cal += Integer.parseInt(c.getString(1));
                //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
                adapter.addItemToList(c.getString(0),c.getString(1));
            }
            total_calorie = Integer.toString(day_total_cal);
            tv_calorie.setText("하루 총 칼로리 " +  total_calorie + " kcal");
            lvList.setAdapter(adapter);
            c.close();

            //메뉴 클릭시 상세보기로 이동
            lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DayListItem item = (DayListItem) adapter.getItem(position);
                    when = item.getWhen_name();
                    Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                    intent.putExtra("date", date);
                    intent.putExtra("when", when);
                    startActivity(intent);
                }
            });
        }
    }

}