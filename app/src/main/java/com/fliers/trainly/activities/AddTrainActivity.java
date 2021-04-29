package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.fliers.trainly.R;

import java.awt.Button;

public class AddTrainActivity extends AppCompatActivity {

    private Spinner placeSpinner;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train);

        placeSpinner = (Spinner) findViewById(R.id.lineSpinner);
        add = findViewById(R.id.buttonAdd);
    }

    add.setOnClickListener( new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switchToTrainDetails();
        }
    });
}