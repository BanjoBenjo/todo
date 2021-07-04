
public class ScheduledTask extends Task{
    public String address = "email@todo.de";
    public Notification notificationType;

    public ScheduledTask(String title, String deadline, String notiType){
        super(title);

        switch (notiType){
            case "popup":
                notificationType = new PopUpNotification(title, deadline);
                break;
            case "email":
                notificationType = new EmailNotification(address);
                break;
            default:
                notificationType = new NoNotification(title);
                break;

        }
    }

    public void setNotificationType(Notification notificationType) {
        System.out.format("NotificationType of %s changed \n", title);
        this.notificationType = notificationType;
    }

    public void remind(){
        notificationType.do_notify();
    }

}
