package com.example.androidproject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MenuListItemView extends LinearLayout {

    TextView tx_menuName;
    TextView tx_calories;

    public MenuListItemView(Context context) {
        super(context);
        init(context);
    }

    public MenuListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.menu_list_item, this, true);

        tx_menuName = findViewById(R.id.menuName);
        tx_calories = findViewById(R.id.calories);
    }


    public void setName(String str){
        tx_menuName.setText(str);
    }

    public void setCal(String str){
        tx_calories.setText(str + "kcal");
    }

}
