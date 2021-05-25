package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OverviewActivity extends AppCompatActivity {

    DatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        getSupportActionBar().hide();

        myDatabaseHelper = DatabaseHelper.getInstance(this);

        ImageButton backButton = findViewById(R.id.backButton);

        TextView activeNumber = findViewById(R.id.activeNumber);
        TextView completedNumber = findViewById(R.id.completedNumber);
        TextView deletedNumber = findViewById(R.id.deletedNumber);

        activeNumber.setText(String.valueOf(myDatabaseHelper.getActiveTaskCount()));
        completedNumber.setText(String.valueOf(myDatabaseHelper.getCompletedTaskCount()));
        deletedNumber.setText(String.valueOf(myDatabaseHelper.getDeletedTaskCount()));

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