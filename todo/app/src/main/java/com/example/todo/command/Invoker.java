package com.example.todo.command;

import com.example.todo.command.Command;

import java.util.ArrayList;

public class Invoker {
    private Command theCommand;
    private int counter = 0;
    private ArrayList<Command> commandList = new ArrayList<>();

    public void setCommand(Command newCommand) {
        theCommand = newCommand;
    }

    public void clickDo() {
        // if command executes after undo
        if (counter < commandList.size()) {
            commandList.subList(counter, commandList.size() - counter).clear();
        }
        commandList.add(theCommand);
        theCommand.execute();
        counter++;
    }

    public void clickUndo() {
        // should never be the case, button not be clickable in this case
        if (commandList.size() == 0) {
            return;
        }
        // should always be the case for same reason
        if (counter > 0) {
            commandList.get(counter - 1).undo();
            counter--;
        }
    }

    public void clickRedo() {
        // button should be deactivated in this case
        if (commandList.size() == 0) {
            return;
        }
        if (counter < commandList.size()) {
            counter++;
            commandList.get(counter - 1).execute();
        }
    }

    public boolean getUndoState() {
        if (counter == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getRedoState() {
        if (counter == commandList.size()) {
            return false;
        } else {
            return true;
        }
    }
}
