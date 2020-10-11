package com.example.todo;


public class Task {

    private String title;

    public Notification notificationType;

    public Task(String title){
        this.title = title;
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

    @Override
    public String toString() {
        return title;
    }
}
