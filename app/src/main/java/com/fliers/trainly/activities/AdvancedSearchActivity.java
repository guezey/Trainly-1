package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fliers.trainly.R;

public class AdvancedSearchActivity extends AppCompatActivity {

    private Button discard, search;
    private RadioGroup direction, location, wagon;
    private RadioButton onlyForward, onlyBack, onlyWindow, onlyCorridor, onlyEconomy, onlyBusiness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        discard = (Button) findViewById(R.id.advancedSearchDiscardButton);
        search = (Button) findViewById(R.id.advancedSearchButton);

        direction = (RadioGroup) findViewById(R.id.seatDirectionRadio);
        onlyBack = (RadioButton) findViewById(R.id.onlyBackwardRadio);
        onlyForward = (RadioButton) findViewById(R.id.onlyForwardRadio);

        location = (RadioGroup) findViewById(R.id.seatLocationRadio);
        onlyWindow = (RadioButton) findViewById(R.id.onlyWindowRadio);
        onlyCorridor = (RadioButton) findViewById(R.id.onlyCorridorRadio);

        wagon = (RadioGroup) findViewById(R.id.wagonTypeRadio);
        onlyEconomy = (RadioButton) findViewById(R.id.onlyEconomyRadio);
        onlyBusiness = (RadioButton) findViewById(R.id.onlyBusinessRadio);

        discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discardAndReturn();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO filter and search tickets
            }
        });

    }

    public void discardAndReturn() {
        direction.clearCheck();
        location.clearCheck();
        wagon.clearCheck();
        EditText ticketNum = (EditText) findViewById(R.id.editTicketNumber);
        ticketNum.setText("");
        finish();
    }
}