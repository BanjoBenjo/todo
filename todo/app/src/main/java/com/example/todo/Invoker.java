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
        counter = 0;
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
        if (!databaseHelper.addCommand(theCommand)) {
            Log.d("Invoker","command not correctly inserted into table");
        }
        counter++;
        Log.wtf("MOIN", "counter is now " + counter);
    }

    public void clickUndo() {
        Log.wtf("MOIN", "undo with counter " + counter);
        theCommand = databaseHelper.getCommand(counter);
        Log.wtf("DEBUG invoker", String.valueOf(theCommand));
        counter--;
        theCommand.undo();
    }

    public void clickRedo() {
        theCommand.execute();
        counter++;
    }
}
