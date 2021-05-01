package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todo.command.Complete;
import com.example.todo.command.Invoker;
import com.example.todo.tasks.Task;
import com.wdullaer.swipeactionadapter.SwipeActionAdapter;
import com.wdullaer.swipeactionadapter.SwipeDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ListActivity implements SwipeActionAdapter.SwipeActionListener {
    private static final String TAG = "MainActivity";
    // list stuff
    protected SwipeActionAdapter mAdapter;
    private List<String> content = new ArrayList<>();
    private ArrayAdapter<String> stringAdapter;
    //invoker for command pattern
    private Invoker invoker = new Invoker();
    private Button undoButton;
    private Button redoButton;
    // Intents
    Intent toNewBasicTask;
    Intent toNewScheduledTask;
    Intent toNewShoppingTask;

    DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
    ;

    // only gets called once on startup, see onResume()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Intents
        toNewBasicTask = new Intent(this, NewBasicTaskActivity.class);
        toNewScheduledTask = new Intent(this, NewScheduledTaskActivity.class);
        toNewShoppingTask = new Intent(this, NewShoppingTaskActivity.class);

        Button addButton = findViewById(R.id.add_button);
        Button scheduledButton = findViewById(R.id.scheduled_button);
        Button shoppingButton = findViewById(R.id.shopping_button);

        undoButton = findViewById(R.id.undo_button);
        undoButton.setEnabled(false);
        redoButton = findViewById(R.id.redo_button);
        redoButton.setEnabled(false);

        registerForContextMenu(addButton);

        updateButtons();

        //button listeners
        addButton.setOnClickListener(new View.OnClickListener() {
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

        List<Task> tasksFromLastUsage = myDatabaseHelper.getAllActiveTasks();
        for (Task task : tasksFromLastUsage){
            content.add(task.getTitle());
        }
        stringAdapter = new ArrayAdapter<>(
                this,
                R.layout.row_bg,
                R.id.text,
                new ArrayList<>(content)
        );

        mAdapter = new SwipeActionAdapter(stringAdapter);
        mAdapter.setSwipeActionListener(this)
                .setDimBackgrounds(true)
                .setListView(getListView());
        setListAdapter(mAdapter);

        mAdapter.addBackground(SwipeDirection.DIRECTION_FAR_LEFT,R.layout.row_bg_left_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right_far)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT,R.layout.row_bg_right);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.choose_task_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //three bottom menu buttons
        switch(item.getItemId()){
            case R.id.basic_task_option:
                MainActivity.this.startActivity(toNewBasicTask);
                return true;
            case R.id.shopping_list_option:
                MainActivity.this.startActivity(toNewShoppingTask);
                return true;
            case R.id.scheduled_task_option:
                MainActivity.this.startActivity(toNewScheduledTask);
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void updateButtons() {
        undoButton.setEnabled(invoker.getUndoState());
        redoButton.setEnabled(invoker.getRedoState());
    }
    public void updateContent() {
        content.clear();
        ArrayList<Task> activeTasks = myDatabaseHelper.getAllActiveTasks();

        for (Task t : activeTasks){
            content.add(t.getTitle());
        }
        stringAdapter.notifyDataSetChanged();
    }
    public void update(){
        // call every time you want to refresh the list
        updateContent();
        updateButtons();
    }
    @Override
    protected void onResume() {
        // when coming back from other NewTaskActivities, this gets called
        super.onResume();
        update();
    }
    @Override
    public boolean hasActions(int position, SwipeDirection direction){
        if(direction.isLeft()) return true;
        if(direction.isRight()) return true;
        return false;
    }
    @Override
    public boolean shouldDismiss(int position, SwipeDirection direction){
        return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;
    }
    @Override
    public void onSwipe(int[] positionList, SwipeDirection[] directionList){
        for(int i=0;i<positionList.length;i++) {
            SwipeDirection direction = directionList[i];
            int position = positionList[i];
            String dir = "";

            switch (direction) {
                case DIRECTION_FAR_LEFT:
                    dir = "Far left";
                    break;
                case DIRECTION_NORMAL_LEFT:
                    dir = "Left";
                    break;
                case DIRECTION_FAR_RIGHT:
                    dir = "Far right";
                    break;
                case DIRECTION_NORMAL_RIGHT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Test Dialog").setMessage("You swiped right").create().show();
                    dir = "Right";
                    break;
            }
            Toast.makeText(
                    this,
                    dir + " swipe Action triggered on " + mAdapter.getItem(position),
                    Toast.LENGTH_SHORT
            ).show();
            mAdapter.notifyDataSetChanged();
        }
    }
}
