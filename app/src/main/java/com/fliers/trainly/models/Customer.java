package com.fliers.trainly.models;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Customer Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Customer extends User {
    
    // Properties
    private final String POINTS = "userPoints";
    private final String TEMP_POINTS = "tempPoints";

    private int discountPoints;

    // Constructors
    /**
     * Initializes a new customer
     */
    public Customer( Context context) {
        super( context);
        setDiscountPoints( 0 );
    }

    // Methods
    public int getDiscountPoints() {
        return this.discountPoints;
    }

    public void setDiscountPoints( int discountPoints ) {
        this.discountPoints = discountPoints;
    }

    public void buyTicket( Ticket ticket ) {
        // TODO: to be done
    }

    /**
     * Sends login email to the given email address
     * @param email email address of the user
     * @param listener EmailListener interface that is called
     *                 when verification email is sent
     * @author Alp Afyonluoğlu
     */
    @Override
    public void login( String email, EmailListener listener) {
        super.login( email, new EmailListener() {
            @Override
            public void onEmailSent( String email, boolean isSent) {
                if ( isSent) {
                    preferences.edit().putInt( TEMP_POINTS, discountPoints).apply();
                }

                listener.onEmailSent( email, isSent);
            }
        });
    }

    /**
     * Completes login process by using the link that is sent via email
     * @param emailLink link sent to the email address of the user
     * @param listener LoginListener interface that is called
     *                 when email link is verified
     * @author Alp Afyonluoğlu
     */
    public void completeLogin( String emailLink, LoginListener listener) {
        discountPoints = preferences.getInt( TEMP_POINTS, 0);

        super.completeLogin( emailLink, new LoginListener() {
            @Override
            public void onLogin( boolean isLoggedIn) {
                if ( isLoggedIn) {
                    preferences.edit().putInt( POINTS, discountPoints).apply();
                }

                listener.onLogin( isLoggedIn);
            }
        });
    }

    /**
     * Logs in the current user account
     * @param update whether data should be with the server data
     *               or not
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server or loaded
     *                 from local storage
     * @author Alp Afyonluoğlu
     */
    public void getCurrentUser( boolean update, ServerSyncListener listener) {
        super.getCurrentUser( update, new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                if ( isSynced && !update) {
                    discountPoints = preferences.getInt( POINTS, 0);
                }

                listener.onSync( isSynced);
            }
        });
    }

    /**
     * Saves local user data to server
     * @param listener ServerSyncListener interface that is called
     *                 when data is sent to server
     * @author Alp Afyonluoğlu
     */
    public void saveToServer( ServerSyncListener listener) {
        super.saveToServer( new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                if ( isSynced) {
                    // Variables
                    FirebaseDatabase database;
                    DatabaseReference reference;
                    HashMap<String, String> userData;

                    // Code
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference( SERVER_KEY + "/Users/" + id);

                    // Create hash map with given user data
                    userData = new HashMap<>();
                    userData.put( "points", String.valueOf( discountPoints));

                    // Save map to server
                    reference.setValue( userData);
                    listener.onSync( true);
                }
                else {
                    listener.onSync( false);
                }
            }
        });
    }

    /**
     * Updates local user data with data retrieved from server
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server
     * @author Alp Afyonluoğlu
     */
    public void getFromServer( ServerSyncListener listener) {
        super.getFromServer( new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                if ( isSynced) {
                    // Variables
                    FirebaseDatabase database;
                    DatabaseReference reference;

                    // Code
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference( SERVER_KEY + "/Users/" + id);

                    // Retrieve data of the user with given email address from server
                    reference.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot dataSnapshot) {
                            // Variables
                            HashMap<String, String> userData;

                            // Code
                            reference.removeEventListener( this);

                            // Check if username entry exists on server or not
                            if ( dataSnapshot.exists()) {
                                userData = (HashMap<String, String>) dataSnapshot.getValue();

                                // Check whether passwords match or not
                                discountPoints = Integer.parseInt( userData.get( "points"));

                                listener.onSync( true);
                            }
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {
                            // Database error occurred
                            reference.removeEventListener( this);

                            listener.onSync( false);
                        }
                    });
                }
                else {
                    listener.onSync( false);
                }
            }
        });
    }
}