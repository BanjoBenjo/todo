package com.example.todo.tasks;

public class BasicTask extends Task {
    /**
     * Basic task, does only have a title and notes.
     */
    private String notes;

    public BasicTask(int ID, String title, String notes){
        super(ID, title);

        this.notes = notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes(){ return this.notes; }

    public String getType(){ return "BASIC"; }
}
