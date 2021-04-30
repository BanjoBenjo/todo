package com.example.todo.command;

import android.util.Log;

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
        int size = commandList.size();
        // if command executes after undo
        if (counter < size) {
            // this should not cause errors now
            commandList.subList(counter, size - 1).clear();
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
        if (counter > 0){
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
        Log.wtf("commandList", commandList.toString());
        Log.wtf("commandList", String.valueOf(commandList.size()));
        Log.wtf("counter", String.valueOf(counter));
        if (counter == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getRedoState() {
        Log.wtf("commandList", commandList.toString());
        Log.wtf("commandList", String.valueOf(commandList.size()));
        Log.wtf("counter", String.valueOf(counter));
        if (counter == commandList.size()) {
            return false;
        } else {
            return true;
        }
    }
}
