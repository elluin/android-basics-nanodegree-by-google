package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/* API Contract for the Habit app.*/

public final class HabitContract {

    private HabitContract() {
    }

    /**
     * Inner class that defines constant values for the habit database table.
     * Each entry in the table represents a single training.
     */
    public static final class HabitEntry implements BaseColumns {

        /* Name of database table for habits */
        public final static String TABLE_NAME = "habits";

        /* Unique ID number for the habit/training. Type: INTEGER */
        public final static String _ID = BaseColumns._ID;

        /* Date of the habit/training. Type: TEXT */
        public final static String COLUMN_HABIT_DATE = "date";

        /* Distance of habit/training. Type: INTEGER */
        public final static String COLUMN_HABIT_DISTANCE = "distance";

        /* Type of the habit/training. Type: INTEGER
         * The only possible values are {@link #TYPE_LONG}, {@link #TYPE_SHORT},
         * or {@link #TYPE_START}.*/
        public final static String COLUMN_HABIT_TYPE = "type";

        /* Comment. Type: TEXT */
        public final static String COLUMN_HABIT_COMMENT = "comment";

        /*Possible values for the type of habit/training.*/
        public static final int TYPE_LONG = 0;
        public static final int TYPE_SHORT = 1;
        public static final int TYPE_START = 2;
    }

}

