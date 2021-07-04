
public class Main {
    public static void main(String[] args) {
        BasicTask basicTask1 = new BasicTask("Mittagessen");
        basicTask1.setNotes("Chilli Sin Carne");

        ScheduledTask scheduledTask1 = new ScheduledTask("Termin", "12:00", "popup");
        ScheduledTask scheduledTask2 = new ScheduledTask("Email Erinnerung", "18:00", "email");
        ScheduledTask scheduledTask3 = new ScheduledTask("Wichtig", "18:00", "no");
        scheduledTask3.setNotificationType(
                new MultipleNotification(
                        scheduledTask3.getTitle(),
                        "22:00",
                        5,
                        3  )
        );

        basicTask1.taskInfo();

        // PopUp
        scheduledTask1.taskInfo();
        scheduledTask1.setNotes("Zahnarzt Str.1");
        scheduledTask1.taskInfo();
        scheduledTask1.remind();

        // Email
        scheduledTask2.taskInfo();
        scheduledTask2.remind();

        // Mutiple
        scheduledTask3.remind();
    }
}
