package com.example.todo.command;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.DatabaseHelper;
import com.example.todo.command.Command;
import com.example.todo.tasks.ScheduledTask;
import com.example.todo.tasks.Task;

public class Complete implements Command {
    private int taskId;
    private DatabaseHelper databaseHelper;
    Context context;

    public Complete( int newTaskId, DatabaseHelper newDateBaseHelper, Context newContext) {
        taskId = newTaskId;
        databaseHelper = newDateBaseHelper;
        context = newContext;
    }

    @Override
    public void execute() {
        // cancel Notifications on Scheduled Tasks when completed
        Task task = databaseHelper.getTask(taskId);
        try{
            ((ScheduledTask)task).cancel(context);
        }catch(Exception e){
            Log.e("Complete", "got exception in execute()");
        }
        databaseHelper.completeTask(taskId);
    }

    @Override
    public void undo() {
        databaseHelper.reopenTask(taskId);
        // activate Notifications on Scheduled Tasks when reopened
        Task task = databaseHelper.getTask(taskId);
        try{
            ((ScheduledTask)task).remind(context);
        }catch(Exception e){
            Log.e("Complete", "got exception in undo()");
        }
    }

    public String getType() {
        return "COMPLETE";
    }

    public int getTaskId() {
        Log.d("DEBUG Complete", "TaskId  " + String.valueOf(taskId));
        return taskId;
    }
}
