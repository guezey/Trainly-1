package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Schedule;
import com.fliers.trainly.models.Wagon;

import java.util.ArrayList;

public class TicketInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Schedule schedule;
    ConstraintLayout economyWagon;
    ConstraintLayout businessWagon;
    TextView tvWagonOfSeat;
    TextView tvSeatNoOfTicketValue;
    TextView tvPriceOfTicketValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);

        Intent intent = getIntent();
        schedule = Schedule.getTempInstance();
        Spinner spinnerWagons = findViewById(R.id.spinnerWagon);
        TextView tvTicketTitle = findViewById(R.id.tvTicketTitle);
        tvWagonOfSeat = findViewById(R.id.tvWagonOfSeat);
        tvSeatNoOfTicketValue = findViewById(R.id.tvSeatNoOfTicketValue);
        tvPriceOfTicketValue = findViewById(R.id.tvPriceOfTicketValue);
        TextView tvLine = findViewById(R.id.tvLine);
        TextView tvDep = findViewById(R.id.tvDepSchedule);
        TextView tvArr = findViewById(R.id.tvArrSchedule);
        TextView tvPrices = findViewById(R.id.tvPricesOfSchedule);
        economyWagon = findViewById(R.id.layoutWagonEconomy);
        businessWagon = findViewById(R.id.layoutWagonBusiness);

        tvTicketTitle.setText( schedule.getLinkedTrain().getLinkedCompany().getName());
        tvLine.setText( schedule.getLine().toString());
        tvDep.setText("Departure: " + Schedule.calendarToString( schedule.getDepartureDate()));
        tvArr.setText("Arrival       " + Schedule.calendarToString( schedule.getArrivalDate()));
        tvPrices.setText("*Economy class wagon seats: $" + String.valueOf( schedule.getEconomyPrice())
                        + "\n*Business class wagon seats: $" + String.valueOf( schedule.getBusinessPrice()));
        tvWagonOfSeat.setText( "Wagon: ");
        tvSeatNoOfTicketValue.setText( "");
        tvPriceOfTicketValue.setText( "");

        ArrayList<Wagon> wagons = schedule.getWagons();
        ArrayList<String> spinnerItems = new ArrayList<>();

        String item;
        for ( int i = 0; i < wagons.size(); i++ ) {
            item = "Wagon " + String.valueOf(i + 1);
            if (wagons.get(i).isBusiness())
                item = item + " (Business class)";
            else
                item = item + " (Economy class)";
            spinnerItems.add( item);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_wagons, spinnerItems){
            /**
             * Shows spinner items.
             * @param position
             * @param convertView
             * @param parent
             * @return
             */
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                return super.getDropDownView(position, convertView, parent);
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_wagons);
        spinnerWagons.setAdapter(spinnerArrayAdapter);

        spinnerWagons.setOnItemSelectedListener( this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        try {
            tvWagonOfSeat.setText( "Wagon: " + ( pos + 1));

            if ( pos < schedule.getLinkedTrain().getBusinessWagonNum()) {
                economyWagon.setVisibility(View.GONE);
                businessWagon.setVisibility(View.VISIBLE);


            }
            else {
                economyWagon.setVisibility(View.VISIBLE);
                businessWagon.setVisibility(View.GONE);
            }
        }
        catch (Exception e) {
            Log.d( "APP_DEBUG", e.toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}