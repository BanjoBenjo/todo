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

    public Complete( int newTaskId, DatabaseHelper newDateBaseHelper, Context context) {
        taskId = newTaskId;
        databaseHelper = newDateBaseHelper;
        this.context = context;
    }

    @Override
    public void execute() {
        // cancel Notifications on Scheduled Tasks when completed
        Task task = databaseHelper.getTask(taskId);
        try{
            ((ScheduledTask)task).cancel(context);
            Toast.makeText(context, "canceled", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(context, "not ", Toast.LENGTH_SHORT).show();
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
        }catch(Exception e){}
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
