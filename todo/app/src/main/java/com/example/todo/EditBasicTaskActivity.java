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

    private EditText name_view;
    private Spinner spinner;
    private Button button;
    private EditText notes;

    private ArrayAdapter<TaskCategory> cat_adapter;

    private List<TaskCategory> categories = new ArrayList<TaskCategory>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_basic_task);

        categories.add(new TaskCategory("Privat", "red"));
        categories.add(new TaskCategory("Schule", "blue"));
        categories.add(new TaskCategory("Arbeit", "grau"));
        categories.add(new TaskCategory("Sport", "grün"));

        name_view = findViewById(R.id.edit_name);
        spinner = findViewById(R.id.spinner);
        button = findViewById(R.id.submit_button);

        cat_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        cat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cat_adapter);

    }

    private void addTask(){
        name_view = findViewById(R.id.edit_name);
        spinner = findViewById(R.id.spinner);
        notes = findViewById(R.id.editTextTextMultiLine);

        if(!(name_view.equals(""))){
            BasicTask task = new BasicTask(name_view.getText().toString(),
                    categories.get(spinner.getSelectedItemPosition()), notes.getText().toString());

            // Todo load task in database
            Intent to_mainactivity = new Intent(EditBasicTaskActivity.this, MainActivity.class);
            EditBasicTaskActivity.this.startActivity(to_mainactivity);
        }
    }

}
