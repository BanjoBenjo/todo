package com.example.todo.tasks;

import com.example.todo.ListItem;

import java.util.ArrayList;

public class ListTask extends Task {
    /**
     * First implemented as shopping list, contains a number of ListItems, which can be checked.
     */
    private ArrayList<ListItem> listItems;

    public ListTask(int ID, String title) {
        super(ID, title);
        listItems = new ArrayList<>();
    }

    public ArrayList<ListItem> getItems(){
        return listItems;
    }

    public int getLength() {
        return listItems.size();
    }

    public void addItem(ListItem newItem) {
        listItems.add(0, newItem);
    }

    public String getType() {
        return "LIST";
    }

    public String getNotes() {
        // returns the ListItems titles as one String for main overview
        StringBuilder stringBuilder = new StringBuilder();
        for (ListItem listItem : listItems){
            stringBuilder.append(listItem.toString());
            stringBuilder.append(", ");
        }
        stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }
}
