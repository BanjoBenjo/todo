package com.example.todo;


import java.io.Serializable;

public class Task implements Serializable {

    private String title;
    private TaskCategory category;

    public Notification notificationType;

    public Task(String title, TaskCategory category){
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

    public TaskCategory getCategory(){ return this.category; }

    @Override
    public String toString() {
        return title;
    }
}
