package com.example.androidproject;

public class MenuListItem {
    private String MenuName;
    private String calories;

    public MenuListItem(String MenuName,  String calories) {
        this.MenuName  = MenuName;
        this.calories = calories;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getCalories() {
        return calories + "kcal";
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
