package com.example.todo;

import android.util.Log;

public class ShoppingItem {
    private boolean checked;
    private String name;

    public ShoppingItem(String newName) {
        checked = false;
        name = newName;
    }

    public boolean isChecked() {
        return checked;
    }

    public String toString() {
        return name;
    }

    public void toggleCheck() {
        checked = !checked;
    }
}
