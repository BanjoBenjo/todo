import java.util.ArrayList;
import java.util.List;

public class Invoker {
    //Invoker
    private Command theCommand;
    private List<Command> commandHistory;
    private int counter;

    public Invoker() {
        commandHistory = new ArrayList<>();
        counter = 0;
    }

    public void setCommand(Command newCommand) {
        theCommand = newCommand;
    }

    //simulates ActionListener of a button in the UI
    public void clickDo() {
        theCommand.execute();
        commandHistory.add(counter, theCommand);
        counter++;
        System.out.println("ClickDo commandHistory: " + commandHistory);
        System.out.println("ClickDo counter: " + counter);
    }

    public void clickUndo() {
        counter--;
        commandHistory.get(counter).undo();
        System.out.println("ClickUndo commandHistory: " + commandHistory);
        System.out.println("ClickUndo counter: " + counter);
    }

    public void clickRedo() {
        theCommand.execute();
        counter++;
        System.out.println("ClickRedo commandHistory: " + commandHistory);
        System.out.println("ClickRedo counter: " + counter);
    }

}
