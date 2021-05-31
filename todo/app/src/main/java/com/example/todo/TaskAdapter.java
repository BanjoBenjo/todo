package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.tasks.Task;

import java.nio.channels.NonReadableChannelException;
import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    ArrayList<Task> taskDataList;
    Context context;

    public TaskAdapter(ArrayList<Task> taskData, Activity activity) {
        this.taskDataList = taskData;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Task taskData = taskDataList.get(position);
        holder.textViewName.setText(taskData.getTitle());
        holder.textViewInfo.setText(taskData.getNotes());

        String taskType = taskData.getType();

        switch (taskType) {
            case "SHOPPING":
                holder.taskImage.setImageResource(R.drawable.baseline_checklist_black_24dp);
                holder.taskImage.setPadding(5,5,5,5); // to adjust size
                holder.taskImage.setImageAlpha(150);
                break;
            case "SCHEDULED":
                holder.taskImage.setImageResource(R.drawable.termin);
                holder.taskImage.setImageAlpha(150);
                break;
            default:
                holder.taskImage.setImageResource(0);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (taskType) {
                    case "BASIC":
                        Intent toEditBasicTask = new Intent(context, NewBasicTaskActivity.class);
                        toEditBasicTask.putExtra("taskID", taskData.getID());
                        context.startActivity(toEditBasicTask);
                        break;
                    case "SHOPPING":
                        Intent toEditShoppingTask = new Intent(context, NewShoppingTaskActivity.class);
                        toEditShoppingTask.putExtra("taskID", taskData.getID());
                        context.startActivity(toEditShoppingTask);
                        break;
                    case "SCHEDULED":
                        Intent toEditScheduledTask = new Intent(context, NewScheduledTaskActivity.class);
                        toEditScheduledTask.putExtra("taskID", taskData.getID());
                        context.startActivity(toEditScheduledTask);
                        break;
                    default:
                        Log.e("MainActivity", "task to edit has unknown type");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView taskImage;
        TextView textViewName;
        TextView textViewInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskImage = itemView.findViewById(R.id.imageView);
            textViewName = itemView.findViewById(R.id.titleTextView);
            textViewInfo = itemView.findViewById(R.id.infoTextView);
        }
    }
}
