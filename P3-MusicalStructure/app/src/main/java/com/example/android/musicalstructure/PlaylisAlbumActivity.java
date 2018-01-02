package com.example.android.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PlaylisAlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlis_album);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

// album item's code -------------------------------------------------------------------------
        // Find the View that shows the plus_button category
        TextView item = (TextView) findViewById(R.id.album_list_item);

        // Set a click listener on that View
        item.setOnClickListener(new View.OnClickListener()

        {
            // The code in this method will be executed when the colors category is clicked on.

            @Override
            public void onClick(View view) {

                // Create a new intent to open the {@link ColorsActivity}
                Intent artistIntent = new Intent(PlaylisAlbumActivity.this, PlaylistArtistActivity.class);

                // Start the new activity
                startActivity(artistIntent);
            }
        });
    }

    /**
     * On selecting toolbar icons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.equalizer:
                equalizerActivity();
                return true;
            case R.id.search:
                searchActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void equalizerActivity() {
        Intent i = new Intent(PlaylisAlbumActivity.this, EqualizerActivity.class);
        startActivity(i);
    }

    private void searchActivity() {
        Intent i = new Intent(PlaylisAlbumActivity.this, SearchActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
