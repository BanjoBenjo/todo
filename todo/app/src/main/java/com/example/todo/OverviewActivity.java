package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


public class OverviewActivity extends AppCompatActivity {
    /**
     * This Activity shows an Overview (Number count) of all active/deleted and completed tasks.
     * The completed and deleted Tasks are also clickable to open another activity which show
     * a list of all Tasks
     */
    DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().hide();

        myDatabaseHelper = DatabaseHelper.getInstance(this);

        // get references of all elements
        ImageButton backButton = findViewById(R.id.backButton);
        TextView activeNumber = findViewById(R.id.activeNumber);
        TextView completedNumber = findViewById(R.id.completedNumber);
        TextView deletedNumber = findViewById(R.id.deletedNumber);

        ConstraintLayout completedRow = findViewById(R.id.completedRow);
        ConstraintLayout deletedRow = findViewById(R.id.deletedTasksRow);

        // set task counts
        activeNumber.setText(String.valueOf(myDatabaseHelper.getActiveTaskCount()));
        completedNumber.setText(String.valueOf(myDatabaseHelper.getCompletedTaskCount()));
        deletedNumber.setText(String.valueOf(myDatabaseHelper.getDeletedTaskCount()));

        // init Intents
        Intent toCompletedList = new Intent(this, OverviewList.class);
        toCompletedList.putExtra("TYPE", "COMPLETED");

        Intent toDeletedList = new Intent(this, OverviewList.class);
        toDeletedList.putExtra("TYPE", "DELETED");

        // Button listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // return to main activity without calling onCreate()
                Intent to_mainactivity = new Intent(OverviewActivity.this, MainActivity.class);
                to_mainactivity.setFlags(FLAG_ACTIVITY_REORDER_TO_FRONT);
                OverviewActivity.this.startActivity(to_mainactivity);
            }
        });

        completedRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OverviewActivity.this.startActivity(toCompletedList); }
        });

        deletedRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OverviewActivity.this.startActivity(toDeletedList); }
        });
    }
}