package com.example.todo.tasks;

import com.example.todo.notifications.Notification;

public class Task {

    private String title;
    private int ID;

    public Notification notificationType;

    public Task(int ID, String title){
        this.ID = ID;
        this.title = title;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public String getTitle(){
        return title;
    }

    public void taskInfo(){
        System.out.format("Taskinfo: title: %s%n, category: %s%n.",title);
    }

    public int getID(){
        return this.ID;
    }

    public void setID(int newID) { this.ID = newID; }

    public String getType(){
        return "TASK";
    }

    @Override
    public String toString() {
        return title;
    }

    public String getNotes() { return "Notes"; }

}
