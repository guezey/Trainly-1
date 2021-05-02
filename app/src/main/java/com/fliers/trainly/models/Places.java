package com.fliers.trainly.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Class to store all places in a list.
 * @author Ali Emir Güzey
 * @version 29.04.2021
 */
public class Places {
    // Properties
    protected static final String SERVER_KEY = "KEY_Tr21iwuS3obrslfL4";
    private final String PLACE_NAMES = "placeNames";
    private final String PLACE_XS = "placeXs";
    private final String PLACE_YS = "placeYs";

    private static Places instance = null;
    private ArrayList<Place> places;
    private Context context;
    private SharedPreferences preferences;

    // Constructor
    /**
     * Creates an empty place list.
     * @param context application context
     */
    public Places( Context context) {
        this.context = context;
        places = new ArrayList<>();

        preferences = context.getSharedPreferences( String.valueOf( R.string.app_name), Context.MODE_PRIVATE);
    }

    // Methods
    /**
     * Finds the place with the given name and returns it.
     * @param name place name
     * @return place with the given name
     */
    public Place findByName( String name) {
        for ( Place p: places ) {
            if (p.getName().equals( name))
                return p;
        }

        return null;
    }

    /**
     * Returns the whole list.
     * @return whole place list
     */
    public ArrayList<Place> getAll() {
        return places;
    }

    /**
     * Adds the given place to the list.
     * @param p given place
     * @param listener ServerSyncListener interface that is called
     *                 when data is saved to server
     * @author Alp Afyonluoğlu
     */
    public void add( Place p, ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        Set<String> placeNames;
        Set<String> placeXs;
        Set<String> placeYs;

        // Code
        // Only add if device is connected to the internet and
        // current list does not have a place with the new place's name
        if ( isConnectedToInternet() && findByName( p.getName()) == null) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Places/");

            // Save to server
            reference.child( p.getName()).child( "x").setValue( p.getLongitude());
            reference.child( p.getName()).child( "y").setValue( p.getLatitude());

            // Save to list in memory
            places.add( p);

            // Save to local storage
            placeNames = preferences.getStringSet( PLACE_NAMES, new LinkedHashSet<String>());
            placeXs = preferences.getStringSet( PLACE_XS, new LinkedHashSet<String>());
            placeYs = preferences.getStringSet( PLACE_YS, new LinkedHashSet<String>());

            placeNames.add( p.getName());
            placeXs.add( String.valueOf( p.getLongitude()));
            placeYs.add( String.valueOf( p.getLatitude()));

            preferences.edit().putStringSet( PLACE_NAMES, placeNames).apply();
            preferences.edit().putStringSet( PLACE_XS, placeXs).apply();
            preferences.edit().putStringSet( PLACE_YS, placeYs).apply();

            listener.onSync( true);
        }
        else {
            listener.onSync( false);
        }
    }

    /**
     * Removes the given place from the list.
     * @param p given place
     * @author Alp Afyonluoğlu
     */
    public void remove( Place p, ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        Set<String> placeNames;
        Set<String> placeXs;
        Set<String> placeYs;

        // Code
        // Only remove if device is connected to the internet and
        // current list has a place with the given place's name
        if ( isConnectedToInternet() && findByName( p.getName()) != null) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Places/");

            // Remove from server
            reference.child( p.getName()).removeValue();

            // Update local storage and memory list data with updated server data
            update( new ServerSyncListener() {
                @Override
                public void onSync( boolean isSynced) {
                    listener.onSync( isSynced);
                }
            });
        }
        else {
            listener.onSync( false);
        }
    }

    /**
     * Updates places list in memory with server data or
     * previously saved local data depending on network connection
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server
     * @author Alp Afyonluoğlu
     */
    public void update( ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        Set<String> placeNames;
        Set<String> placeXs;
        Set<String> placeYs;
        String[] placeNamesArray;
        String[] placeXsArray;
        String[] placeYsArray;
        Place place;

        // Code
        if ( isConnectedToInternet()) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Places/");

            reference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    // Variables
                    HashMap<String, String> coordinates;
                    String placeName;
                    Set<String> retrievedPlaceNames;
                    Set<String> retrievedPlaceXs;
                    Set<String> retrievedPlaceYs;
                    Place retrievedPlace;

                    // Code
                    reference.removeEventListener( this);

                    // Check if username entry exists on server or not
                    places = new ArrayList<>();
                    retrievedPlaceNames = new HashSet<String>();
                    retrievedPlaceXs = new HashSet<String>();
                    retrievedPlaceYs = new HashSet<String>();
                    if ( dataSnapshot.exists()) {
                        // Get all places from server one by one
                        for ( DataSnapshot placeInfo : dataSnapshot.getChildren()) {
                            placeName = placeInfo.getKey();
                            coordinates = (HashMap<String, String>) placeInfo.getValue();

                            // Add to list in memory
                            retrievedPlace = new Place( placeName, Double.parseDouble( coordinates.get( "x")), Double.parseDouble( coordinates.get( "y")));
                            places.add( retrievedPlace);

                            // Add to string set to save to local storage
                            retrievedPlaceNames.add( placeName);
                            retrievedPlaceXs.add( coordinates.get( "x"));
                            retrievedPlaceYs.add( coordinates.get( "y"));
                        }
                    }
                    preferences.edit().putStringSet( PLACE_NAMES, retrievedPlaceNames).apply();
                    preferences.edit().putStringSet( PLACE_XS, retrievedPlaceXs).apply();
                    preferences.edit().putStringSet( PLACE_YS, retrievedPlaceYs).apply();

                    listener.onSync( true);
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
            places = new ArrayList<>();

            placeNames = preferences.getStringSet( PLACE_NAMES, null);
            placeXs = preferences.getStringSet( PLACE_XS, null);
            placeYs = preferences.getStringSet( PLACE_YS, null);
            if ( placeNames != null) {
                placeNamesArray = placeNames.toArray( new String[placeNames.size()]);
                placeXsArray = placeXs.toArray( new String[placeXs.size()]);
                placeYsArray = placeYs.toArray( new String[placeYs.size()]);

                for ( int count = 0; count < placeNamesArray.length; count++) {
                    place = new Place( placeNamesArray[count], Double.parseDouble( placeYsArray[count]), Double.parseDouble( placeXsArray[count]));
                    places.add( place);
                }
            }
            listener.onSync( true);
        }
    }

    /**
     * Getter method for static places instance
     * @return places instance
     * @author Alp Afyonluoğlu
     */
    public static Places getInstance() {
        return instance;

    }

    /**
     * Setter method for static places instance
     * @param places places instance to be set
     * @author Alp Afyonluoğlu
     */
    public static void setInstance( Places places) {
        Places.instance = places;
    }

    /**
     * Checks connection status
     * @return whether device is connected to internet or not
     * @author Alp Afyonluoğlu
     */
    private boolean isConnectedToInternet() {
        // Variables
        ConnectivityManager connectivityManager;

        // Code
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public interface ServerSyncListener {
        void onSync( boolean isSynced);
    }
}
