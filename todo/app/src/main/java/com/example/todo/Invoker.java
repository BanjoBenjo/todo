package com.example.todo;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
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

    public void clickDo() {
        theCommand.execute();
        commandHistory.add(counter, theCommand);
        counter++;
    }

    public void clickUndo() {
        counter--;
        commandHistory.get(counter).undo();
    }

    public void clickRedo() {
        theCommand.execute();
        counter++;
    }
}
