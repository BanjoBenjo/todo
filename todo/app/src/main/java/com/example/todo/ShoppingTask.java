package com.example.todo;

import java.util.ArrayList;

public class ShoppingTask extends Task{
    private ArrayList<ShoppingItem> shoppingItems;

    public ShoppingTask(String title, TaskCategory category) {
        super(title);
    }

}
