package com.example.android.habittracker;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplayView;
    private HabitDbHelper mHabitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHabitDbHelper = new HabitDbHelper(this);
        mDisplayView = (TextView) findViewById(R.id.textview_list);

        HabitDbHelper databaseHelper = new HabitDbHelper(this);

        addHabits(databaseHelper);
        displayDatabaseContent(databaseHelper);
    }

    private void addHabits(HabitDbHelper databaseHelper) {
        databaseHelper.insertHabit("20170711", 7, 0, "Hot, windy weather");
        databaseHelper.insertHabit("20170713", 9, 0, "New paddle");
        databaseHelper.insertHabit("20170716", 6, 1, "");
    }


    private void displayDatabaseContent(HabitDbHelper databaseHelper) {
        Cursor cursor = mHabitDbHelper.queryHabits();

        int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
        int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DATE);
        int distanceColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DISTANCE);
        int typeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TYPE);
        int commentColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_COMMENT);

        String displayDatabaseContent = "";
        cursor.moveToFirst();
        do {
            int id = cursor.getInt(idColumnIndex);
            String date = cursor.getString(dateColumnIndex);
            int distance = cursor.getInt(distanceColumnIndex);
            int type = cursor.getInt(typeColumnIndex);
            String comment = cursor.getString(commentColumnIndex);
            displayDatabaseContent = displayDatabaseContent + id + " - " + date + " - " +
                    distance + " - " + type + " - " + comment + "\n";
        } while (cursor.moveToNext());

        // Set header for displaying the habits
        mDisplayView.setText((HabitEntry._ID + " - " +
                HabitEntry.COLUMN_HABIT_DATE + " - " +
                HabitEntry.COLUMN_HABIT_DISTANCE + " - " +
                HabitEntry.COLUMN_HABIT_TYPE + " - " +
                HabitEntry.COLUMN_HABIT_COMMENT + "\n"));

        mDisplayView.append(displayDatabaseContent);
    }
}
