package com.example.todo.command;

import com.example.todo.DatabaseHelper;
import com.example.todo.command.Command;

public class Delete implements Command {
    private int taskId;
    private DatabaseHelper databaseHelper;

    public Delete(int newTaskId, DatabaseHelper newDatabaseHelper) {
        taskId = newTaskId;
        databaseHelper = newDatabaseHelper;
    }

    @Override
    public void execute() {
        databaseHelper.deleteTask(taskId);
    }

    @Override
    public void undo() {
        databaseHelper.reloadTask(taskId);
    }

    @Override
    public String getType() {
        return "DELETE";
    }

    @Override
    public int getTaskId() {
        return taskId;
    }

}
