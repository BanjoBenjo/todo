public class BasicTask extends Task{
    //Receiver
    private String theTitle;

    public BasicTask(String newTitle) {
        theTitle = newTitle;
    }

    public void print() {
        System.out.println("BasicTask with Title " + theTitle);
    }
}
