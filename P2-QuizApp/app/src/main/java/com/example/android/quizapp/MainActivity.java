package com.example.android.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private float result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculate the result, used in onButtonPressed method
     */
    public void calculate() {

        //QUESTION 1 .........................................................................
        //a1 means answer for question 1
        EditText a1 = (EditText) findViewById(R.id.a1_edittext);
        String a1String = a1.getText().toString();
        if (a1String.equalsIgnoreCase("intranet")) {
            result += 1;
            a1.setTextColor(Color.GREEN);
        } else if (a1String.equals("")) {
            result += 0;
        } else {
            result -= 0.5;
            a1.setTextColor(Color.RED);
        }

        //QUESTION 2 ........................................................................
        //a2a means answer "a" for question 2
        //TRUE
        RadioButton a2a = (RadioButton) findViewById(R.id.a2a_radiobutton);
        if (a2a.isChecked()) {
            result += 1;
            a2a.setTextColor(Color.GREEN);
        }
        //FALSE
        RadioButton a2b = (RadioButton) findViewById(R.id.a2b_radiobutton);
        RadioButton a2c = (RadioButton) findViewById(R.id.a2c_radiobutton);
        RadioButton a2d = (RadioButton) findViewById(R.id.a2d_radiobutton);
        RadioButton a2e = (RadioButton) findViewById(R.id.a2e_radiobutton);
        if (a2b.isChecked()) {
            result -= 0.5;
            a2b.setTextColor(Color.RED);
        }
        if (a2c.isChecked()) {
            result -= 0.5;
            a2c.setTextColor(Color.RED);
        }
        if (a2d.isChecked()) {
            result -= 0.5;
            a2d.setTextColor(Color.RED);
        }
        if (a2e.isChecked()) {
            result -= 0.5;
            a2e.setTextColor(Color.RED);
        }

        //QUESTION 3 .........................................................................
        //TRUE
        CheckBox a3a = (CheckBox) findViewById(R.id.a3a_checkbox);
        if (a3a.isChecked()) {
            result += 1;
            a3a.setTextColor(Color.GREEN);
        }
        CheckBox a3c = (CheckBox) findViewById(R.id.a3c_checkbox);
        if (a3c.isChecked()) {
            result += 1;
            a3c.setTextColor(Color.GREEN);
        }
        //FALSE
        CheckBox a3b = (CheckBox) findViewById(R.id.a3b_checkbox);
        if (a3b.isChecked()) {
            result -= 0.5;
            a3b.setTextColor(Color.RED);
        }
        CheckBox a3d = (CheckBox) findViewById(R.id.a3d_checkbox);
        if (a3d.isChecked()) {
            result -= 0.5;
            a3d.setTextColor(Color.RED);
        }

        //QUESTION 4 .........................................................................
        RadioButton a4b = (RadioButton) findViewById(R.id.a4b_radiobutton);
        if (a4b.isChecked()) {
            result += 1;
            a4b.setTextColor(Color.GREEN);
        }
        //FALSE
        RadioButton a4a = (RadioButton) findViewById(R.id.a4a_radiobutton);
        RadioButton a4c = (RadioButton) findViewById(R.id.a4c_radiobutton);
        RadioButton a4d = (RadioButton) findViewById(R.id.a4d_radiobutton);

        if (a4a.isChecked()) {
            result -= 0.5;
            a4a.setTextColor(Color.RED);
        }
        if (a4c.isChecked()) {
            result -= 0.5;
            a4c.setTextColor(Color.RED);
        }
        if (a4d.isChecked()) {
            result -= 0.5;
            a4d.setTextColor(Color.RED);
        }

        //QUESTION 5 .........................................................................
        EditText a5 = (EditText) findViewById(R.id.a5_edittext);
        String a5String = a5.getText().toString();
        if (a5String.equalsIgnoreCase("BYOD")) {
            result += 1;
            a5.setTextColor(Color.GREEN);
        } else if (a5String.equals("")) {
            result += 0;
        } else {
            result -= 0.5;
            a5.setTextColor(Color.RED);
        }

        //QUESTION 6 .........................................................................
        //TRUE
        CheckBox a6c = (CheckBox) findViewById(R.id.a6c_checkbox);
        if (a6c.isChecked()) {
            result += 1;
            a6c.setTextColor(Color.GREEN);
        }
        CheckBox a6d = (CheckBox) findViewById(R.id.a6d_checkbox);
        if (a6d.isChecked()) {
            result += 1;
            a6d.setTextColor(Color.GREEN);
        }
        //FALSE
        CheckBox a6a = (CheckBox) findViewById(R.id.a6a_checkbox);
        if (a6a.isChecked()) {
            result -= 0.5;
            a6a.setTextColor(Color.RED);
        }
        CheckBox a6b = (CheckBox) findViewById(R.id.a6b_checkbox);
        if (a6b.isChecked()) {
            result -= 0.5;
            a6b.setTextColor(Color.RED);
        }

        //QUESTION 7 .........................................................................
        //TRUE
        RadioButton a7d = (RadioButton) findViewById(R.id.a7d_radiobutton);
        if (a7d.isChecked()) {
            result += 1;
            a7d.setTextColor(Color.GREEN);
        }
        //FALSE
        RadioButton a7a = (RadioButton) findViewById(R.id.a7a_radiobutton);
        RadioButton a7b = (RadioButton) findViewById(R.id.a7b_radiobutton);
        RadioButton a7c = (RadioButton) findViewById(R.id.a7c_radiobutton);
        if (a7a.isChecked() || a7b.isChecked() || a7c.isChecked()) {
            result -= 0.5;
        }
        if (a7a.isChecked()) {
            result -= 0.5;
            a7a.setTextColor(Color.RED);
        }
        if (a7b.isChecked()) {
            result -= 0.5;
            a7b.setTextColor(Color.RED);
        }
        if (a7c.isChecked()) {
            result -= 0.5;
            a7c.setTextColor(Color.RED);
        }
    }

    /*
    * Show the results, in point and percent
    */
    public void message(){
        if(result<3)
            Toast.makeText(this, "Not bad, but learn more!\n" + result + " point, "+String.format("%.1f", result/9*100)+" %", Toast.LENGTH_SHORT).show();
        else if(result<7)
            Toast.makeText(this, "Good!\n" + result + " point, "+String.format("%.1f", result/9*100)+" %", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Ecellent!\n" + result + " point, "+String.format("%.1f", result/9*100)+" %", Toast.LENGTH_SHORT).show();

        result=0;
    }

    /**
     * Reload app, used in onButtonPressed method
     */
    public void reset() {
        result = 0;
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     * Called when a button is pressed
     */
    public void onButtonPressed(View myButton) {

        switch (myButton.getId()) {
            case R.id.submitButton:
                calculate();
                message();
                break;
            case R.id.resettButton:
                reset();
                break;
        }
    }

}


