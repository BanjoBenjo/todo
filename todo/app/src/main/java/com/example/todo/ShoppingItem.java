package com.example.todo;

import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

public class ShoppingItem {
    private boolean checked;
    private int quantity;
    private String name;

    public ShoppingItem(int newQuantity, String newName) {
        checked = false;
        quantity = newQuantity;
        name = newName;
    }

    public boolean isChecked() {
        return checked;
    }

    public String toString() {
        return quantity + "\t" + name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void toggleCheck() {
        checked = !checked;
    }
}
