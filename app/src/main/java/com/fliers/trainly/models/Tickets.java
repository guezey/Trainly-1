package com.fliers.trainly.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Tickets class managing multiple instances of the Ticket class
 * @author Alp Afyonluoğlu
 * @version 29.04.2021
 */
public class Tickets extends SQLiteOpenHelper {
    // Properties
    protected static final String SERVER_KEY = "KEY_Tr21iwuS3obrslfL4";
    private final String TABLE_NAME = "tickets";
    private final String ID = "id";
    private final String COMPANY_ID = "company_id";
    private final String TRAIN_ID = "train_id";
    private final String DEPARTURE_YEAR = "dep_year";
    private final String DEPARTURE_MONTH = "dep_month";
    private final String DEPARTURE_DAY = "dep_day";
    private final String DEPARTURE_HOUR = "dep_hour";
    private final String DEPARTURE_MINUTE = "dep_minute";
    private final String DEPARTURE = "departure";
    private final String ARRIVAL = "arrival";
    private final String WAGON_NO = "wagon_no";
    private final String SEAT_NO = "seat_no";
    private final String OWNER = "owner";
    private final String NULL = "null";
    private final String UNKNOWN = "unk";

    private final String FROM = "from";
    private final String TO = "to";
    private final String TRAINS = "trains";
    private final String SCHEDULES = "schedules";
    private final String WAGONS = "wagons";

    private ArrayList<Ticket> tickets;
    private Context context;

    // Constructors
    /**
     * Initializes the tickets database
     * @param context application context
     */
    public Tickets( Context context) {
        super( context, "TICKETS", null, 1);
        this.context = context;
    }

