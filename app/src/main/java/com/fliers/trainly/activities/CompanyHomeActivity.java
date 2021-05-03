package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Ticket;
import com.fliers.trainly.models.Tickets;

import java.util.ArrayList;

/**
 * Company Homepage Activity
 * Companies can use this page to learn statistics and go to several other pages by a menu
 * @author Cengizhan Terzioğlu
 * @version 03.05.2021
 */

public class CompanyHomeActivity extends AppCompatActivity {

    Company company;
    ImageView menuButton;
    TextView trainNumber;
    TextView customerLastWeek;
    TextView lineNumber;
    TextView employeeNumber;
    TextView revenueLastWeek;
    TextView balance;
    TextView starPointText;
    ImageView[] starPoint;
    ArrayList<Ticket> tickets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_home);

        company = ( Company ) Company.getCurrentUserInstance();
        Tickets ticketManager = new Tickets( getApplicationContext() );
        tickets = ticketManager.getRecentlySoldTickets( company );

        // Generate the statistics on the screen
        trainNumber.setText( company.getTrains().size() + "" );
        customerLastWeek.setText( tickets.size() + "" );
        lineNumber.setText( company.getLines().size() + "" );
        employeeNumber.setText( company.getEmployees().size() + "" );
        revenueLastWeek.setText( company.getLastWeeksRevenue() + "$" );
        balance.setText( company.getBalance() + "$" );
        starPointText.setText( "Average point: " + company.getAveragePoint() + "/5" );

        // Generate star points on the screen
        double roundAverage = Math.round( company.getAveragePoint() * 2 ) / 2;
        int starIndex = 0;
        for ( double i = roundAverage; i < 0; i-- ) {
            if ( i >= 1 )
                starPoint[starIndex].setImageResource( R.drawable.ic_baseline_star_outline_48 );
            else if ( i == 0.5 )
                starPoint[starIndex].setImageResource( R.drawable.ic_baseline_star_half_48 );
            else
                starPoint[starIndex].setImageResource( R.drawable.ic_baseline_star_48 );
        }

        // Opens the menu to go other pages
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                CompanyHomeActivity.this.openOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.activity_main_drawer, menu );
        return true;
    }

    /**
     * Determines what will happen after an item from the menu has been selected
     * @param item menu item that has been selected by the company user
     * @return false to allow normal menu processing to proceed, true to consume it here
     */
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        int id = item.getItemId();
        Intent intent = new Intent( getApplicationContext(), CompanyHomeActivity.class);

        switch (id) {
            case R.id.nav_home:
                intent.setClass( getApplicationContext(), CompanyHomeActivity.class );
                startActivity( intent);
                break;
            case R.id.nav_trains:
                intent.setClass( getApplicationContext(), TrainsActivity.class );
                startActivity( intent);
                break;
            case R.id.nav_lines:
                intent.setClass( getApplicationContext(), LinesActivity.class );
                startActivity( intent);
                break;
            case R.id.nav_employees:
                intent.setClass( getApplicationContext(), EmployeesActivity.class );
                startActivity( intent);
                break;
            case R.id.nav_account_settings:
                intent.setClass( getApplicationContext(), CustomerAccountActivity.class );
                startActivity( intent);
                break;
            case R.id.nav_general_information:
                intent.setClass( getApplicationContext(), GeneralInfoActivity.class );
                startActivity( intent);
                break;
            case R.id.nav_log_out:
                intent.setClass( getApplicationContext(), WelcomeActivity.class );
                startActivity( intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}