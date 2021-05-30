package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.todo.command.Complete;
import com.example.todo.command.Delete;
import com.example.todo.command.Invoker;
import com.example.todo.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDatabaseHelper = DatabaseHelper.getInstance(this);

    private ArrayList<Task> taskList;
    TaskAdapter taskAdapter;
    RecyclerView recyclerView;

    Invoker invoker = new Invoker();
    ImageButton undoButton;
    ImageButton redoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // Initialize RecyclerView with Adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskList = myDatabaseHelper.getAllActiveTasks();
        taskAdapter = new TaskAdapter(taskList, MainActivity.this);
        recyclerView.setAdapter(taskAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Initialize Intents
        Intent toNewBasicTask = new Intent(this, NewBasicTaskActivity.class);
        Intent toNewScheduledTask = new Intent(this, NewScheduledTaskActivity.class);
        Intent toNewShoppingTask = new Intent(this, NewShoppingTaskActivity.class);
        Intent toOverview = new Intent(this, OverviewActivity.class);

        // Initialize Buttons
        ImageButton scheduledButton = findViewById(R.id.scheduled_button);
        ImageButton shoppingButton = findViewById(R.id.shopping_button);
        ImageButton basicButton = findViewById(R.id.basic_button);
        ImageButton overviewButton = findViewById(R.id.overviewButton);

        undoButton = findViewById(R.id.undo_button);
        undoButton.setEnabled(false);
        redoButton = findViewById(R.id.redo_button);
        redoButton.setEnabled(false);

        // Task button Listener
        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.startActivity(toNewBasicTask);
            }
        });
        scheduledButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(toNewScheduledTask);
            }
        });
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(toNewShoppingTask);
            }
        });

        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { MainActivity.this.startActivity(toOverview); }
        });

        // Command Pattern Button Listener
        undoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                invoker.clickUndo();
                update();
            }
        });
        redoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                invoker.clickRedo();
                update();
            }
        });
    }

    @Override
    protected void onResume() {
        // when coming back from other NewTaskActivities, this gets called
        super.onResume();
        update();
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
            (3, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Task task1 = taskList.get(fromPosition);
            Task task2 = taskList.get(toPosition);
            swapTaskId(task1, task2);
            Collections.swap(taskList, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            final Task taskData = taskList.get(position);

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    // complete task
                    Complete completeCommand = new Complete(taskData.getID(), myDatabaseHelper, MainActivity.this);
                    invoker.setCommand(completeCommand);
                    invoker.clickDo();
                    update();
                    break;

                case ItemTouchHelper.RIGHT:
                    // delete task
                    Delete deleteCommand = new Delete(taskData.getID(), myDatabaseHelper, MainActivity.this);
                    invoker.setCommand(deleteCommand);
                    invoker.clickDo();
                    update();
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.ButtonEnabled))
                    .addSwipeLeftActionIcon(R.drawable.baseline_done_black_24dp)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.RedSwipe))
                    .addSwipeRightActionIcon(R.drawable.baseline_delete_black_24dp)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void updateButtons() {
        undoButton.setEnabled(invoker.getUndoState());
        redoButton.setEnabled(invoker.getRedoState());
    }

    public void updateContent() {
        taskList.clear();
        taskList.addAll(myDatabaseHelper.getAllActiveTasks());
        taskAdapter.notifyDataSetChanged();
    }

    public void update() {
        // call every time you want to refresh the list
        updateContent();
        updateButtons();
    }

    private void swapTaskId(Task task1, Task task2){
        int taskId1 = task1.getID();
        int taskId2 = task2.getID();

        task1.setID(taskId2);
        task2.setID(taskId1);

        myDatabaseHelper.addTask(task1);
        myDatabaseHelper.addTask(task2);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}



