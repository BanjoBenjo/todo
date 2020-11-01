package com.example.todo;


public class BasicTask extends Task {

    private String notes;
    private TaskCategory category;

    public BasicTask(int ID, String title, String category, String notes){
        super(ID, title, category);

        this.notes = notes;
        notificationType = new NoNotification(title);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public void setCategory(TaskCategory category) { this.category=category; }

    public String getNotes(){ return this.notes; }

    public String getType(){ return "BASIC"; }
}
