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
            TABLE_ACTIVE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL2 + " TEXT ," + COL3 + " TEXT ," + COL4 + " TEXT ," + COL5 + " TEXT ," + COL6 +
            " TEXT ," + COL7 + ")";

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
     * adds Data/Task to database
     */

    public boolean addBasicTaskActive(BasicTask task){
        return addBasicTask(task, TABLE_ACTIVE);
    }

    public boolean addBasicTaskCompleted(BasicTask task){
        return addBasicTask(task, TABLE_COMPLETED);
    }

    public boolean addBasicTaskDeleted(BasicTask task){
        return addBasicTask(task, TABLE_DELETED);
    }

    public boolean addBasicTask(BasicTask task, String table_type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TYPE", "BASIC");
        contentValues.put("TITLE", task.getTitle());
        contentValues.put("CATEGORY", task.getCategory().toString());
        contentValues.put("NOTES", task.getNotes());

        // Log.d(TAG, "addData: Adding " + item + " to " + TABLE_COMPLETED);

        long result = db.insert(table_type, null, contentValues);

        // if data is inserted incorrectly result will be -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    public boolean addData(String title, TaskCategory category, String notes, String deadline){}

    public boolean addData(String title, TaskCategory category, String notes, String deadline,
                           ArrayList<ShoppingItem> shoppingItems){}


     */

    /**
     * TODO returns all the data from the database
     */
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT ID, TITLE FROM " + TABLE_ACTIVE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}






