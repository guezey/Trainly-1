package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Place;
import com.fliers.trainly.models.Places;
import com.fliers.trainly.models.Ticket;
import com.fliers.trainly.models.Tickets;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Controls Tickets List page.
 * @author Ali Emir Güzey
 * @version 01.05.2021
 */
public class TicketsActivity extends AppCompatActivity {

    ArrayList<String> tickets;
    String departure;
    String arrival;
    String date;

    /**
     * Manipulates the activity once created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);

        TextView title = (TextView) findViewById(R.id.tvTicketFilter);
        ImageView back = (ImageView) findViewById(R.id.imageView2);
        ListView listTickets = findViewById( R.id.listTickets);
        departure = getIntent().getStringExtra("departure");
        arrival = getIntent().getStringExtra("arrival");
        date = getIntent().getStringExtra("date");
        title.setText( "Tickets for trips from " + departure + " to " + arrival +
                " having departure dates of " + date);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        int year = Integer.parseInt(date.substring(6));
        int month = Integer.parseInt(date.substring(3,5));
        int day = Integer.parseInt(date.substring(0,2));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        tickets = new ArrayList<>();
        tickets.add("1");
        tickets.add("1");
        tickets.add("1");
        tickets.add("1");

//        Places places = Places.getInstance();
//        Place departurePlace = places.findByName(departure);
//        Place arrivalPlace = places.findByName(arrival);
//        Tickets ticketManager = new Tickets( getApplicationContext());
//        tickets = ticketManager.getQueriedTickets( departurePlace, arrivalPlace, calendar);

        if ( tickets.size() == 0) {
            title.setText("No tickets were found for " + date + " from " + departure + " to " + arrival);
            title.setTextColor(Color.RED);
        }
        else {
            CustomAdaptor customAdaptor = new CustomAdaptor();
            listTickets.setAdapter( customAdaptor);
        }
    }

    public void goToTicketInfo() {
        Intent switchActivityIntent = new Intent( this, TicketInfoActivity.class);
        startActivity( switchActivityIntent);
    }

    class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            // TODO: Edit this part
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
        public View getView( final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate( R.layout.list_item_tickets, null);
//            Ticket ticket = tickets.get(position);
//
//            // Get title text view
//            TextView tvTicketTitle = view.findViewById( R.id.tvTicketTitle);
//            TextView tvDirection = view.findViewById( R.id.tvDirection);
//            TextView tvDepTime = view.findViewById(R.id.cap1);
//            TextView tvArrTime = view.findViewById(R.id.lin1);
//
//            tvTicketTitle.setText( ticket.getSeat().getLinkedWagon().getLinkedSchedule().
//                    getLinkedTrain().getLinkedCompany().getName());
//            tvDirection.setText( "From " + departure + " to " + arrival);
//            tvDepTime.setText( "Departure: " + ticket.getSeat().getLinkedWagon().
//                    getLinkedSchedule().getDepartureDate().toString());
//            tvArrTime.setText( "Arrival:       " + ticket.getSeat().getLinkedWagon().
//                    getLinkedSchedule().getArrivalDate().toString());

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToTicketInfo();
                }
            });

            return view;
        }
    }
}