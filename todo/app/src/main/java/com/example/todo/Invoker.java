package com.example.todo;

import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private Command theCommand;
    private int counter;
    private DatabaseHelper databaseHelper;

    public Invoker(DatabaseHelper newDataBaseHelper) {
        counter = 1;
        databaseHelper = newDataBaseHelper;
    }

    public void setCommand(Command newCommand) {
        theCommand = newCommand;
    }

    public int getCounter() {
        return counter;
    }

    public void clickDo() {
        Log.wtf("MOIN", "do with command " + theCommand);
        theCommand.execute();
        if (!databaseHelper.addCommand(theCommand, counter)) {
            Log.d("DEBUG Invoker","command not correctly inserted into table");
        }
        counter++;
        Log.wtf("MOIN", "counter is now " + counter);
    }

    public void clickUndo() {
        Log.d("DEBUG Invoker", "undo with counter " + counter);
        counter--;
        theCommand = databaseHelper.getCommand(counter);
        theCommand.undo();
    }

    public void clickRedo() {
        Log.d("DEBUG Invoker", "redo with counter " + counter);
        theCommand = databaseHelper.getCommand(counter);
        Log.wtf("DEBUG", "got command " + String.valueOf(theCommand));
        theCommand.execute();
        counter++;
    }
}
