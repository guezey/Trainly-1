package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.fliers.trainly.R;

import java.awt.Button;

import javax.swing.text.View;

public class AddTrainActivity extends AppCompatActivity {

    private Spinner departurePlaceSpinner;
    private Spinner arrivalPlaceSpinner;
    private TextView txtBusinessWagonNum;
    private TextView txtEconomyWagonNum;
    Button goDetails;
    Button addTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train);

        departurePlaceSpinner = (Spinner) findViewById(R.id.departurePlaceSpinner);
        arrivalPlaceSpinner = (Spinner) findViewById(R.id.arrivalPlaceSpinner);
        txtBusinessWagonNum = (TextView) findViewById(R.id.textView30);
        txtEconomyWagonNum = (TextView) findViewById(R.id.textView31);
        goDetails = findViewById(R.id.buttonGoDetails);
    }

    goDetails.setOnClickListener( new View.OnClickListener() {

        public void onClick(View view) {
            switchToTrainDetails();
        }
    });

    addTrain.setOnClickListener( new View.OnClickListener() {

        public void onClick(View view) {
            goToManageTrains();
        }
    });

    public void goToManageTrains() {





        Intent switchActivityIntent = new Intent(this, TrainsActivity.class);
        startActivity(switchActivityIntent);
    }

    public void switchToTrainDetails() {

        txtBusinessWagonNum = (TextView) findViewById(R.id.textView30);
        txtEconomyWagonNum = (TextView) findViewById(R.id.textView31);
        txtBusinessWagonNum.setText("0");
        txtEconomyWagonNum.setText("0");
        addTrain = findViewById(R.id.buttonAddTrain);
    }
}