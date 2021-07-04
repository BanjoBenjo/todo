package com.example.todo.command;

import java.util.ArrayList;

public class Invoker {
    /**
     * Calls execute() or undo() of the associated command and handles the command list. Tracks the
     * current position in the command list.
     */
    private Command theCommand;
    private int counter = 0;
    private ArrayList<Command> commandList = new ArrayList<>();

    public void setCommand(Command newCommand) {
        // needs to be set before calling any of the click-methods
        theCommand = newCommand;
    }

    public void clickDo() {
        int size = commandList.size();
        // if command executes after undo
        if (counter < size) {
            // remove everything with index higher than counter
            commandList.subList(counter, size).clear();
        }
        // add the command to the list
        commandList.add(theCommand);
        // execute the command and count up
        theCommand.execute();
        counter++;
    }

    public void clickUndo() {
        // safety
        if (commandList.size() == 0) {
            return;
        }
        // count down and undo the command
        if (counter > 0){
            counter--;
            commandList.get(counter).undo();
        }
    }

    public void clickRedo() {
        // safety
        if (commandList.size() == 0) {
            return;
        }
        // execute the command again and count up
        if (counter < commandList.size()) {
            commandList.get(counter).execute();
            counter++;
        }
    }

    public boolean getUndoState() {
        // this method is used to update the clickable state of the undo button
        return counter != 0;
    }

    public boolean getRedoState() {
        // this method is used to update the clickable state of the redo button
        return counter != commandList.size();
    }
}
