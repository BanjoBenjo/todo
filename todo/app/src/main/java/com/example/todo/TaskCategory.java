package com.example.todo;

public class TaskCategory {
    private String name;
    private String color;

    public TaskCategory(String name, String color){
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }

    void edit_category(){

    }
}
