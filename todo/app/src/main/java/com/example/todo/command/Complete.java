package com.example.todo.command;

import android.util.Log;

import com.example.todo.DatabaseHelper;
import com.example.todo.command.Command;

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
        Log.d("DEBUG Complete", "TaskId  " + String.valueOf(taskId));
        return taskId;
    }
}
