package com.company;

public class BasicTask extends Task {

    private String notes;

    public BasicTask(String title){
        super(title);

        notificationType = new NoNotification(title);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
