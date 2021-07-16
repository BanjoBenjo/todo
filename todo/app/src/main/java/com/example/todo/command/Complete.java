package com.example.todo.command;

import android.content.Context;
import com.example.todo.DatabaseHelper;
import com.example.todo.tasks.ScheduledTask;
import com.example.todo.tasks.Task;

public class Complete implements Command {
    /**
     * Moves task between active and completed table. If task is scheduled also cancel or activate
     * notification.
     */
    private int taskId;
    private DatabaseHelper databaseHelper;
    private Context context;

    public Complete( int newTaskId, DatabaseHelper newDateBaseHelper, Context newContext) {
        taskId = newTaskId;
        databaseHelper = newDateBaseHelper;
        context = newContext;
    }

    @Override
    public void execute() {
        // cancel Notifications on Scheduled Tasks when completed
        Task task = databaseHelper.getTask(taskId);
        if (task.getType().equals("SCHEDULED")) {
            ((ScheduledTask)task).doCancel(context);
        }
        databaseHelper.completeTask(taskId);
    }

    @Override
    public void undo() {
        databaseHelper.reopenTask(taskId);
        // activate Notifications on Scheduled Tasks when reopened
        Task task = databaseHelper.getTask(taskId);
        if (task.getType().equals("SCHEDULED")) {
            ((ScheduledTask)task).doRemind(context);
        }
    }

    public String getType() {
        return "COMPLETE";
    }

    public int getTaskId() {
        return taskId;
    }
}
