package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "TASK_DB";

    //  Table + COLS
    private static final String TABLE_ACTIVE = "TABLE_ACTIVE";
    private static final String TABLE_COMPLETED = "TABLE_COMPLETED";
    private static final String TABLE_DELETED = "TABLE_DELETED";

    private static final String DATABASE_CREATE_TABLE_ACTIVE = "CREATE TABLE " +
            "TABLE_ACTIVE (ID INTEGER PRIMARY KEY, TYPE TEXT NOT NULL, TITLE TEXT, CATEGORY TEXT, DEADLINE TEXT," +
            "NOTIFICATION TEXT, NOTES TEXT)";

    private static final String DATABASE_CREATE_TABLE_COMPLETED = "CREATE TABLE " +
            "TABLE_COMPLETED (ID INTEGER PRIMARY KEY, TYPE TEXT, TITLE TEXT, CATEGORY TEXT, DEADLINE TEXT," +
                    "NOTIFICATION TEXT, NOTES TEXT)";

    private static final String DATABASE_CREATE_TABLE_DELETED = "CREATE TABLE " +
            "TABLE_DELETED (ID INTEGER PRIMARY KEY, TYPE TEXT, TITLE TEXT, CATEGORY TEXT, DEADLINE TEXT," +
                    "NOTIFICATION TEXT, NOTES TEXT)";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // only gets called after device data has been wiped or after fresh install
        Log.d("DEBUG DatabaseHelper", "onCreate()");
        db.execSQL(DATABASE_CREATE_TABLE_ACTIVE);
        db.execSQL(DATABASE_CREATE_TABLE_COMPLETED);
        db.execSQL(DATABASE_CREATE_TABLE_DELETED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // gets called on upgrade, not update !!!
        Log.d("DEBUG DatabaseHelper", "onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELETED);

        onCreate(db);
    }

    /**
     * Adds a Task to the Active Table
     */
    public boolean addTask(Task my_task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = createContentFromTask(my_task);

        long result = db.insert("TABLE_ACTIVE", null, contentValues);

        // if data is inserted incorrectly result will be -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Updates a Task to the Active Table
     */
    public boolean updateTask(Task my_task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = createContentFromTask(my_task);

        long result = db.update(TABLE_ACTIVE, contentValues,"ID=" + my_task.getID(),null);

        // if data is inserted incorrectly result will be -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * gets complete Active Table, builds Tasks and returns list
     */
    public ArrayList<Task> getAllActiveTasks(){
        ArrayList<Task> listTasks = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_ACTIVE;
        Cursor data = db.rawQuery(query, null);

        while(data.moveToNext()){
            listTasks.add(convertRowToTask(data));
        }

        return listTasks;
    }

    /**
     * gets Task with the given ID
     */
    public Task getTask(int ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Task myTask = null;
        String query = "SELECT * FROM " + TABLE_ACTIVE + " WHERE ID = " + ID;
        Cursor data = db.rawQuery(query, null);

        // TODO find better solution for this case, than this workaround (only one return)
        while(data.moveToNext()){
            myTask = convertRowToTask(data);
        }
        return myTask;
    }


    /**
     * Methods to move Data from one Table to another
     */
    public void completeTask(int task_id) {
        //TODO add return statement if function was successful
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
        //TODO add return statement if function was successful
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
        //TODO add return statement if function was successful
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
        //TODO add return statement if function was successfull
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

    /**
     * Creates a ContenValue from a Task object, which now is able to be uploaded to DB
     */
    public ContentValues createContentFromTask(Task my_task){
        ContentValues contentValues = new ContentValues();
        String task_type = my_task.getType();
        switch(task_type)
        {
            case "BASIC":
                //TODO casten ist unnötig oder?
                BasicTask b_task = (BasicTask)my_task;
                contentValues.put("ID", b_task.getID());
                contentValues.put("TYPE", "BASIC");
                contentValues.put("TITLE", b_task.getTitle());
                contentValues.put("CATEGORY", b_task.getCategory().toString());
                contentValues.put("DEADLINE", "");
                contentValues.put("NOTIFICATION", "");
                contentValues.put("NOTES", b_task.getNotes());
                break;

            case "SCHEDULED":
                ScheduledTask sched_task = (ScheduledTask)my_task;
                contentValues.put("ID", sched_task.getID());
                contentValues.put("TYPE", "SCHEDULED");
                contentValues.put("TITLE", sched_task.getTitle());
                contentValues.put("CATEGORY", sched_task.getCategory().toString());
                contentValues.put("DEADLINE", sched_task.getDeadline());
                contentValues.put("NOTIFICATION", sched_task.getNotification().toString());
                contentValues.put("NOTES", sched_task.getNotes());
                break;

            case "SHOPPING":
                // TODO save shopping list maybe in notes field??
                ShoppingTask shop_task = (ShoppingTask)my_task;
                contentValues.put("ID", shop_task.getID());
                contentValues.put("TYPE", "SHOPPING");
                contentValues.put("TITLE", shop_task.getTitle());
                contentValues.put("CATEGORY", "");
                contentValues.put("DEADLINE", "");
                contentValues.put("NOTIFICATION", "");
                contentValues.put("NOTES", "");
                break;

            //TODO ADD DEFAULT EXCEPTION
        }
        return contentValues;
    }

    /**
     * converts a Row from the DB to a Task
     */
    public Task convertRowToTask(Cursor data){
        String task_type = data.getString(data.getColumnIndex("TYPE"));
        int ID;
        String title;
        String category;
        String notes;
        String notificationType;
        String deadline;
        Task my_task;

        switch (task_type) {
            case "BASIC":
                ID = data.getInt(data.getColumnIndex("ID"));
                title = data.getString(data.getColumnIndex("TITLE"));
                category = data.getString(data.getColumnIndex("CATEGORY"));
                notes = data.getString(data.getColumnIndex("NOTES"));
                my_task = new BasicTask(ID, title, category, notes);
                break;

            case "SCHEDULED":
                ID = data.getInt(data.getColumnIndex("ID"));
                title = data.getString(data.getColumnIndex("TITLE"));
                category = data.getString(data.getColumnIndex("CATEGORY"));
                notes = data.getString(data.getColumnIndex("NOTES"));
                notificationType = data.getString(data.getColumnIndex("NOTIFICATION"));
                deadline = data.getString(data.getColumnIndex("DEADLINE"));
                my_task = new ScheduledTask(ID, title, category, notes, deadline, notificationType);
                break;
            case "SHOPPING":
                ID = data.getInt(data.getColumnIndex("ID"));
                title = data.getString(data.getColumnIndex("TITLE"));
                category = data.getString(data.getColumnIndex("CATEGORY"));
                // TODO save shopping list
                my_task = new ShoppingTask(ID, title, category);
                break;
            default:
                my_task = null;
        }
        return my_task;
    }
}
