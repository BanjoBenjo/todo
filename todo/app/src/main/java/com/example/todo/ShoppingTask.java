package com.example.todo;

import java.util.ArrayList;

public class ShoppingTask extends Task{
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingTask(int ID, String title, String category) {
        super(ID, title, category);
    }

    public ArrayList<ShoppingItem> getShoppingItems(){
        return this.shoppingItems;
    }
    public String getType(){ return "SHOPPING"; }

}
