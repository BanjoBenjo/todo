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
        theCommand.execute();
        Log.wtf("DEBUG Invoker", "Do with counter " + counter);
        if (!databaseHelper.addCommand(theCommand, counter)) {
            Log.d("DEBUG Invoker","command not correctly inserted into table");
        }
        counter++;
        Log.wtf("MOIN", "counter is now " + counter);
    }

    public void clickUndo() {
        Log.wtf("DEBUG Invoker", "Undo with counter " + counter);
        counter--;
        theCommand = databaseHelper.getCommand(counter);
        theCommand.undo();
    }

    public void clickRedo() {
        Log.wtf("DEBUG Invoker", "Redo with counter " + counter);
        theCommand = databaseHelper.getCommand(counter);
        theCommand.execute();
        counter++;
    }
    /*
    TODO
    Counter does not work, when do() follows undo()
    does work, when undo() redo() do()
    solution:   two seperate counters?
                get last command from table?
     */
}
