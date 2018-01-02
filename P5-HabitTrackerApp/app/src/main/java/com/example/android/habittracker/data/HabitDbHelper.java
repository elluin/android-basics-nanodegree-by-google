package com.example.android.habittracker.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.data.HabitContract.HabitEntry;

import static android.R.attr.type;

/**
 * Database helper for Habits app. Manages database creation and version management.
 */
public class HabitDbHelper extends SQLiteOpenHelper {

    /*Name of the database file*/
    private static final String DATABASE_NAME = "dragonboat.db";

    /*Database version. If you change the database schema, you must increment the database version.*/
    private static final int DATABASE_VERSION = 1;

    /*Constructs a new instance of {@link HabitDbHelper} @param context of the app*/
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*This is called when the database is created for the first time.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the habits table
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_DATE + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_DISTANCE + " INTEGER, "
                + HabitEntry.COLUMN_HABIT_TYPE + " INTEGER, "
                + HabitEntry.COLUMN_HABIT_COMMENT + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    /*This is called when the database needs to be upgraded.*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No need in this version
    }

    /**
     * Inserts a habit into habits database table using the given arguments
     *
     * @param date     the date of the habit/training
     * @param distance the distance in kilometer of the habit/training
     * @param type  the type of  the habit/training (long or short or start)
     * @param comment  my comment for the habit/training
     */
    public void insertHabit(String date, int distance, int type, String comment) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_DATE, date);
        values.put(HabitEntry.COLUMN_HABIT_DISTANCE, distance);
        values.put(HabitEntry.COLUMN_HABIT_TYPE, type);
        values.put(HabitEntry.COLUMN_HABIT_COMMENT, comment);

        db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    /**
     * Queries the habits from the corresponding database table
     * @return the Cursor object containing the result of the query
     */
    public Cursor queryHabits() {
        Cursor cursor;

        SQLiteDatabase db = getReadableDatabase();

        // Defining the columns we need to retrieve
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_DATE,
                HabitEntry.COLUMN_HABIT_DISTANCE,
                HabitEntry.COLUMN_HABIT_TYPE,
                HabitEntry.COLUMN_HABIT_COMMENT};

        // Query database for habits table with the given column names without any specific
        // selection, order or grouping
        cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,              // The columns to return
                null,                    // The columns for the WHERE clause
                null,                    // The values for the WHERE clause
                null,                    // Don't group the rows
                null,                    // Don't filter by row groups
                null);                   // The sort order

        return cursor;
    }
}



