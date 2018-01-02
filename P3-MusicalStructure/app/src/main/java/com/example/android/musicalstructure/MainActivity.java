package com.example.android.musicalstructure;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // plus button code:
        // Find the View that shows the plus_button category
        Button album = (Button) findViewById(R.id.plus_button);

        // Set a click listener on that View
        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to open the playlist
                Intent albumIntent = new Intent(MainActivity.this, PlaylisAlbumActivity.class);
                // Start the new activity
                startActivity(albumIntent);
            }
        });
    } // end of onCreate method

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
        Intent i = new Intent(MainActivity.this, EqualizerActivity.class);
        startActivity(i);
    }

    private void searchActivity() {
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
