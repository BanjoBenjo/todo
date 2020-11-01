package com.example.todo;


import android.content.SharedPreferences;

import java.io.Serializable;

import static android.content.Context.MODE_PRIVATE;

public class Task {

    private String title;
    private String category;
    private int ID;

    public Notification notificationType;

    public Task(int ID, String title, String category){
        this.ID = ID;
        this.title = title;
        this.category = category;
    }

    public void setTitle(String newTitle){ this.title = newTitle; }
    public String getTitle(){ return title; }


    public void taskInfo(){
        System.out.format("Taskinfo: title: %s%n, category: %s%n.",title);
    }

    public void remind(){
        notificationType.do_notify();
    }

    public void setNotificationType(Notification notificationType) {
        System.out.format("NotificationType of %s changed \n", title);
        this.notificationType = notificationType;
    }

    public String getCategory(){ return this.category; }

    public int getID(){ return this.ID; }

    public String getType(){ return "TASK"; }

    @Override
    public String toString() {
        return title;
    }
}
