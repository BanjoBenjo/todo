package com.example.todo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NewBasicTaskActivity extends Activity {

    private static final String TAG = "NewBasicTaskActivity";

    DatabaseHelper myDatabaseHelper;
    private EditText nameView;
    private Spinner notiSpinner;
    private Button submitButton;
    private EditText notes;

    private ArrayAdapter<String> catAdapter;

    private List<String> categories = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_task);

        categories.add("privat");
        categories.add("school");
        categories.add("work");
        categories.add("sport");

        nameView = findViewById(R.id.editName);
        notiSpinner = findViewById(R.id.spinner);
        submitButton = findViewById(R.id.submitButton);
        notes = findViewById(R.id.editNote);

        catAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notiSpinner.setAdapter(catAdapter);

        myDatabaseHelper = new DatabaseHelper(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty(nameView)) {
                    BasicTask task = new BasicTask(getGlobalTaskId(), nameView.getText().toString(),
                            categories.get(notiSpinner.getSelectedItemPosition()), notes.getText().toString());
                    toastMessage("Task created");
                    addTask(task);
                }else {
                    toastMessage("You have to enter a name!");
                }
            }
        });
    }

    public void addTask(BasicTask mTask) {
        // Todo load task obejct in database / test with name only
        boolean insertData = myDatabaseHelper.addTask(mTask);

        if (insertData) {
            Intent to_mainactivity = new Intent(NewBasicTaskActivity.this, MainActivity.class);
            NewBasicTaskActivity.this.startActivity(to_mainactivity);

        } else {
            toastMessage("There went something wrong..");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private int getGlobalTaskId(){
        SharedPreferences mPreferences = getSharedPreferences("CurrentUser",
                MODE_PRIVATE);

        int task_id = mPreferences.getInt("GlobalTaskID", 1);

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("GlobalTaskID", task_id+1);
        editor.commit();

        return task_id;
    }
}
