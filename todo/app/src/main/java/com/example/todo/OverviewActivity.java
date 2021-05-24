package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class OverviewActivity extends AppCompatActivity {

    DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().hide();

        myDatabaseHelper = DatabaseHelper.getInstance(this);

        ImageButton backButton = findViewById(R.id.backButton);

        EditText activeNumber = findViewById(R.id.activeNumber);
        EditText completedNumber = findViewById(R.id.completedNumber);
        EditText deletedNumber = findViewById(R.id.deletedNumber);

        activeNumber.setText(String.valueOf(myDatabaseHelper.getActiveTaskCount()));
        completedNumber.setText(String.valueOf(myDatabaseHelper.getCompletedTaskCount()));
        deletedNumber.setText(String.valueOf(myDatabaseHelper.getDeletedTaskCount()));

        ConstraintLayout activeRow = findViewById(R.id.activeTasksRow);
        ConstraintLayout completedRow = findViewById(R.id.completedRow);
        ConstraintLayout deletedRow = findViewById(R.id.deletedTasksRow);


        Intent toMain = new Intent(this, MainActivity.class);
        Intent toCompletedList = new Intent(this, OverviewList.class);
        toCompletedList.putExtra("TYPE", "COMPLETED");

        Intent toDeletedList = new Intent(this, OverviewList.class);
        toDeletedList.putExtra("TYPE", "DELETED");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OverviewActivity.this.startActivity(toMain); }
        });

        activeRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { OverviewActivity.this.startActivity(toMain); }
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