    // Methods
    /**
     * Initializes database table
s     * @param db SQL Database
     */
    @Override
    public void onCreate( SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COMPANY_ID + " TEXT, " +
                TRAIN_ID + " TEXT, " +
                DEPARTURE_YEAR + " INTEGER, " +
                DEPARTURE_MONTH + " INTEGER, " +
                DEPARTURE_DAY + " INTEGER, " +
                DEPARTURE_HOUR + " INTEGER, " +
                DEPARTURE_MINUTE + " INTEGER, " +
                DEPARTURE + " TEXT, " +
                ARRIVAL + " TEXT, " +
                WAGON_NO + " INTEGER, " +
                SEAT_NO + " TEXT, " +
                OWNER + " TEXT)");
    }

    /**
     * Deletes old database and creates a new one
     * @param db sql database object
     * @param oldVersion old version no
     * @param newVersion new version no
     */
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion) {
        reset( db);
    }

    /**
     * Deletes old database and creates a new one
     * @param db sql database object
     */
    private void reset( SQLiteDatabase db) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate( db);
    }

    /**
     * Adds given ticket to the database
     * @param ticket ticket to be added
     */
    private void add( Ticket ticket) {
        // Variables
        SQLiteDatabase db;
        ContentValues values;
        Seat seat;
        Wagon wagon;
        Schedule schedule;
        Place fromPlace;
        Place toPlace;
        Train train;
        Company company;
        Calendar departureTime;
        Customer customer;
        String companyId;
        String trainId;
        int year;
        int month;
        int day;
        int hour;
        int minute;
        String from;
        String to;
        int wagonNo;
        String seatNo;
        String customerId;

        // Code
        db = this.getWritableDatabase();
        values = new ContentValues();

        seat = ticket.getSeat();
        wagon = seat.getLinkedWagon();
        schedule = wagon.getLinkedSchedule();
        fromPlace = schedule.getDeparturePlace();
        toPlace = schedule.getArrivalPlace();
        train = schedule.getLinkedTrain();
        company = train.getLinkedCompany();
        departureTime = schedule.getDepartureDate();
        customer = ticket.getCustomer();

        companyId = company.getCompanyId();
        trainId = train.getId();
        year = departureTime.get( Calendar.YEAR);
        month = departureTime.get( Calendar.MONTH);
        day = departureTime.get( Calendar.DAY_OF_MONTH);
        hour = departureTime.get( Calendar.HOUR_OF_DAY);
        minute = departureTime.get( Calendar.MINUTE);
        from = fromPlace.getName();
        to = toPlace.getName();
        wagonNo = wagon.getWagonNumber();
        seatNo = seat.getSeatNumber();

        if ( customer == null || customer.getEmail().equals( "")) {
            customerId = NULL;
        }
        else {
            customerId = customer.getId();
        }

        add( companyId, trainId, year, month, day, hour, minute, from, to, wagonNo, seatNo, customerId);
    }

    /**
     * Adds given ticket to the database
     * @param companyId company id
     * @param trainId train id
     * @param year departure year
     * @param month departure month
     * @param day departure day
     * @param hour departure hour
     * @param minute departure minute
     * @param from departure place
     * @param to arrival place
     * @param wagonNo wagon number
     * @param seatNo seat number
     * @param customerId customer id of the owner
     */
    private void add( String companyId, String trainId, int year, int month, int day, int hour, int minute, String from, String to, int wagonNo, String seatNo, String customerId) {
        // Variables
        SQLiteDatabase db;
        ContentValues values;

        // Code
        db = this.getWritableDatabase();
        values = new ContentValues();

        values.put( COMPANY_ID, companyId);
        values.put( TRAIN_ID, trainId);
        values.put( DEPARTURE_YEAR, year);
        values.put( DEPARTURE_MONTH, month);
        values.put( DEPARTURE_DAY, day);
        values.put( DEPARTURE_HOUR, hour);
        values.put( DEPARTURE_MINUTE, minute);
        values.put( DEPARTURE, from);
        values.put( ARRIVAL, to);
        values.put( WAGON_NO, wagonNo);
        values.put( SEAT_NO, seatNo);
        values.put( OWNER, customerId);

        db.insert( TABLE_NAME, null, values);
    }

    /**
     * Creates tickets for all empty seats
     * @param schedule schedule used to get ticket information
     */
    public void createTickets( Schedule schedule, ServerSyncListener listener) {
        // Variables
        ArrayList<Wagon> wagons;
        ArrayList<Seat> seats;
        Ticket ticket;

        if ( !isConnectedToInternet()) {
            listener.onSync( false);
        }
        else {
            // Code
            wagons = schedule.getWagons();
            for ( int wagonNo = 0; wagonNo < wagons.size(); wagonNo++) {
                seats = wagons.get( wagonNo).getSeats();
                for ( int seatNo = 0; seatNo < seats.size(); seatNo++) {
                    ticket = new Ticket( seats.get( seatNo), null);
                }
            }

            // TODO: saveToServer(); & listener.onSync();
        }
    }

    /**
     * Data cursor for the ticket with given seat
     * @param seat seat to be searched
     * @return data cursor
     */
    private Cursor getData( Seat seat) {
        // Variables
        SQLiteDatabase db;
        ContentValues values;
        Cursor data;
        Wagon wagon;
        Schedule schedule;
        Place from;
        Place to;
        Train train;
        Company company;
        Calendar departureTime;
        Customer customer;

        // Code
        db = this.getWritableDatabase();

        wagon = seat.getLinkedWagon();
        schedule = wagon.getLinkedSchedule();
        from = schedule.getDeparturePlace();
        to = schedule.getArrivalPlace();
        train = schedule.getLinkedTrain();
        company = train.getLinkedCompany();
        departureTime = schedule.getDepartureDate();

        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COMPANY_ID + " = " + company.getCompanyId() + " AND "
                + TRAIN_ID + " = " + train.getId() + " AND "
                + DEPARTURE_YEAR + " = " + departureTime.get( Calendar.YEAR) + " AND "
                + DEPARTURE_MONTH + " = " + departureTime.get( Calendar.MONTH) + " AND "
                + DEPARTURE_DAY + " = " + departureTime.get( Calendar.DAY_OF_MONTH) + " AND "
                + DEPARTURE_HOUR + " = " + departureTime.get( Calendar.HOUR_OF_DAY) + " AND "
                + DEPARTURE_MINUTE + " = " + departureTime.get( Calendar.MINUTE) + " AND "
                + DEPARTURE + " = " + from.getName() + " AND "
                + ARRIVAL + " = " + to.getName() + " AND "
                + WAGON_NO + " = " + wagon.getWagonNumber() + " AND "
                + SEAT_NO + " = " + seat.getSeatNumber() + ";", null);

        return data;
    }

    /**
     * Searches for id of the ticket with given seat
     * @param seat seat to be searched
     * @return database id of the seat
     */
    private int getDbId( Seat seat) {
        // Variables
        Cursor data;

        // Variables
        data = getData( seat);
        if ( data.getCount() == 0) {
            // Database does not have this ticket
            return 0;
        }
        else {
            data.moveToFirst();
            return data.getInt( data.getColumnIndex( ID));
        }
    }

    /**
     * Searches for the owner of the ticket with given seat
     * @param seat seat to be searched
     * @return owner id of the seat
     */
    private String getOwnerId( Seat seat) {
        // Variables
        Cursor data;

        // Variables
        data = getData( seat);
        if ( data.getCount() == 0) {
            return UNKNOWN;
        }
        else {
            data.moveToFirst();
            return data.getString( data.getColumnIndex( OWNER));
        }
    }

    /**
     * Check if seat is available to buy
     * @param seat seat to be searched
     * @return whether seat is available to buy or not
     */
    public boolean isSeatEmpty( Seat seat) {
        // Variables
        String ownerId;

        // Code
        ownerId = getOwnerId( seat);
        if ( ownerId.equals( NULL)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks whether database is empty or not
     * @return whether database is empty or not
     */
    private boolean isEmpty() {
        // Variables
        SQLiteDatabase db;
        Cursor data;

        // Code
        db = this.getWritableDatabase();
        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + ";", null);

        return data.getCount() == 0;
    }

    /**
     * Prints table  for debugging
     */
    public void printTable() {
        // Variables
        final int CELL_LENGTH = 15;

        SQLiteDatabase db;
        Cursor data;
        String[] columnNames;
        String cell;
        StringBuilder tableContent;

        // Code
        tableContent = new StringBuilder();
        db = this.getWritableDatabase();
        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + ";", null);

        if ( isEmpty()) {
            Log.d("DB_" + TABLE_NAME.toUpperCase(), "\nDatabase is empty");
        }
        else {
            // Get column names
            columnNames = data.getColumnNames();
            for ( int count = 0; count < columnNames.length; count++) {
                cell = columnNames[count];

                if ( cell.length() > CELL_LENGTH) {
                    cell = cell.substring( 0, CELL_LENGTH);
                }
                else {
                    while ( cell.length() != CELL_LENGTH) {
                        cell = " " + cell;
                    }
                }
                tableContent.append( cell);

                if ( count != columnNames.length - 1) {
                    tableContent.append( "|");
                }
            }
            tableContent.append( "\n");

            // Add separator line
            String line = "";
            while ( line.length() != ( ( CELL_LENGTH + 1) * columnNames.length) - 1) {
                line = line + "_";
            }
            tableContent.append( line).append( "\n");

            // Add values
            data.moveToFirst();
            do {
                for ( int count = 0; count < columnNames.length; count++) {
                    cell = data.getString( data.getColumnIndex( columnNames[count]));

                    if ( cell.length() > CELL_LENGTH) {
                        cell = cell.substring( 0, CELL_LENGTH);
                    }
                    else {
                        while ( cell.length() != CELL_LENGTH) {
                            cell = " " + cell;
                        }
                    }
                    tableContent.append( cell);

                    if ( count != columnNames.length - 1) {
                        tableContent.append( "|");
                    }
                }
                tableContent.append( "\n");
            } while ( data.moveToNext());

            Log.d("DB_" + TABLE_NAME.toUpperCase(), " \n" + tableContent);
        }
    }

    /**
     * Checks connection status
     * @return whether device is connected to internet or not
     */
    protected boolean isConnectedToInternet() {
        // Variables
        ConnectivityManager connectivityManager;

        // Code
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    /**
     * Updates local database tickets with data retrieved from server
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server
     */
    public void update( ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        SQLiteDatabase db;

        // Code
        db = this.getWritableDatabase();
        if ( isConnectedToInternet()) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Companies/");

            reference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    // Variables
                    String companyId;
                    String trainId;
                    String departureDateStr;
                    int year;
                    int month;
                    int day;
                    int hour;
                    int minute;
                    String from;
                    String to;
                    int wagonNo;
                    String seatNo;
                    String customerId;

                    // Code
                    reference.removeEventListener( this);

                    // Erase all database data and refill with server data
                    reset( db);
                    if ( dataSnapshot.exists()) {
                        for ( DataSnapshot company : dataSnapshot.getChildren()) {
                            companyId = company.getKey();

                            for ( DataSnapshot train : company.child( TRAINS).getChildren()) {
                                trainId = train.getKey();

                                for ( DataSnapshot schedule : train.child( SCHEDULES).getChildren()) {
                                    departureDateStr = schedule.getKey();
                                    year = Integer.parseInt( departureDateStr.substring( 0, 4));
                                    month = Integer.parseInt( departureDateStr.substring( 4, 6));
                                    day = Integer.parseInt( departureDateStr.substring( 6, 8));
                                    hour = Integer.parseInt( departureDateStr.substring( 8, 10));
                                    minute = Integer.parseInt( departureDateStr.substring( 10, 12));

                                    from = schedule.child( FROM).getValue( String.class);
                                    to = schedule.child( TO).getValue( String.class);

                                    for ( DataSnapshot wagon : schedule.child( WAGONS).getChildren()) {
                                        wagonNo = Integer.parseInt( wagon.getKey());

                                        for ( DataSnapshot seat : wagon.getChildren()) {
                                            seatNo = seat.getKey();
                                            customerId = seat.getValue( String.class);

                                            add( companyId, trainId, year, month, day, hour, minute, from, to, wagonNo, seatNo, customerId);
                                        }
                                    }
                                }
                            }
                        }

                        listener.onSync( true);
                    }
                    else {
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

    public interface ServerSyncListener {
        void onSync( boolean isSynced);
    }
}
