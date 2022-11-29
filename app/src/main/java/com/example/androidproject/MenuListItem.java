package com.example.androidproject;

public class MenuListItem {
    private String MenuName;
    private String amount;

    public MenuListItem(String MenuName,  String amount) {
        this.MenuName  = MenuName;
        this.amount = amount;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
