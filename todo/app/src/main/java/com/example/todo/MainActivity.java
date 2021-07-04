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
    /**
     * Main application screen. Shows the currently active tasks and two toolbars.
     */
    DatabaseHelper myDatabaseHelper = DatabaseHelper.getInstance(this);
    // active task list
    private ArrayList<Task> taskList;
    TaskAdapter taskAdapter;
    RecyclerView recyclerView;
    // command pattern
    Invoker invoker = new Invoker();
    ImageButton undoButton;
    ImageButton redoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // only gets called on startup, if we come from another activity onResume() gets called
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        // initialize RecyclerView with Adapter
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        taskList = myDatabaseHelper.getAllActiveTasks();
        Collections.sort(taskList);
        System.out.println(taskList);

        Collections.reverse(taskList);
        System.out.println(taskList);

        taskAdapter = new TaskAdapter(taskList, MainActivity.this);
        recyclerView.setAdapter(taskAdapter);
        recyclerView.scrollToPosition(taskList.size() - 1);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // initialize Intents
        Intent toNewBasicTask = new Intent(this, EditBasicTaskActivity.class);
        Intent toNewScheduledTask = new Intent(this, EditScheduledTaskActivity.class);
        Intent toNewListTask = new Intent(this, EditListTaskActivity.class);
        Intent toOverview = new Intent(this, OverviewActivity.class);

        // initialize Buttons
        ImageButton scheduledButton = findViewById(R.id.scheduled_button);
        ImageButton listButton = findViewById(R.id.list_button);
        ImageButton basicButton = findViewById(R.id.basic_button);
        ImageButton overviewButton = findViewById(R.id.overviewButton);

        // initialize command pattern buttons and set clickable state
        undoButton = findViewById(R.id.undo_button);
        undoButton.setEnabled(false);
        redoButton = findViewById(R.id.redo_button);
        redoButton.setEnabled(false);

        // new task button listener
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
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(toNewListTask);
            }
        });
        // button to overview/statistics
        overviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { MainActivity.this.startActivity(toOverview); }
        });

        // command pattern listeners
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
        // when coming back from other activities, this gets called
        super.onResume();
        update();
    }

    // swiping and dragging
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback
            (3, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            // drag tasks
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Task task1 = taskList.get(fromPosition);
            Task task2 = taskList.get(toPosition);
            // change ids and list positions to save the new order
            swapTaskId(task1, task2);
            Collections.swap(taskList, fromPosition, toPosition);
            // update
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // swiping
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
            // background for swiping
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
        // disabled buttons have elevation 0
        undoButton.setElevation(0.0f);
        redoButton.setElevation(0.0f);
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        // set the enabled button to higher elevation to indicate state to user
        if (invoker.getRedoState()){
            redoButton.setElevation(20.0f);
            redoButton.setEnabled(true);
        }
        if (invoker.getUndoState()){
            undoButton.setElevation(20.0f);
            undoButton.setEnabled(true);
        }
    }

    public void updateContent() {
        // update the contents of active task list
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
        // change order of tasks, needed for saving order in list after dragging cards
        int taskId1 = task1.getID();
        int taskId2 = task2.getID();
        // swap task ids
        task1.setID(taskId2);
        task2.setID(taskId1);
        // replace them in database
        myDatabaseHelper.addTask(task1);
        myDatabaseHelper.addTask(task2);
    }

    @Override
    public void onBackPressed() {
        // close app on back press in main activity
        this.finishAffinity();
    }
}



