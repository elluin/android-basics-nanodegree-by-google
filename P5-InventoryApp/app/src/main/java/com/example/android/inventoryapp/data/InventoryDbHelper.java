package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public InventoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //This is called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " + InventoryEntry.PRODUCTS_TABLE_NAME + "("
                + InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InventoryEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL, "
                + InventoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL, "
                + InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL + " TEXT NOT NULL, "
                + InventoryEntry.COLUMN_PRODUCT_IMAGE + " TEXT NOT NULL);";

        // Execute the SQL statement above
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
    }

    //This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + InventoryEntry.PRODUCTS_TABLE_NAME);
        onCreate(db);
    }
}
