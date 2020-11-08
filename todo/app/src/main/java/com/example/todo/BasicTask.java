package com.example.todo;


public class BasicTask extends Task {

    private String notes;
    public BasicTask(int ID, String title, String category, String notes){
        super(ID, title, category);

        this.notes = notes;
        notificationType = new NoNotification(title);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes(){ return this.notes; }

    public String getType(){ return "BASIC"; }
}
