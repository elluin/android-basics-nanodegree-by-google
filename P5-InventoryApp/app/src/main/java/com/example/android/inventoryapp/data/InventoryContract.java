package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

    private InventoryContract() {
    }

    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PRODUCTS = "products";

    public static class InventoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;

        //Table name
        public final static String PRODUCTS_TABLE_NAME = "products";
        //Fields name
        //Unique ID - Type: integer
        public final static String _ID = BaseColumns._ID;
        //Name - Type: text
        public final static String COLUMN_PRODUCT_NAME = "name";
        //Price - Type: integer
        public final static String COLUMN_PRODUCT_PRICE = "price";
        //Quantity - Type integer
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        //Supplier's email - text
        public final static String COLUMN_PRODUCT_SUPPLIER_EMAIL = "supplier_email";
        //Product's photo - text
        public final static String COLUMN_PRODUCT_IMAGE = "image";

    } //end of InventoryEntry


}//end of InventoryContract