package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.todo.tasks.BasicTask;
import com.example.todo.tasks.ScheduledTask;
import com.example.todo.tasks.ListTask;
import com.example.todo.tasks.Task;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * API for the Database.
     * Creates the tables after a fresh install.
     * It has all methods needed to enter and load data from the database.
     */

    private static final String DATABASE_NAME = "TASK_DB";
    private ArrayList<ListItem> listItems;

    //  Table + COLS
    private static final String TABLE_ACTIVE = "TABLE_ACTIVE";
    private static final String TABLE_COMPLETED = "TABLE_COMPLETED";
    private static final String TABLE_DELETED = "TABLE_DELETED";

    private static final String DATABASE_CREATE_TABLE_ACTIVE = "CREATE TABLE " +
            "TABLE_ACTIVE (ID INTEGER PRIMARY KEY, TYPE TEXT NOT NULL, TITLE TEXT, DEADLINE TEXT," +
            "NOTIFICATION TEXT, NOTES TEXT)";

    private static final String DATABASE_CREATE_TABLE_COMPLETED = "CREATE TABLE " +
            "TABLE_COMPLETED (ID INTEGER PRIMARY KEY, TYPE TEXT, TITLE TEXT, DEADLINE TEXT," +
                    "NOTIFICATION TEXT, NOTES TEXT)";

    private static final String DATABASE_CREATE_TABLE_DELETED = "CREATE TABLE " +
            "TABLE_DELETED (ID INTEGER PRIMARY KEY, TYPE TEXT, TITLE TEXT, DEADLINE TEXT," +
                    "NOTIFICATION TEXT, NOTES TEXT)";

    // singleton Variable
    private static DatabaseHelper single_instance = null;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHelper getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new DatabaseHelper(context);

        return single_instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // only gets called after device data has been wiped or after fresh install
        db.execSQL(DATABASE_CREATE_TABLE_ACTIVE);
        db.execSQL(DATABASE_CREATE_TABLE_COMPLETED);
        db.execSQL(DATABASE_CREATE_TABLE_DELETED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // gets called on upgrade, not update !!!
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELETED);

        onCreate(db);
    }

    /**
     *
     */
    public boolean addTask(Task my_task){
        // Adds a Task to the Active Table, if the task exists the values are edited and no
        // new Task is created
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = createContentFromTask(my_task);

        long result = db.replace("TABLE_ACTIVE", null, contentValues);

        // if data is inserted incorrectly result will be -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public long getActiveTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_ACTIVE);
        db.close();
        return count;
    }

    public long getCompletedTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_COMPLETED);
        db.close();
        return count;
    }

    public long getDeletedTaskCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_DELETED);
        db.close();
        return count;
    }

    public ArrayList<Task> getAllActiveTasks(){
        // returns a list of all active tasks
        ArrayList<Task> listTasks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTIVE;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            listTasks.add(convertRowToTask(data));
        }

        return listTasks;
    }

    public ArrayList<Task> getAllCompletedTasks(){
        // returns a list of all completed tasks
        ArrayList<Task> listTasks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_COMPLETED;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            listTasks.add(convertRowToTask(data));
        }

        return listTasks;
    }

    public ArrayList<Task> getAllDeletedTasks(){
        // returns a list of all deleted tasks
        ArrayList<Task> listTasks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_DELETED;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            listTasks.add(convertRowToTask(data));
        }

        return listTasks;
    }

    public Task getTask(int ID) {
        // gets active Task with the given ID
        SQLiteDatabase db = this.getWritableDatabase();
        Task myTask = null;
        String query = "SELECT * FROM " + TABLE_ACTIVE + " WHERE ID = " + ID;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            myTask = convertRowToTask(data);
        }
        return myTask;
    }

    public Task getCompletedTask(int ID) {
        // gets completed Task with the given ID
        SQLiteDatabase db = this.getWritableDatabase();
        Task myTask = null;
        String query = "SELECT * FROM " + TABLE_COMPLETED + " WHERE ID = " + ID;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            myTask = convertRowToTask(data);
        }
        return myTask;
    }

    public Task getDeletedTask(int ID) {
        // gets deleted Task with the given ID
        SQLiteDatabase db = this.getWritableDatabase();
        Task myTask = null;
        String query = "SELECT * FROM " + TABLE_DELETED + " WHERE ID = " + ID;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            myTask = convertRowToTask(data);
        }
        return myTask;
    }

    public void completeTask(int task_id) {
        // moves task from active table to completed table
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO TABLE_COMPLETED SELECT * FROM TABLE_ACTIVE WHERE ID =" + task_id);
            db.execSQL("DELETE FROM TABLE_ACTIVE WHERE ID =" + task_id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void reopenTask(int task_id) {
        // moves task from completed table to active table
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO TABLE_ACTIVE SELECT * FROM TABLE_COMPLETED WHERE ID =" + task_id);
            db.execSQL("DELETE FROM TABLE_COMPLETED WHERE ID =" + task_id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteTask(int task_id) {
        // moves task from active table to deleted table
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO TABLE_DELETED SELECT * FROM TABLE_ACTIVE WHERE ID =" + task_id);
            db.execSQL("DELETE FROM TABLE_ACTIVE WHERE ID =" + task_id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void reloadTask(int task_id) {
        // moves task from deleted table to active table
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("INSERT INTO TABLE_ACTIVE SELECT * FROM TABLE_DELETED WHERE ID =" + task_id);
            db.execSQL("DELETE FROM TABLE_DELETED WHERE ID =" + task_id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public ContentValues createContentFromTask(Task my_task){
        // Creates a ContenValue object from a Task object, which is able to be uploaded to the DB
        ContentValues contentValues = new ContentValues();
        String task_type = my_task.getType();
        switch(task_type)
        {
            case "BASIC":
                BasicTask b_task = (BasicTask)my_task;
                contentValues.put("ID", b_task.getID());
                contentValues.put("TYPE", "BASIC");
                contentValues.put("TITLE", b_task.getTitle());
                contentValues.put("DEADLINE", "");
                contentValues.put("NOTIFICATION", "");
                contentValues.put("NOTES", b_task.getNotes());
                break;

            case "SCHEDULED":
                ScheduledTask sched_task = (ScheduledTask)my_task;
                contentValues.put("ID", sched_task.getID());
                contentValues.put("TYPE", "SCHEDULED");
                contentValues.put("TITLE", sched_task.getTitle());
                contentValues.put("DEADLINE", sched_task.getDeadline().toString());
                contentValues.put("NOTIFICATION", sched_task.getNotification().toString());
                contentValues.put("NOTES", sched_task.getNotes());
                break;

            case "LIST":
                ListTask list_task = (ListTask)my_task;
                contentValues.put("ID", list_task.getID());
                contentValues.put("TYPE", "LIST");
                contentValues.put("TITLE", list_task.getTitle());
                contentValues.put("DEADLINE", "");
                contentValues.put("NOTIFICATION", "");
                listItems = list_task.getItems();
                HashMap<String, Boolean> hash_map = new HashMap<>();
                for (ListItem i: listItems) {
                    hash_map.put(i.toString(), i.isChecked());
                }
                try {
                    contentValues.put("NOTES", serialize(hash_map));
                } catch (IOException e ){
                    Log.e("databaseHelper", "Exception while storing notes of list task");
                }
                break;
        }
        return contentValues;
    }

    public Task convertRowToTask(Cursor data){
        // converts a Row from the DB to a Task object
        String task_type = data.getString(data.getColumnIndex("TYPE"));
        int ID;
        String title;
        String notes;
        String notificationType;
        LocalDateTime deadline;
        Task my_task;

        switch (task_type) {
            case "BASIC":
                ID = data.getInt(data.getColumnIndex("ID"));
                title = data.getString(data.getColumnIndex("TITLE"));
                notes = data.getString(data.getColumnIndex("NOTES"));
                my_task = new BasicTask(ID, title, notes);
                break;

            case "SCHEDULED":
                ID = data.getInt(data.getColumnIndex("ID"));
                title = data.getString(data.getColumnIndex("TITLE"));
                notes = data.getString(data.getColumnIndex("NOTES"));
                String date = data.getString(data.getColumnIndex("DEADLINE"));

                DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
                deadline = LocalDateTime.parse(date, formatter);
                notificationType = data.getString(data.getColumnIndex("NOTIFICATION"));

                my_task = new ScheduledTask(ID, title, notes, deadline, notificationType);

                break;

            case "LIST":
            case "SHOPPING": //stays because there may be old shopping items in db
                ID = data.getInt(data.getColumnIndex("ID"));
                title = data.getString(data.getColumnIndex("TITLE"));
                my_task = new ListTask(ID, title);
                notes = data.getString(data.getColumnIndex("NOTES"));
                //split notes into item names, create items and add them to shopping task

                try {
                    HashMap<String, Boolean> hash_map = (HashMap<String, Boolean>) deserialize(notes);
                    for ( String key : hash_map.keySet() ) {
                        ListItem newItem = new ListItem(key);
                        newItem.setChecked(hash_map.get(key));
                        ((ListTask)my_task).addItem(newItem);
                    }
                } catch (IOException e ){
                } catch (ClassNotFoundException ce){}
                break;
            default:
                my_task = null;
        }
        return my_task;
    }

    private static String serialize(Serializable o) throws IOException {
        // serialize objects for storing in db
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static Object deserialize(String s) throws IOException, ClassNotFoundException {
        // deserialize objects
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return o;
    }
}
