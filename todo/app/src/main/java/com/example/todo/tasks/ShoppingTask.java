package com.example.todo.tasks;

import com.example.todo.ShoppingItem;

import java.util.ArrayList;

public class ShoppingTask extends Task {
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingTask(int ID, String title, String category) {
        super(ID, title, category);
        shoppingItems = new ArrayList<>();
    }

    public ArrayList<ShoppingItem> getItems(){
        return shoppingItems;
    }

    public int getLength() {
        return shoppingItems.size();
    }

    public void addItem(ShoppingItem newItem) {
        shoppingItems.add(newItem);
    }

    public String getType() {
        return "SHOPPING";
    }
}
