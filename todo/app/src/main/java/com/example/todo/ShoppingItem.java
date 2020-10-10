package com.example.todo;

public class ShoppingItem {
    private boolean checked;
    private int quantity;
    private String name;

    public ShoppingItem(int newQuantity, String newName) {
        checked = false;
        quantity = newQuantity;
        name = newName;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void toggleCheck() {
        checked = !checked;
    }
}
