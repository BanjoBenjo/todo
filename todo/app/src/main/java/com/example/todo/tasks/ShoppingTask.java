package com.example.todo.tasks;

import com.example.todo.ShoppingItem;

import java.util.ArrayList;

public class ShoppingTask extends Task {
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingTask(int ID, String title) {
        super(ID, title);
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

    public String getNotes() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ShoppingItem shoppingItem: shoppingItems){
            stringBuilder.append(shoppingItem.toString());
            stringBuilder.append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
}
