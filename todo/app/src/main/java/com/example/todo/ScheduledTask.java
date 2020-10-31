package com.example.todo;


public class ScheduledTask extends Task {
    private String notes;
    private TaskCategory category;
    private String deadline;
    private Notification notificationType;

    public ScheduledTask(int ID, String title, TaskCategory category, String notes, String deadline){
        super(ID, title, category);

        this.category = category;
        this.notes = notes;
        this.deadline = deadline;
        this.notificationType = new PopUpNotification(title, deadline);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes(){ return this.notes; }
    public String getDeadline(){ return this.deadline; }
    public String getType(){ return "SCHEDULED"; }
    public Notification getNotification(){ return this.notificationType ;}


}
