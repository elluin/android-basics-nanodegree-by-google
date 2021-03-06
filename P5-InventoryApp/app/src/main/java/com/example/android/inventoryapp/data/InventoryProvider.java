package com.example.android.inventoryapp.data;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import static com.example.android.inventoryapp.data.InventoryContract.CONTENT_AUTHORITY;
import static com.example.android.inventoryapp.data.InventoryContract.PATH_PRODUCTS;


public class InventoryProvider extends ContentProvider {

    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    //URI matcher code
    private static final int PRODUCTS = 100;
    private static final int PRODUCT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS, PRODUCTS);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_PRODUCTS + "/#", PRODUCT_ID);
    }

    private InventoryDbHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new InventoryDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDatabaseHelper.getReadableDatabase();

        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                // The cursor could contain multiple rows of the pets table.
                cursor = database.query(InventoryEntry.PRODUCTS_TABLE_NAME,
                        projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case PRODUCT_ID:
                //Containing only  the actual ID
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryEntry.PRODUCTS_TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return insertProduct(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    //Insert a product into the database with the given content values.
    private Uri insertProduct(Uri uri, ContentValues values) {

        String productName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);
        if (productName == null) {
            throw new IllegalArgumentException("Product requires a name.");
        }

        Integer price = values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_PRICE);
        if (price != null && price < 0) {
            throw new IllegalArgumentException("Product requires valid price.");
        }

        Integer quantity = values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
        if (quantity != null && quantity < 0) {
            throw new IllegalArgumentException("Product requires valid quantity.");
        }

        String emailOfSupplier = values.getAsString(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
        if (emailOfSupplier == null) {
            throw new IllegalArgumentException("Product requires an email.");
        }

        String productImage = values.getAsString(InventoryEntry.COLUMN_PRODUCT_IMAGE);
        if (productImage == null) {
            throw new IllegalArgumentException("Product requires an image.");
        }

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        long id = database.insert(InventoryEntry.PRODUCTS_TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }//end of insertProduct

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return updateProduct(uri, contentValues, selection, selectionArgs);
            case PRODUCT_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateProduct(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(InventoryEntry.COLUMN_PRODUCT_NAME)) {
            String productName = values.getAsString(InventoryEntry.COLUMN_PRODUCT_NAME);
            if (productName == null) {
                throw new IllegalArgumentException("Product requires a name.");
            }
        }

        if (values.containsKey(InventoryEntry.COLUMN_PRODUCT_PRICE)) {
            Integer price = values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_PRICE);
            if (price != null && price < 0) {
                throw new IllegalArgumentException("Product requires valid price.");
            }
        }

        if (values.containsKey(InventoryEntry.COLUMN_PRODUCT_QUANTITY)) {
            Integer quantity = values.getAsInteger(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            if (quantity != null && quantity < 0) {
                throw new IllegalArgumentException("Product requires valid quantity.");
            }
        }

        if (values.containsKey(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL)) {
            String emailOfSupplier = values.getAsString(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
            if (emailOfSupplier == null) {
                throw new IllegalArgumentException("Product requires an email address.");
            }
        }

        if (values.containsKey(InventoryEntry.COLUMN_PRODUCT_IMAGE)) {
            String imageUriString = values.getAsString(InventoryEntry.COLUMN_PRODUCT_IMAGE);

            // Check if image uri string is null
            if (imageUriString == null) {
                throw new IllegalArgumentException("Must choose an image");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        int rowsUpdated = database.update(InventoryEntry.PRODUCTS_TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }//end of updateProduct

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = mDatabaseHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                rowsDeleted = database.delete(InventoryEntry.PRODUCTS_TABLE_NAME, selection, selectionArgs);
                break;
            case PRODUCT_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(InventoryEntry.PRODUCTS_TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return InventoryEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
