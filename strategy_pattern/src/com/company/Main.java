package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        /*TODO the call of remind has to happen IN the Notification object called by a timer.
           Very important if the notificationType is changed OR the time is changed to CANCEL/EDIT the old Timer */

        Task mittagessen = new BasicTask("Mittagessen");
        Task termin = new ScheduledTask("Termin", "12:00");

        mittagessen.remind();
        termin.remind();

        mittagessen.setNotificationType(new MultipleNotification(mittagessen.getTitle(),"15:00", 5, 3));

        mittagessen.remind();
    }
}
