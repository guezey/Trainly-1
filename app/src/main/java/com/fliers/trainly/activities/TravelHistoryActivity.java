package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Customer;
import com.fliers.trainly.models.Schedule;
import com.fliers.trainly.models.Ticket;
import com.fliers.trainly.models.Tickets;

import java.util.ArrayList;

public class TravelHistoryActivity extends AppCompatActivity {


    ArrayList<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_history);
        ListView history = findViewById(R.id.history);
        ImageView btBack = findViewById(R.id.btBack);
        Button saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: save feedback
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Tickets ticketManager = new Tickets( getApplicationContext());
        tickets = ticketManager.getBoughtTickets((Customer) Customer.getCurrentUserInstance());

        if ( tickets.size() == 0) {
            Toast.makeText( getApplicationContext(), "No tickets found", Toast.LENGTH_SHORT).show();
        }
        else {
            TravelHistoryActivity.CustomAdaptor customAdaptor = new TravelHistoryActivity.CustomAdaptor();
            history.setAdapter( customAdaptor);
        }
    }


    class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return tickets.size();
        }

        @Override
        public Object getItem( int position) {
            return null;
        }

        @Override
        public long getItemId( int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate( R.layout.list_item_travel_history, null);
            Ticket ticket = tickets.get(position);
            Schedule schedule = ticket.getSeat().getLinkedWagon().getLinkedSchedule();

            // Get view
            TextView tvTicketTitle = view.findViewById(R.id.tvTicketTitle);
            TextView tvDirection = view.findViewById(R.id.tvDirection);
            TextView tvDepTime = view.findViewById(R.id.tvDepTime);
            TextView tvArrTime = view.findViewById(R.id.tvArrTime);
            TextView tvWagonNo = view.findViewById(R.id.tvWagonNo);
            TextView tvSeatNoValue = view.findViewById(R.id.tvSeatNoValue);
            TextView tvPriceValue = view.findViewById(R.id.tvPriceValue);

            //Manipulate view
            tvTicketTitle.setText( schedule.getLinkedTrain().getLinkedCompany().getName());
            tvDirection.setText( schedule.getLine().toString());
            tvDepTime.setText( "Departure: " + schedule.getIdRepresentation(schedule.getDepartureDate()).substring(6,8) + "/"
                            + schedule.getIdRepresentation(schedule.getDepartureDate()).substring(4,6) + "/"
                            + schedule.getIdRepresentation(schedule.getDepartureDate()).substring(0,3) + "   "
                            + schedule.getIdRepresentation(schedule.getDepartureDate()).substring(8,10) + ":"
                            + schedule.getIdRepresentation(schedule.getDepartureDate()).substring(10));
            tvArrTime.setText( "Arrival:       " + schedule.getIdRepresentation(schedule.getArrivalDate()).substring(6,8) + "/"
                    + schedule.getIdRepresentation(schedule.getArrivalDate()).substring(4,6) + "/"
                    + schedule.getIdRepresentation(schedule.getArrivalDate()).substring(0,3) + "   "
                    + schedule.getIdRepresentation(schedule.getArrivalDate()).substring(8,10) + ":"
                    + schedule.getIdRepresentation(schedule.getArrivalDate()).substring(10));
            tvWagonNo.setText( "Wagon no: " + String.valueOf(ticket.getSeat().getLinkedWagon().getWagonNumber()));
            tvSeatNoValue.setText( ticket.getSeat().getSeatNumber());
            if ( ticket.getSeat().getLinkedWagon().isBusiness())
                tvPriceValue.setText( String.valueOf(schedule.getBusinessPrice()));
            else
                tvPriceValue.setText( String.valueOf(schedule.getEconomyPrice()));

            return view;
        }
    }
}