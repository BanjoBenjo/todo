package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.todo.tasks.Task;

import java.util.ArrayList;

public class OverviewList extends AppCompatActivity {

    DatabaseHelper myDatabaseHelper;
    private ArrayList<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_list);
        getSupportActionBar().hide();

        myDatabaseHelper = DatabaseHelper.getInstance(this);

        // Initialize RecyclerView with Adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Intent thisIntent = getIntent();
        String type = thisIntent.getStringExtra("TYPE");

        switch (type){
            case "COMPLETED":
                taskList = myDatabaseHelper.getAllCompletedTasks();
                break;
            case "DELETED":
                taskList = myDatabaseHelper.getAllDeletedTasks();
                break;
        }

        OverviewAdapter taskAdapter = new OverviewAdapter(taskList, OverviewList.this);
        recyclerView.setAdapter(taskAdapter);

        ImageButton backButton = findViewById(R.id.backButtonCL);
        Intent toOverview = new Intent(this, OverviewActivity.class);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OverviewList.this.startActivity(toOverview); }
        });
    }
}