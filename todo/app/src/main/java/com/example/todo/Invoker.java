package com.example.todo;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private Command theCommand;
    private int counter;
    private DatabaseHelper databaseHelper;

    public Invoker(DatabaseHelper newDataBaseHelper) {
        counter = 0;
        databaseHelper = newDataBaseHelper;
    }

    public void setCommand(Command newCommand) {
        theCommand = newCommand;
    }

    public void clickDo() {
        theCommand.execute();
        databaseHelper.addCommand(theCommand);
        counter++;
    }

    public void clickUndo() {
        counter--;
        theCommand = databaseHelper.getCommand(counter);
        theCommand.undo();
    }

    public void clickRedo() {
        theCommand.execute();
        counter++;
    }
}
