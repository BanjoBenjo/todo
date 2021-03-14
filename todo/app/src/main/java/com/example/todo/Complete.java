package com.example.todo;

import android.provider.ContactsContract;
import android.util.Log;

import java.util.List;

public class Complete implements Command {
    private int taskId;
    private DatabaseHelper databaseHelper;

    public Complete( int newTaskId, DatabaseHelper newDateBaseHelper) {
        taskId = newTaskId;
        databaseHelper = newDateBaseHelper;
    }

    @Override
    public void execute() {
        databaseHelper.completeTask(taskId);
    }

    @Override
    public void undo() {
        databaseHelper.reopenTask(taskId);
    }

    @Override
    public String getType() {
        return "COMPLETE";
    }

    @Override
    public int getTaskId() {
        Log.wtf("DEBUG complete", "returning " + String.valueOf(taskId));
        return taskId;
    }
}
