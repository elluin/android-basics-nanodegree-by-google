package com.example.android.inventoryapp;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

public class InventoryCursorAdapter extends CursorAdapter {

    private Context mContext;

    public InventoryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        String productName = cursor.getString(nameColumnIndex);
        nameTextView.setText(productName);

        TextView priceTextView = (TextView) view.findViewById(R.id.price);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
        int productPrice = cursor.getInt(priceColumnIndex);
        priceTextView.setText(String.valueOf(productPrice));

        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
        int productQuantity = cursor.getInt(quantityColumnIndex);
        quantityTextView.setText(String.valueOf(productQuantity));

        final int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
        final int productId = cursor.getInt(idColumnIndex);

        view.setOnClickListener(openProductDetailsAction(productId));

        Button saleProductButton = (Button) view.findViewById(R.id.button_sale);
        saleProductButton.setOnClickListener(sellOneItemAction(quantityTextView, productId));
    }

    private View.OnClickListener openProductDetailsAction(final int productId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                Uri contentProductUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, productId);
                intent.setData(contentProductUri);
                mContext.startActivity(intent);
            }
        };
    }

    private View.OnClickListener sellOneItemAction(final TextView quantityTextView, final int productId) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(quantityTextView.getText().toString());

                if (0 == quantity) {
                    Toast.makeText(mContext, R.string.message_empty_stock,
                            Toast.LENGTH_SHORT).show();
                } else if (quantity > 0) {
                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantity - 1);

                    Uri currentProductUri =
                            ContentUris.withAppendedId(InventoryEntry.CONTENT_URI, productId);

                    int rowsAffected =
                            mContext.getContentResolver().update(currentProductUri, values, null, null);

                    if (0 != rowsAffected) {
                        quantityTextView.setText(Integer.toString(quantity - 1));
                    } else {
                        Toast.makeText(mContext, R.string.message_update_error, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }
}
