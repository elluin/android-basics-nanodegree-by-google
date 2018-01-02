package com.example.android.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.InventoryContract.InventoryEntry;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_INVENTORY_LOADER = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final String LOG_TAG = EditorActivity.class.getSimpleName();

    private ImageView mImageView;

    private Uri mCurrentProductUri;

    private Bitmap bitmap;

    // Uri of the image chosen
    private Uri mImageUri;

    //fields of activity_editor.xml
    private EditText mNameEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;
    private EditText mSupplierEmailEditText;

    private boolean mProductHasChanged = false;

    private View.OnTouchListener mChangeTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mImageView = (ImageView) findViewById(R.id.imageview_item_photo);

        // Get uri and bitmap on configuration change
        // if it is not equal null
        if (savedInstanceState != null) {
            mImageUri = savedInstanceState.getParcelable("Uri");
            bitmap = savedInstanceState.getParcelable("Bitmap");
            mImageView.setImageBitmap(bitmap);
        }

        Intent intent = getIntent();
        mCurrentProductUri = intent.getData();

        if (null == mCurrentProductUri) {
            setTitle(R.string.editor_activity_title_new_product);
        } else {
            setTitle(R.string.editor_activity_title_edit_product);
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER, null, this);
        }

        mNameEditText = (EditText) findViewById(R.id.edittext_product_name);
        mPriceEditText = (EditText) findViewById(R.id.edittext_product_price);
        mQuantityEditText = (EditText) findViewById(R.id.edittext_product_quantity);
        mSupplierEmailEditText = (EditText) findViewById(R.id.edittext_product_supplier);

        mNameEditText.setOnTouchListener(mChangeTouchListener);
        mPriceEditText.setOnTouchListener(mChangeTouchListener);
        mQuantityEditText.setOnTouchListener(mChangeTouchListener);
        mSupplierEmailEditText.setOnTouchListener(mChangeTouchListener);

        if (mImageUri != null) {
            mProductHasChanged = true;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_photo);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (null == mCurrentProductUri) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                // Insert Product
                if (saveProduct()) {
                    // Go back to CatalogActivity
                    finish();
                }
                return true;
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                // Get image from data
                mImageUri = data.getData();

                try {
                    // Convert image uri to a bitmap
                    InputStream imageStream = getContentResolver().openInputStream(mImageUri);

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap bitmap = BitmapFactory.decodeStream(imageStream, null, options);
                    imageStream.close();

                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {

                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error with loading image", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean saveProduct() {

        boolean allFilledOut = false;

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString = mNameEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();
        String emailString = mSupplierEmailEditText.getText().toString().trim();

        Uri imageUri = mImageUri;

        if (TextUtils.isEmpty(nameString) || TextUtils.isEmpty(priceString) || TextUtils.isEmpty(quantityString) || imageUri == null) {
            Toast.makeText(this, getString(R.string.editor_error_blank), Toast.LENGTH_SHORT).show();
            allFilledOut = false;
            return allFilledOut;
        } else {
            String imageUriString = imageUri.toString();
            ContentValues values = new ContentValues();
            values.put(InventoryEntry.COLUMN_PRODUCT_NAME, nameString);
            values.put(InventoryEntry.COLUMN_PRODUCT_PRICE, priceString);
            values.put(InventoryEntry.COLUMN_PRODUCT_QUANTITY, quantityString);
            values.put(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, emailString);
            values.put(InventoryEntry.COLUMN_PRODUCT_IMAGE, imageUriString);

            // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
            if (mCurrentProductUri == null) {
                // This is a NEW pet, so insert a new pet into the provider,
                // returning the content URI for the new pet.
                Uri newUri = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);

                // Show a toast message depending on whether or not the insertion was successful.
                if (newUri == null) {
                    // If the new content URI is null, then there was an error with insertion.
                    Toast.makeText(this, getString(R.string.editor_insert_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the insertion was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_insert_success),
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                int rowsAffected = getContentResolver().update(mCurrentProductUri, values, null, null);

                // Show a toast message depending on whether or not the update was successful.
                if (rowsAffected == 0) {
                    // If no rows were affected, then there was an error with the update.
                    Toast.makeText(this, getString(R.string.editor_update_failed),
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Otherwise, the update was successful and we can display a toast.
                    Toast.makeText(this, getString(R.string.editor_update_success),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        allFilledOut = true;
        return allFilledOut;
    }

    // Save uri and bitmap on configuration change
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("Uri", mImageUri);
        outState.putParcelable("Bitmap", bitmap);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void deleteProduct() {
        if (null != mCurrentProductUri) {
            int rowsDeleted = getContentResolver().delete(mCurrentProductUri, null, null);
            showToastIf(0 == rowsDeleted, R.string.editor_delete_failed,
                    R.string.editor_delete_success);
        }
        finish();
    }

    private void showToastMessage(int messageResourceId) {
        Toast.makeText(EditorActivity.this, getString(messageResourceId),
                Toast.LENGTH_SHORT).show();
    }

    private void showToastIf(boolean condition, int successStringId, int failureStringId) {
        int messageResourceId = condition ? successStringId : failureStringId;
        showToastMessage(messageResourceId);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {
                InventoryEntry._ID,
                InventoryEntry.COLUMN_PRODUCT_NAME,
                InventoryEntry.COLUMN_PRODUCT_PRICE,
                InventoryEntry.COLUMN_PRODUCT_QUANTITY,
                InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,
                InventoryEntry.COLUMN_PRODUCT_IMAGE};


        return new CursorLoader(this,
                mCurrentProductUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (null == cursor || 1 > cursor.getCount()) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
            String name = cursor.getString(nameColumnIndex);
            mNameEditText.setText(name);

            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_PRICE);
            int price = cursor.getInt(priceColumnIndex);
            mPriceEditText.setText(Integer.toString(price));

            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_QUANTITY);
            int quantity = cursor.getInt(quantityColumnIndex);
            mQuantityEditText.setText(Integer.toString(quantity));

            int supplierContactColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
            String supplierContact = cursor.getString(supplierContactColumnIndex);
            mSupplierEmailEditText.setText(supplierContact);

            int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_IMAGE);
            String imageUriString = cursor.getString(imageColumnIndex);
            mImageUri = Uri.parse(imageUriString);
            // Convert image uri to a bitmap
            try {
                InputStream imageStream = getContentResolver().openInputStream(Uri.parse(imageUriString));
                bitmap = BitmapFactory.decodeStream(imageStream);
                imageStream.close();

                mImageView.setImageBitmap(bitmap);
            } catch (IOException e) {

            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
        mSupplierEmailEditText.setText("");
        mImageView.setImageBitmap(null);
    }

    @Override
    public void onBackPressed() {
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_unsaved_changes_dialog);
        builder.setPositiveButton(R.string.option_discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.option_keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (null != dialog) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.message_delete_dialog);
        builder.setPositiveButton(R.string.option_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.option_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (null != dialog) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void increaseQuantity(View v) {
        int quantity = 0;
        String quantityText = mQuantityEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(quantityText)) {
            quantity = Integer.parseInt(quantityText);
        }
        mQuantityEditText.setText(String.valueOf(quantity + 1));
        mProductHasChanged = true;
    }

    public void decreaseQuantity(View v) {
        int quantity = 0;
        String quantityText = mQuantityEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(quantityText)) {
            quantity = Integer.parseInt(quantityText);
        }
        if (0 < quantity) {
            mQuantityEditText.setText(String.valueOf(quantity - 1));
            mProductHasChanged = true;
        }
    }

    public void orderProduct(View view) {
        String nameString = mNameEditText.getText().toString().trim();
        String supplierContactString = mSupplierEmailEditText.getText().toString().trim();

        Intent sendEmailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.fromParts("mailto", supplierContactString, null));
        sendEmailIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.email_subject);
        sendEmailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_message_before_product) +
                nameString + getString(R.string.email_message_after_product));

        if (null != sendEmailIntent.resolveActivity(getPackageManager())) {
            startActivity(sendEmailIntent);
        } else {
            Log.w(LOG_TAG, getString(R.string.error_no_email));
            showToastMessage(R.string.error_no_email_client);
        }
    }
}
