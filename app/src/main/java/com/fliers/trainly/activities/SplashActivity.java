package com.fliers.trainly.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Customer;
import com.fliers.trainly.models.Places;
import com.fliers.trainly.models.Tickets;
import com.fliers.trainly.models.User;

public class SplashActivity extends AppCompatActivity {
    // Properties
    private final String LOGGED_IN_USER_TYPE = "loggedInUserType";
    private final int NO_LOGIN = 0;
    private final int COMPANY_LOGIN = 1;
    private final int CUSTOMER_LOGIN = 2;

    // Methocs
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Variables
        Handler handler;
        SharedPreferences preferences;
        CardView cardWelcome;

        // Code
        cardWelcome = findViewById( R.id.cardWelcome);
        preferences = getSharedPreferences( String.valueOf( R.string.app_name), Context.MODE_PRIVATE);

        // Wait for 250 milliseconds to load graphics
        handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                // Variables
                int loginType;
                Intent welcomeIntent;
                User currentUser;
                boolean update;
                Places places;

                // Code
                loginType = preferences.getInt( LOGGED_IN_USER_TYPE, NO_LOGIN);

                if ( loginType == NO_LOGIN) {
                    // Start welcome activity
                    welcomeIntent = new Intent( getApplicationContext(), WelcomeActivity.class);
                    startActivity( welcomeIntent);
                    finish();
                }
                else {
                    // Load logged in user account
                    if ( loginType == COMPANY_LOGIN) {
                        currentUser = new Company( getApplicationContext());
                    }
                    else  {
                        currentUser = new Customer( getApplicationContext());
                    }

                    if ( currentUser.isConnectedToInternet()) {
                        update = true;
                    }
                    else {
                        update = false;
                    }

                    cardWelcome.animate().alpha( 1).setDuration( 250).setInterpolator( new DecelerateInterpolator()).start();
                    places = new Places( getApplicationContext());

                    // Update places with server data
                    places.update( new Places.ServerSyncListener() {
                        @Override
                        public void onSync( boolean isSynced) {
                            if ( isSynced) {
                                Places.setInstance( places);

                                // Update current user's data with server data
                                currentUser.getCurrentUser( update, new User.ServerSyncListener() {
                                    @Override
                                    public void onSync( boolean isSynced) {
                                        // Variables
                                        Tickets tickets;

                                        // Code
                                        if ( isSynced) {
                                            User.setCurrentUserInstance( currentUser);
                                            tickets = new Tickets( getApplicationContext());

                                            // Update tickets with server data
                                            tickets.update( new Tickets.ServerSyncListener() {
                                                @Override
                                                public void onSync( boolean isSynced) {
                                                    // Variables
                                                    Intent homeIntent;

                                                    // Code
                                                    if ( loginType == COMPANY_LOGIN) {
                                                        homeIntent = new Intent( getApplicationContext(), CompanyHomeActivity.class);
                                                    }
                                                    else {
                                                        homeIntent = new Intent( getApplicationContext(), CustomerHomeActivity.class);
                                                    }

                                                    startActivity( homeIntent);
                                                    finish();
                                                }
                                            });
                                        }
                                        else {
                                            showAlertDialog();
                                        }
                                    }
                                });
                            }
                            else {
                                showAlertDialog();
                            }
                        }
                    });
                }
            }
        }, 500);
    }

    /**
     * Displays alert dialog with error message
     */
    private void showAlertDialog() {
        new AlertDialog.Builder( getApplicationContext())
                .setTitle( "An error occurred!")
                .setMessage( "Data of logged in user could not be loaded. Please check the network connection and try again.")
                .setPositiveButton( "Close", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable( false)
                .show();
    }
}