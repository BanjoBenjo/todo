package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatebaseHelper";

    private static final String DATABASE_NAME = "TASK_DB";

    //  Table + COLS
    private static final String TABLE_ACTIVE = "TABLE_ACTIVE";
    private static final String TABLE_COMPLETED = "TABLE_COMPLETED";
    private static final String TABLE_DELETED = "TABLE_DELETED";

    private static final String COL1 = "ID";
    private static final String COL2 = "TYPE";
    private static final String COL3 = "TITLE";
    private static final String COL4 = "CATEGORY";
    private static final String COL5 = "DEADLINE";
    private static final String COL6 = "NOTIFICATION";
    private static final String COL7 = "NOTES";

    private static final String DATABASE_CREATE_TABLE_ACTIVE = "CREATE TABLE " +
            TABLE_ACTIVE + "(ID INTEGER, TYPE TEXT, TITLE TEXT, CATEGORY TEXT, DEADLINE TEXT," +
            "NOTIFICATION TEXT, NOTES TEXT)";

    private static final String DATABASE_CREATE_TABLE_COMPLETED = "CREATE TABLE " +
            TABLE_COMPLETED + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " TEXT ," + COL3 + " TEXT ," + COL4 + " TEXT ," + COL5 + " TEXT ," + COL6 +
            " TEXT ," + COL7 + ")";

    private static final String DATABASE_CREATE_TABLE_DELETED = "CREATE TABLE " +
            TABLE_DELETED + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " TEXT ," + COL3 + " TEXT ," + COL4 + " TEXT ," + COL5 + " TEXT ," + COL6 +
            " TEXT ," + COL7 + ")";

    public DatabaseHelper(Context context){ super(context, DATABASE_NAME, null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_TABLE_ACTIVE);
        db.execSQL(DATABASE_CREATE_TABLE_COMPLETED);
        db.execSQL(DATABASE_CREATE_TABLE_DELETED);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETED);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELETED);
        onCreate(db);
    }

    /**
     * =========================================================================
     * Methods to add a BASIC TASK to a specific Table in the DB
     */

    public boolean addTask(Task task){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String task_type = task.getType();
        switch(task_type)
        {
            case "BASIC":
                BasicTask b_task = (BasicTask)task;
                contentValues.put("ID", b_task.getID());
                contentValues.put("TYPE", "BASIC");
                contentValues.put("TITLE", b_task.getTitle());
                contentValues.put("CATEGORY", b_task.getCategory().toString());
                contentValues.put("DEADLINE", "");
                contentValues.put("NOTIFICATION", "");
                contentValues.put("NOTES", b_task.getNotes());
                break;

            case "SCHEDULED":
                ScheduledTask sched_task = (ScheduledTask)task;
                contentValues.put("ID", sched_task.getID());
                contentValues.put("TYPE", "SCHEDULED");
                contentValues.put("TITLE", sched_task.getTitle());
                contentValues.put("CATEGORY", sched_task.getCategory().toString());
                contentValues.put("DEADLINE", sched_task.getDeadline());
                contentValues.put("NOTIFICATION", sched_task.getNotification().toString());
                contentValues.put("NOTES", sched_task.getNotes());
                break;

            case "SHOPPING":
                ShoppingTask shop_task = (ShoppingTask)task;
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

        // Log.d(TAG, "addData: Adding " + item + " to " + TABLE_COMPLETED);

        long result = db.insert("TABLE_ACTIVE", null, contentValues);

        // if data is inserted incorrectly result will be -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Methods to move Data from one Tabel to another
     */

    public void completeTask(int task_id) {
        //TODO add return statement if function was successfull
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

    public void undoTask(int task_id) {
        //TODO add return statement if function was successfull
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
        //TODO add return statement if function was successfull
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
     * TODO maybe Build TASK objects here
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ACTIVE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}






