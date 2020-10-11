package com.example.todo;


public class BasicTask extends Task {

    private String notes;
    private TaskCategory category;

    public BasicTask(String title, TaskCategory category, String notes){
        super(title);
        this.notes = notes;
        notificationType = new NoNotification(title);
    }

    public BasicTask(String title){
        super(title);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setCategory(TaskCategory category) { this.category=category; }


}
