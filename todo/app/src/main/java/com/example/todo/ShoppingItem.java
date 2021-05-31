package com.example.todo;

import android.util.Log;

import com.example.todo.tasks.Task;

public class ShoppingItem implements Comparable<ShoppingItem>{
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

    public void setChecked(Boolean check) { checked = check; }
    public void setName(String newName) {
        name = newName;
    }
    public String getName(){
        return name;
    }

    @Override
    public int compareTo(ShoppingItem i) {
        return Boolean.compare(this.checked, i.isChecked());
    }
}
