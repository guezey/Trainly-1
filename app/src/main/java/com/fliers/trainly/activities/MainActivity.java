package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Customer;
import com.fliers.trainly.models.Line;
import com.fliers.trainly.models.Place;
import com.fliers.trainly.models.Schedule;
import com.fliers.trainly.models.Seat;
import com.fliers.trainly.models.Ticket;
import com.fliers.trainly.models.Tickets;
import com.fliers.trainly.models.Train;
import com.fliers.trainly.models.User;
import com.fliers.trainly.models.Wagon;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Company company = new Company( "001", getApplicationContext());
        Place place = new Place( "Train001", 0, 0);
        Line line = new Line( place, place);
        Train train = new Train( company, place, 1, 1, 100, 70, "001");
        Schedule schedule = new Schedule( "202105011000", "202105011330", line, 1, 1, train);
        train.addSchedule( schedule);

        Customer customer = new Customer( getApplicationContext());
//        customer.login("alpafyonluoglu@gmail.com", new User.EmailListener() {
//            @Override
//            public void onEmailSent(String email, boolean isSent) {
//                Log.d( "TRAINLY_APP", "OK");
//            }
//        });

        customer.getCurrentUser(false, new User.ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                Log.d( "TRAINLY_APP", "isSynced: " + isSynced);
                Log.d( "TRAINLY_APP", "User ID: " + customer.getId());
                Log.d( "TRAINLY_APP", "Email: " + customer.getEmail());

                Tickets tickets = new Tickets( getApplicationContext());
                tickets.printTable();
                tickets.createTickets(schedule, new Tickets.ServerSyncListener() {
                    @Override
                    public void onSync( boolean isSynced) {
                        Log.d( "TRAINLY_APP", "Tickets created and synced");
                        tickets.printTable();
                    }
                });
            }
        });
    }
}