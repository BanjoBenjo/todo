package com.example.todo;

import com.example.todo.notifications.Notification;

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

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public String getTitle(){
        return title;
    }

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

    public String getCategory(){
        return this.category;
    }

    public void setCategory(String newCategory){
        this.category = newCategory;
    }

    public int getID(){
        return this.ID;
    }

    public String getType(){
        return "TASK";
    }

    @Override
    public String toString() {
        return title;
    }
}
