package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EditBasicTaskActivity extends Activity {

    private EditText nameView;
    private Spinner notiSpinner;
    private Button submitButton;
    private EditText notes;

    private ArrayAdapter<TaskCategory> catAdapter;

    private List<TaskCategory> categories = new ArrayList<TaskCategory>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_basic_task);

        categories.add(new TaskCategory("Privat", "red"));
        categories.add(new TaskCategory("Schule", "blue"));
        categories.add(new TaskCategory("Arbeit", "grau"));
        categories.add(new TaskCategory("Sport", "grün"));

        nameView = findViewById(R.id.editName);
        notiSpinner = findViewById(R.id.spinner);
        submitButton = findViewById(R.id.submitButton);

        catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notiSpinner.setAdapter(catAdapter);

    }

    private void addTask(){
        nameView = findViewById(R.id.editName);
        notiSpinner = findViewById(R.id.spinner);
        notes = findViewById(R.id.editTextTextMultiLine);

        if(!(nameView.equals(""))){
            BasicTask task = new BasicTask(nameView.getText().toString(),
                    categories.get(notiSpinner.getSelectedItemPosition()), notes.getText().toString());

            // Todo load task in database
            Intent to_mainactivity = new Intent(EditBasicTaskActivity.this, MainActivity.class);
            EditBasicTaskActivity.this.startActivity(to_mainactivity);
        }
    }

}
