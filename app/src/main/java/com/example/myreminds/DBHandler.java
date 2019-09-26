package com.example.myreminds;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    //initialize constants for DB version and name
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "reminders.db";

    //intialize constants for shopping list table
    private static final String TABLE_REMINDER_LIST = "reminders";
    private static final String COLUMN_LIST_ID = "_id";
    private static final String COLUMN_LIST_TITLE = "title";
    private static final String COLUMN_LIST_DATE = "date";
    private static final String COLUMN_LIST_TYPE = "type";

    public DBHandler (Context context, SQLiteDatabase.CursorFactory factory){
        // call superclass constructor
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Define string for create statement for shopping list table
        String query = "CREATE TABLE " + TABLE_REMINDER_LIST + "("  +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_TITLE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT, " +
                COLUMN_LIST_TYPE + " TEXT " +
                ");";

        // execute the create statement
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // execute a drop statement that drops the shopping list table from the database
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER_LIST);

        // call method that creates the table
        onCreate(sqLiteDatabase);
    }

    public void addReminder (String title, String date, String type){

        //get writable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        //initialize an empty ContentValues object
        ContentValues values = new ContentValues();

        // put key value pairs in the ContentValues object
        // the key must be the name of a column and the value
        // is the value to be inserted in the column
        values.put(COLUMN_LIST_TITLE, title);
        values.put(COLUMN_LIST_DATE, date);
        values.put(COLUMN_LIST_TYPE, type);

        //insert values into the shopping list table
        db.insert(TABLE_REMINDER_LIST, null, values);

        //close the reference to the shopper database
        db.close();
    }

    public Cursor getReminders(){

        SQLiteDatabase db = getWritableDatabase();


        return db.rawQuery("SELECT * FROM " + TABLE_REMINDER_LIST, null);
    }

    public String getReminderTitle(int id) {

        //get writable reference to shopper database
        SQLiteDatabase db = getWritableDatabase();

        String dbString = "";

        //create sql string that will get the shopping list name
        String query = "SELECT * FROM " + TABLE_REMINDER_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        //execute select statement and store result in cursor
        Cursor cursor = db.rawQuery(query, null);

        //move to the first row in the cursor
        cursor.moveToFirst();

        //check to make sure there's a shopping list name value
        if (cursor.getString(cursor.getColumnIndex("title")) != null) {
            //store it in the String that will be returned by the method
            dbString = cursor.getString(cursor.getColumnIndex("title"));
        }

        db.close();

        return dbString;
    }

}
