package com.fliers.trainly.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Line;
import com.fliers.trainly.models.Train;
import com.fliers.trainly.models.User;

import java.util.ArrayList;

/**
* Add Train Page
* A page for Companies to add train to their collections.
* @author Erkin Aydın
* @version 03.05.2021
*/

public class AddTrainActivity extends AppCompatActivity {

    Company currentUser;
    private ConstraintLayout minus1;
    private ConstraintLayout plus1;
    private ConstraintLayout minus2;
    private ConstraintLayout plus2;
    private TextView businessCounter;
    private TextView economyCounter;
    private EditText businessPrice;
    private EditText economyPrice;
    private ImageView back;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_train);

        currentUser = (Company) User.getCurrentUserInstance();
        minus1 = (ConstraintLayout) findViewById(R.id.idl321);
        plus1 = (ConstraintLayout) findViewById(R.id.idl31);
        minus2 = (ConstraintLayout) findViewById(R.id.idl322);
        plus2 = (ConstraintLayout) findViewById(R.id.idl32);
        businessCounter = (TextView) findViewById(R.id.textView412);
        economyCounter = (TextView) findViewById(R.id.textView42);
        businessPrice = (EditText) findViewById(R.id.editTextNumber);
        economyPrice = (EditText) findViewById(R.id.editTextNumber2);
        add = (Button) findViewById(R.id.button);
        back = (ImageView) findViewById(R.id.imageView2);

        minus1.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {

                int i = Integer.parseInt(businessCounter.getText().toString());
                if( i >= 1) {
                    i-=1;
                    businessCounter.setText( "" + i);
                }
            }
        });

        plus1.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {

                int i = Integer.parseInt(businessCounter.getText().toString());
                int j = Integer.parseInt(economyCounter.getText().toString());
                if( i + j < 30) {
                    i += 1;
                    businessCounter.setText("" + i);
                }
            }
        });

        minus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = Integer.parseInt(economyCounter.getText().toString());
                if( i > 0) {
                     i -= 1;
                    economyCounter.setText( "" + i);
                }
            }
        });

        plus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = Integer.parseInt(economyCounter.getText().toString());
                int j = Integer.parseInt(businessCounter.getText().toString());
                if( i + j < 30) {
                    i += 1;
                    economyCounter.setText("" + i);
                }
            }
        });

        businessPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(businessPrice.getText().toString().trim().equalsIgnoreCase("")||
                        economyPrice.getText().toString().trim().equalsIgnoreCase(""))) {
                    if (Double.parseDouble(businessPrice.getText().toString()) <= Double.parseDouble(economyPrice.getText().toString())) {

                        businessPrice.setError("Are You Sure About That?");
                    }
                }
            }
        });

        economyPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(businessPrice.getText().toString().trim().equalsIgnoreCase("")||
                        economyPrice.getText().toString().trim().equalsIgnoreCase(""))) {
                    if (Double.parseDouble(businessPrice.getText().toString()) <= Double.parseDouble(economyPrice.getText().toString())) {

                        businessPrice.setError("Are You Sure About That?");
                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int businessNum = Integer.parseInt( businessCounter.getText().toString());
                int economyNum = Integer.parseInt( economyCounter.getText().toString());
                double bPrice = Double.parseDouble( businessPrice.getText().toString());
                double ePrice = Double.parseDouble( economyPrice.getText().toString());
                ArrayList<Train> currentTrains = currentUser.getTrains();
                int biggestId = 0;
                for( Train a : currentTrains) {
                    if( biggestId < Integer.parseInt(a.getId())) {
                        biggestId = Integer.parseInt(a.getId());
                    }
                }
                String idRoot = biggestId + "";
                String addId = "";
                for( int i = idRoot.length(); i < 3; i++) {
                    addId += 0;
                }
                addId += idRoot;
                Train newTrain = new Train( currentUser, businessNum, economyNum, bPrice, ePrice, addId);
                currentUser.addTrain( newTrain);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
