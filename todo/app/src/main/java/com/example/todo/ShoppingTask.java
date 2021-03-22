package com.example.todo;

import android.util.Log;

import java.util.ArrayList;

public class ShoppingTask extends Task{
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingTask(int ID, String title, String category) {
        super(ID, title, category);
        shoppingItems = new ArrayList<>();
    }

    public ArrayList<ShoppingItem> getShoppingItems(){
        return shoppingItems;
    }

    public void addItem(ShoppingItem newItem) {
        shoppingItems.add(newItem);
        Log.wtf("DEBUG", "added item: " + newItem.toString());
    }

    public ArrayList<ShoppingItem> getItems() {
        return shoppingItems;
    }

    public String getType(){
        return "SHOPPING";
    }

}
