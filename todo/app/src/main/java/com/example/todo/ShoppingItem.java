package com.example.todo;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

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
