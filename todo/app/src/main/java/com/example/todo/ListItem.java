package com.example.todo;

public class ListItem implements Comparable<ListItem>{
    /**
     * List items in list tasks. Originally meant as a shopping list, items are checkable. Checked
     * state is displayed on the right by checking box.
     */
    private boolean checked;
    private String name;

    public ListItem(String newName) {
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

    public void setChecked(Boolean check) {
        checked = check;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getName(){
        return name;
    }

    @Override
    public int compareTo(ListItem i) {
        return Boolean.compare(this.checked, i.isChecked());
    }
}
