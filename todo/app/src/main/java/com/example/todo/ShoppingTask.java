package com.example.todo;

import android.util.Log;

import java.util.ArrayList;

public class ShoppingTask extends Task{
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingTask(int ID, String title, String category) {
        super(ID, title, category);
        shoppingItems = new ArrayList<>();
        Log.wtf("ShoppingTask", "CREATION");
    }

    public ArrayList<ShoppingItem> getItems(){
        return shoppingItems;
    }

    public int getLength() {
        return shoppingItems.size();
    }

    public void addItem(ShoppingItem newItem) {
        shoppingItems.add(newItem);
        Log.wtf("DEBUG", "added: " + newItem.toString() + " length: " + shoppingItems.size() + " task: " + getID());
    }

    public String getType() {
        return "SHOPPING";
    }
}
