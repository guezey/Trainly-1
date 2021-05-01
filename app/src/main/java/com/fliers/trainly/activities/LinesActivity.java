package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.fliers.trainly.R;

import java.awt.Button;

import javax.swing.text.View;

public class LinesActivity extends AppCompatActivity {

    private TextView textView20;
    private TextView textView21;
    private EditText editTextTextPersonName2;
    private EditText editTextTextPersonName3;
    private Textview x1;
    private TextView y1;
    private TextView x2;
    private TextView y2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);

        textView20 = (TextView) findByViewId(R.id.textView20);
        textView21 = (TextView) findbyViewId(R.id.textView21);
        editTextTextPersonName2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        button = findViewById(R.id.button);
    }

    button.setOnClickListener( new View.OnClickListener() {

        public void onClick(View view) {

            if( editTextTextPersonName2.equals("") || editTextTextPersonName2.equals("Place 1") || editTextTextPersonName3.equals("") || editTextTextPersonName3.equals("Place 2")) {

                editTextTextPersonName2.setError("Invalid Input");
                editTextTextPersonName3.setError("Invalid Input");
            }
            else if(editTextTextPersonName2.equals("") || editTextTextPersonName2.equals("Place 1")) {

                editTextTextPersonName2.setError("Invalid Input");
            }
            else if( editTextTextPersonName3.equals("") || editTextTextPersonName3.equals("Place 2")) {

                editTextTextPersonName3.setError("Invalid Input");
            }

            else{

                //TO-DO
            }
        }
    });

}