package com.example.todo.command;

import android.content.Context;
import com.example.todo.DatabaseHelper;
import com.example.todo.tasks.ScheduledTask;
import com.example.todo.tasks.Task;

public class Delete implements Command {
    /**
     * Calls deleteTask() and reloadTask() to move tasks between active and deleted table. If task
     * is of type scheduled the notification gets cancelled or activated.
     */
    private int taskId;
    private DatabaseHelper databaseHelper;
    private Context context;

    public Delete(int newTaskId, DatabaseHelper newDatabaseHelper, Context newContext) {
        taskId = newTaskId;
        databaseHelper = newDatabaseHelper;
        context = newContext;
    }

    @Override
    public void execute() {
        // cancel notifications on Scheduled Tasks when completed
        Task task = databaseHelper.getTask(taskId);
        if(task.getType().equals("SCHEDULED")) {
            ((ScheduledTask)task).doCancel(context);
        }
        databaseHelper.deleteTask(taskId);
    }

    @Override
    public void undo() {
        databaseHelper.reloadTask(taskId);
        // activate notifications on Scheduled Tasks when reloaded
        Task task = databaseHelper.getTask(taskId);
        if (task.getType().equals("SCHEDULED")) {
            ((ScheduledTask)task).doRemind(context);
        }
    }

    public String getType() {
        return "DELETE";
    }

    public int getTaskId() {
        return taskId;
    }

}
