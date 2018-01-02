package com.example.android.quidditchworldcup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int scoreGryffindor=0;
    int scoreRavenclaw=0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //GRYFFINDOR program -----------------------------------------------------------------------------------
    /**
     * This method is called when the Quaffle button is clicked.
     */
    public void add10forGryffindor(View view){
        scoreGryffindor=scoreGryffindor+10;
        displayForGryffindor(scoreGryffindor);
    }

    /**
     * This method is called when the Snitch button is clicked.
     */
    public void add250forGryffindor(View view){
        scoreGryffindor=scoreGryffindor+250;
        displayForGryffindor(scoreGryffindor);
        Toast.makeText(this, "Gryffindor wins!", Toast.LENGTH_SHORT).show();

    }

    /**
     * Displays the given score for Gryffindor
     */
    public void displayForGryffindor(int score) {
        TextView scoreView = (TextView) findViewById(R.id.gryffindor_score);
        scoreView.setText(String.valueOf(score));
    }



    //RAVENCLAV program -----------------------------------------------------------------------------------
    /**
     * This method is called when the Quaffle button is clicked.
     */
    public void add10forRavenclaw(View view){
        scoreRavenclaw=scoreRavenclaw+10;
        displayForRavenclaw(scoreRavenclaw);
    }

    /**
     * This method is called when the Snitch button is clicked.
     */
    public void add250forRavenclaw(View view){
        scoreRavenclaw=scoreRavenclaw+250;
        displayForRavenclaw(scoreRavenclaw);
        Toast.makeText(this, "Ravenclaw wins!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Displays the given score for Ravenclaw
     */
    public void displayForRavenclaw(int score) {
        TextView scoreView = (TextView) findViewById(R.id.ravenclaw_score);
        scoreView.setText(String.valueOf(score));
    }

    public void reset(View view) {
        scoreGryffindor=0;
        scoreRavenclaw=0;

        displayForGryffindor(scoreGryffindor);
        displayForRavenclaw(scoreRavenclaw);
    }
}
