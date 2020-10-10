public class BasicTask extends Task{
    //Receiver
    private boolean completed;
    private String theTitle;

    public BasicTask(String newTitle) {
        theTitle = newTitle;
        completed = false;
    }

    @Override
    public void complete() {
        completed = true;
        System.out.println("Basic Task has been completed");
    }

    public void reopen() {
        completed = false;
        System.out.println("Basic Task has been reopened");
    }

    public void print() {
        if (completed) {
            System.out.println("BasicTask with name " + theTitle + " has been completed");
        } else {
            System.out.println("BasicTask with name " + theTitle + " has not yet been completed");
        }
    }
}
