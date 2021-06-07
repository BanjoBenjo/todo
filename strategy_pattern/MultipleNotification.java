package com.company;

public class MultipleNotification implements Notification {

    private String title;
    public String deadline;
    public int interval;
    public int count;

    public MultipleNotification(String title, String deadline, int interval, int count){
        this.title = title;
        this.deadline = deadline;
        this.interval = interval;
        this.count = count;
    }

    public void do_notify(){
        for (int i = count-1; i >= 0; i--) {
            System.out.format("It's %d minutes till %s then %s ! MultipleNotification \n", i*interval, deadline, title);
        }
    }
}
