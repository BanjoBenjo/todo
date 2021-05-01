package com.example.todo.command;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.todo.DatabaseHelper;
import com.example.todo.command.Command;
import com.example.todo.tasks.ScheduledTask;
import com.example.todo.tasks.Task;

public class Delete implements Command {
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
        try{
            ((ScheduledTask)task).cancel(context);

        }catch(Exception e){
            Log.e("Delete", "got exception in execute()");
        }
        databaseHelper.deleteTask(taskId);
    }

    @Override
    public void undo() {
        databaseHelper.reloadTask(taskId);
        // activate notifications on Scheduled Tasks when reloaded
        Task task = databaseHelper.getTask(taskId);
        try{
            ((ScheduledTask)task).remind(context);
        }catch(Exception e){
            Log.e("Delete", "got exception in undo()");
        }
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
