package com.fliers.trainly.models;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents company users.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Company extends User {
    // Properties
    private final String COMPANY_ID = "companyId";
    private final String BALANCE = "balance";
    private final String TRAINS = "trainIds";
    private final String SCHEDULES = "schedules";
    private final String ESTIMATED_ARRIVAL = "estimatedArrival";
    private final String BUSINESS_WAGON_NO = "businessWagonNo";
    private final String ECONOMY_WAGON_NO = "economyWagonNo";
    private final String BUSINESS_PRICE = "businessPrice";
    private final String ECONOMY_PRICE = "economyPrice";
    private final String CURRENT_LOCATION = "currentLocation";
    private final String FROM = "from";
    private final String TO = "to";

    private String companyId;
    private int balance;
    private ArrayList<Train> trains;
    private ArrayList<Line> lines;
    private ArrayList<Line> feedback;
    private ArrayList<Employee> employees;

    // Constructor
    /**
     * Initializes a new company
     */
    public Company( Context context) {
        super( context);
        companyId = "000";
        balance = 0;
        trains = new ArrayList<>();
        lines = new ArrayList<>();
        feedback = new ArrayList<>();
        employees = new ArrayList<>();
    }

    // Methods
    /**
     * Sets the balance.
     * @param newBalance new balance value
     */
    public void setBalance( int newBalance) {
        balance = newBalance;
    }

    /**
     * Returns balance.
     * @return balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * Returns employees list.
     * @return employees
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Returns lines list
     * @return lines
     */
    public ArrayList<Line> getLines() {
        return lines;
    }

    /**
     * Returns trains list
     * @return trains
     */
    public ArrayList<Train> getTrains() {
        return trains;
    }

    /**
     * Adds a new trains to trains list.
     * @param newTrain a new train
     */
    public void addTrain( Train newTrain) {
        trains.add( newTrain);
    }

    /**
     * Adds a new line to lines list.
     * @param newLine a new line
     */
    public void addLine( Line newLine) {
        lines.add( newLine);
    }

    /**
     * Adds a new employee to employees list.
     * @param newEmployee a new employee
     */
    public void addEmployee( Employee newEmployee) {
        employees.add( newEmployee);
    }

    /**
     * Removes a train from trains list.
     * @param train a train from list
     */
    public void removeTrain( Object train) {
        trains.remove( train);
    }

    /**
     * Removes a line from lines list.
     * @param line a line from list
     */
    public void removeLine( Object line) {
        lines.remove( line);
    }

    /**
     * Removes an employee from employees list.
     * @param employee an employee from list
     */
    public void removeEmployee( Object employee) {
        employees.remove( employee);
    }

    /**
     * Returns last week's customer number for this company.
     * @return customer number
     */
    public int getLastWeeksCustomerNum() {
        // TODO: to be done
        return 0;
    }

    /**
     * Returns last week's customer number for this company.
     * @return revenue
     */
    public double getLastWeeksRevenue() {
        // TODO: to be done
        return 0;
    }

    /**
     * Overridable method to be modified by sub classes to assign ids, if
     * required, before server sync
     * @param listener LoginListener interface of completeLogin method to
     *                 be called when server sync is completed
     */
    @Override
    protected void onLoginEmailVerified( LoginListener listener) {
        createCompanyId( new IdCreateListener() {
            @Override
            public void onIdCreated( boolean isCreated) {
                if ( isCreated) {
                    Company.super.onLoginEmailVerified( new LoginListener() {
                        @Override
                        public void onLogin( boolean isLoggedIn) {
                            listener.onLogin( isLoggedIn);
                        }
                    });
                }
                else {
                    listener.onLogin( false);
                }
            }
        });
    }

    /**
     * Creates company id
     * @param listener IdCreateListener interface that is called
     *                 id is created with data retrieved from server
     */
    private void createCompanyId( IdCreateListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;

        // Code
        if ( isNewAccount) {
            // Assign company id
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Companies/");

            // Retrieve company id with the highest value registered so far
            reference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    // Variables
                    int idVal;
                    int highestIdVal;

                    // Code
                    reference.removeEventListener( this);

                    // Get highest id value
                    highestIdVal = 1;
                    if ( dataSnapshot.exists()) {
                        for ( DataSnapshot company : dataSnapshot.getChildren()) {
                            idVal = Integer.parseInt( company.getKey());
                            if ( idVal > highestIdVal) {
                                highestIdVal = idVal;
                            }
                        }
                    }

                    // Create company id
                    companyId = String.valueOf( highestIdVal + 1);
                    while ( companyId.length() != 3) {
                        companyId = "0" + companyId;
                    }

                    listener.onIdCreated( true);
                }

                @Override
                public void onCancelled( DatabaseError error) {
                    // Database error occurred
                    reference.removeEventListener( this);

                    listener.onIdCreated( false);
                }
            });
        }
    }

    /**
     * Logs in the current user account
     * @param update whether user data should be updated with the
     *               server data or not
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server or loaded
     *                 from local storage
     * @author Alp Afyonluoğlu
     */
    @Override
    public void getCurrentUser( boolean update, ServerSyncListener listener) {
        // Variables
        final Company THIS_COMPANY = this;

        // Code
        super.getCurrentUser( update, new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                // Variables
                Set<String> trainIds;
                String[] trainIdsArray;
                Train train;
                Place defaultPlace;
                Line defaultLine;

                // Code
                if ( isSynced && !update) {
                    companyId = preferences.getString( COMPANY_ID, "000");
                    balance = preferences.getInt( BALANCE, 0);

                    // Create default place and line
                    defaultPlace = new Place( "Unknown", 0, 0);
                    defaultLine = new Line( defaultPlace, defaultPlace);

                    // Create trains
                    trainIds = preferences.getStringSet( TRAINS, null);
                    trains = new ArrayList<>();
                    if ( trainIds != null) {
                         trainIdsArray = trainIds.toArray( new String[trainIds.size()]);

                         for ( String trainId : trainIdsArray) {
                             train = new Train( THIS_COMPANY, defaultPlace, 0, 0, 0, 0, new ArrayList<>(), trainId);
                             trains.add( train);
                         }
                    }
                }

                listener.onSync( isSynced);
            }
        });
    }

    /**
     * Saves user data to local storage
     */
    @Override
    protected void saveToLocalStorage() {
        // Variables
        Set<String> trainIds;

        // Code
        super.saveToLocalStorage();
        preferences.edit().putString( COMPANY_ID, companyId).apply();
        preferences.edit().putString( COMPANY_ID, companyId).apply();
        preferences.edit().putInt( BALANCE, balance).apply();

        trainIds = new HashSet<String>();
        for ( int count = 0; count < trains.size(); count++) {
            trainIds.add( String.valueOf( trains.get( count).getId()));
        }
        preferences.edit().putStringSet( TRAINS, trainIds).apply();
    }

    /**
     * Saves local user data to server
     * @param listener ServerSyncListener interface that is called
     *                 when data is sent to server
     * @author Alp Afyonluoğlu
     */
    @Override
    public void saveToServer( ServerSyncListener listener) {
        super.saveToServer( new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                if ( isSynced) {
                    // Variables
                    FirebaseDatabase database;
                    DatabaseReference reference;
                    HashMap<String, String> data;
                    Train train;
                    ArrayList<Schedule> schedules;
                    Schedule schedule;

                    // Code
                    database = FirebaseDatabase.getInstance();

                    // Save user data to server
                    reference = database.getReference( SERVER_KEY + "/Users/" + id);
                    data = new HashMap<>();
                    data.put( COMPANY_ID, companyId);
                    reference.setValue( data);

                    // Save company data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId);
                    data = new HashMap<>();
                    data.put( NAME, name);
                    data.put( BALANCE, String.valueOf( balance));
                    reference.setValue( data);

                    // Save trains data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId + "/trains");
                    for ( int count = 0; count < trains.size(); count++) {
                        train = trains.get( count);

                        // Save train related general info
                        data = new HashMap<>();
                        data.put( BUSINESS_WAGON_NO, String.valueOf( train.businessWagonNum));
                        data.put( ECONOMY_WAGON_NO, String.valueOf( train.economyWagonNum));
                        data.put( BUSINESS_PRICE, String.valueOf( train.businessPrice));
                        data.put( ECONOMY_PRICE, String.valueOf( train.economyPrice));
                        reference.child( train.id).setValue( data);
                        // TODO: Add availability

                        // Save current location of train
                        data = new HashMap<>();
                        data.put( "x", String.valueOf( train.lon));
                        data.put( "y", String.valueOf( train.lat));
                        reference.child( train.id).child( CURRENT_LOCATION).setValue( data);

                        // Save schedules and their lines
                        schedules = train.schedules;
                        for ( int scheduleCount = 0; scheduleCount < schedules.size(); scheduleCount++) {
                            schedule = schedules.get( scheduleCount);

                            data = new HashMap<>();
                            data.put( FROM, String.valueOf( schedule.getDeparturePlace().getName()));
                            data.put( TO, String.valueOf( schedule.getArrivalPlace().getName()));
                            data.put( ESTIMATED_ARRIVAL, schedule.getIdRepresentation( schedule.getArrivalDate()));
                            reference.child( train.id).child( SCHEDULES).child( schedule.getIdRepresentation( schedule.getDepartureDate())).setValue( data);
                        }

                        listener.onSync( true);
                    }
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
    @Override
    public void getFromServer( ServerSyncListener listener) {
        // Variables
        final Company THIS_COMPANY = this;

        // Code
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

                    // Retrieve user data from server
                    reference.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot dataSnapshot) {
                            // Variables
                            HashMap<String, String> userData;
                            DatabaseReference referenceCompany;

                            // Code
                            reference.removeEventListener( this);
                            if ( dataSnapshot.exists()) {
                                userData = (HashMap<String, String>) dataSnapshot.getValue();
                                companyId = userData.get( COMPANY_ID);

                                // Retrieve company data from server
                                referenceCompany = database.getReference( SERVER_KEY + "/Companies/" + companyId);

                                // Retrieve user data from server
                                referenceCompany.addValueEventListener( new ValueEventListener() {
                                    @Override
                                    public void onDataChange( DataSnapshot dataSnapshot) {
                                        // Variables
                                        HashMap<String, Object> companyData;
                                        Train train;
                                        String trainId;
                                        int businessWagonNo;
                                        int economyWagonNo;
                                        int businessPrice;
                                        int economyPrice;
                                        int currentX;
                                        int currentY;
                                        Place currentLocation;
                                        ArrayList<Schedule> schedules;
                                        Schedule schedule;
                                        String departureId;
                                        String arrivalId;
                                        String from;
                                        String to;
                                        Place departure;
                                        Place arrival;
                                        Line line;

                                        // Code
                                        referenceCompany.removeEventListener( this);
                                        if ( dataSnapshot.exists()) {
                                            companyData = (HashMap<String, Object>) dataSnapshot.getValue();
                                            balance = Integer.parseInt( (String) companyData.get( COMPANY_ID));

                                            // Create trains with server data
                                            trains = new ArrayList<>();
                                            for ( DataSnapshot trainData : dataSnapshot.child( "trains").getChildren()) {
                                                trainId = trainData.getKey();
                                                businessWagonNo = Integer.parseInt( trainData.child( trainId).child( BUSINESS_WAGON_NO).getValue( String.class));
                                                economyWagonNo = Integer.parseInt( trainData.child( trainId).child( ECONOMY_WAGON_NO).getValue( String.class));
                                                businessPrice = Integer.parseInt( trainData.child( trainId).child( BUSINESS_PRICE).getValue( String.class));
                                                economyPrice = Integer.parseInt( trainData.child( trainId).child( ECONOMY_PRICE).getValue( String.class));
                                                currentX = Integer.parseInt( trainData.child( trainId).child( CURRENT_LOCATION).child( "x").getValue( String.class));
                                                currentY = Integer.parseInt( trainData.child( trainId).child( CURRENT_LOCATION).child( "y").getValue( String.class));
                                                currentLocation = new Place( "Train " + trainId, currentY, currentX);

                                                schedules = new ArrayList<>();
                                                for ( DataSnapshot scheduleData : dataSnapshot.child( trainId).child( SCHEDULES).getChildren()) {
                                                    departureId = scheduleData.getKey();
                                                    arrivalId = trainData.child( ESTIMATED_ARRIVAL).getValue( String.class);
                                                    from = trainData.child( FROM).getValue( String.class);
                                                    to = trainData.child( TO).getValue( String.class);

                                                    departure = new Place( from, 0, 0); // TODO: Get coordinates from place list
                                                    arrival = new Place( to, 0, 0); // TODO: Get coordinates from place list
                                                    line = new Line( departure, arrival);

                                                    schedule = new Schedule( departureId, arrivalId, line, businessWagonNo, economyWagonNo);
                                                    schedules.add( schedule);
                                                }

                                                train = new Train( THIS_COMPANY, currentLocation, businessWagonNo, economyWagonNo, businessPrice, economyPrice, schedules, trainId);
                                                trains.add( train);
                                            }

                                            listener.onSync( true);
                                        }
                                    }

                                    @Override
                                    public void onCancelled( DatabaseError error) {
                                        // Database error occurred
                                        referenceCompany.removeEventListener( this);

                                        listener.onSync( false);
                                    }
                                });
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

    private interface IdCreateListener {
        void onIdCreated( boolean isCreated);
    }
}
