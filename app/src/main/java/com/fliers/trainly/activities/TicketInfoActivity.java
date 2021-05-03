package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Schedule;
import com.fliers.trainly.models.Wagon;

import java.util.ArrayList;

public class TicketInfoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Schedule schedule;
    View economyWagon;
    View businessWagon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);

        Intent intent = getIntent();
        schedule = (Schedule) intent.getSerializableExtra("schedule");
        Spinner spinnerWagons = findViewById(R.id.spinnerWagon);
        TextView tvLine = findViewById(R.id.tvLine);
        TextView tvDep = findViewById(R.id.tvDepSchedule);
        TextView tvArr = findViewById(R.id.tvArrSchedule);
        TextView tvPrices = findViewById(R.id.tvPricesOfSchedule);
        economyWagon = findViewById(R.id.includeEconomy);
        businessWagon = findViewById(R.id.includeBusiness);

        tvLine.setText(schedule.getLine().toString());
        tvDep.setText("Departure: " + schedule.getDepartureDate().toString());
        tvArr.setText("Arrival       " + schedule.getArrivalDate().toString());
        tvPrices.setText("*Economy class wagon seats: " + String.valueOf(schedule.getEconomyPrice())
                        + "$\n*Business class wagon seats: " + String.valueOf(schedule.getBusinessPrice()) + "$");

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
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        TextView tvWagonNo = findViewById(R.id.tvWagonOfSeat);
        tvWagonNo.setText("Wagon no: " + String.valueOf( pos + 1));

        if ( pos < schedule.getLinkedTrain().getBusinessWagonNum()) {
            economyWagon.setVisibility(View.GONE);
            businessWagon.setVisibility(View.VISIBLE);


        }
        else {
            economyWagon.setVisibility(View.VISIBLE);
            businessWagon.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}