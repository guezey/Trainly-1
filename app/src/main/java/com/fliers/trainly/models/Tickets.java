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
    private final String COMPANY_NAME = "company_name";
    private final String COMPANY_ID = "company_id";
    private final String TRAIN_ID = "train_id";
    private final String DEPARTURE_TIME = "departure_time";
    private final String DEPARTURE = "departure";
    private final String ARRIVAL = "arrival";
    private final String WAGON_NO = "wagon_no";
    private final String SEAT_NO = "seat_no";
    private final String BUSINESS_WAGON_NO = "business_wagon_no";
    private final String ECONOMY_WAGON_NO = "economy_wagon_no";
    private final String BUSINESS_PRICE = "business_price";
    private final String ECONOMY_PRICE = "economy_price";
    private final String OWNER = "owner";
    private final String NULL = "null";
    private final String UNKNOWN = "unk";

    private final String FROM = "from";
    private final String TO = "to";
    private final String TRAINS = "trains";
    private final String SCHEDULES = "schedules";
    private final String WAGONS = "wagons";
    private final String NAME = "name";
    private final String BUSINESS_WAGON_NO_FB = "businessWagonNo";
    private final String ECONOMY_WAGON_NO_FB = "economyWagonNo";
    private final String BUSINESS_PRICE_FB = "businessPrice";
    private final String ECONOMY_PRICE_FB = "economyPrice";

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
                COMPANY_NAME + " TEXT, " +
                COMPANY_ID + " TEXT, " +
                TRAIN_ID + " TEXT, " +
                DEPARTURE_TIME + " LONG, " +
                DEPARTURE + " TEXT, " +
                ARRIVAL + " TEXT, " +
                WAGON_NO + " INTEGER, " +
                SEAT_NO + " TEXT, " +
                BUSINESS_WAGON_NO + " INTEGER, " +
                ECONOMY_WAGON_NO + " INTEGER, " +
                BUSINESS_PRICE + " DOUBLE, " +
                ECONOMY_PRICE + " DOUBLE, " +
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
        Calendar departureTimeCalendar;
        Customer customer;
        String companyName;
        String companyId;
        String trainId;
        String year;
        String month;
        String day;
        String hour;
        String minute;
        long departureTime;
        String from;
        String to;
        int wagonNo;
        String customerId;
        int businessWagonNo;
        int economyWagonNo;
        double businessPrice;
        double economyPrice;
        String seatNo;

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
        departureTimeCalendar = schedule.getDepartureDate();
        customer = ticket.getCustomer();

        companyName = company.getName();
        companyId = company.getCompanyId();
        trainId = train.getId();
        businessWagonNo = train.getBusinessWagonNum();
        economyWagonNo = train.getEconomyWagonNum();
        businessPrice = train.getBusinessPrice();
        economyPrice = train.getEconomyPrice();
        year = String.valueOf( departureTimeCalendar.get( Calendar.YEAR));
        month = String.valueOf( departureTimeCalendar.get( Calendar.MONTH));
        day = String.valueOf( departureTimeCalendar.get( Calendar.DAY_OF_MONTH));
        hour = String.valueOf( departureTimeCalendar.get( Calendar.HOUR_OF_DAY));
        minute = String.valueOf( departureTimeCalendar.get( Calendar.MINUTE));

        if ( month.length() == 1) {
            month = "0" + month;
        }
        if ( day.length() == 1) {
            day = "0" + day;
        }
        if ( hour.length() == 1) {
            hour = "0" + hour;
        }
        if ( minute.length() == 1) {
            minute = "0" + minute;
        }
        departureTime = Long.parseLong( year + month + day + hour + minute);

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

        add( companyName, companyId, trainId, departureTime, from, to, wagonNo, seatNo, businessWagonNo, economyWagonNo, businessPrice, economyPrice, customerId);
    }

    /**
     * Adds given ticket to the database
     * @param companyName company name
     * @param companyId company id
     * @param trainId train id
     * @param departureTime departure time as long value
     * @param from departure place
     * @param to arrival place
     * @param wagonNo wagon number
     * @param seatNo seat number
     * @param businessWagonNo business wagon number
     * @param economyWagonNo economy wagon no
     * @param businessPrice business price
     * @param economyPrice economy price
     * @param customerId customer id of the owner
     */
    private void add( String companyName, String companyId, String trainId, long departureTime, String from, String to, int wagonNo, String seatNo, int businessWagonNo, int economyWagonNo, double businessPrice, double economyPrice, String customerId) {
        // Variables
        SQLiteDatabase db;
        ContentValues values;

        // Code
        db = this.getWritableDatabase();
        values = new ContentValues();

        values.put( COMPANY_NAME, companyName);
        values.put( COMPANY_ID, companyId);
        values.put( TRAIN_ID, trainId);
        values.put( DEPARTURE_TIME, departureTime);
        values.put( DEPARTURE, from);
        values.put( ARRIVAL, to);
        values.put( WAGON_NO, wagonNo);
        values.put( SEAT_NO, seatNo);
        values.put( BUSINESS_WAGON_NO, businessWagonNo);
        values.put( ECONOMY_WAGON_NO, economyWagonNo);
        values.put( BUSINESS_PRICE, businessPrice);
        values.put( ECONOMY_PRICE, economyPrice);
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
                    add( ticket);
                }
            }

            saveToServer( new ServerSyncListener() {
                @Override
                public void onSync( boolean isSynced) {
                    listener.onSync( isSynced);
                }
            });
        }
    }

    /**
     * Sets the owner of given seat as the given customer
     * @param ticket to be bought
     * @param listener ServerSyncListener interface that is called
     *                 when data is sent to server
     */
    public void buy( Ticket ticket, ServerSyncListener listener) {
        // Variables
        SQLiteDatabase db;
        int dbId;

        // Code
        if ( isConnectedToInternet()) {
            db = this.getWritableDatabase();
            dbId = getDbId( ticket.getSeat());

            db.execSQL( "UPDATE " + TABLE_NAME + " SET " + OWNER + " = '" + ticket.getCustomer().getId() + "' WHERE " + ID + " = " + dbId + ";");
            db.close();

            saveToServer( dbId, new ServerSyncListener() {
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
        Calendar departureTimeCalendar;
        String year;
        String month;
        String day;
        String hour;
        String minute;
        long departureTime;
        Customer customer;

        // Code
        db = this.getWritableDatabase();

        wagon = seat.getLinkedWagon();
        schedule = wagon.getLinkedSchedule();
        from = schedule.getDeparturePlace();
        to = schedule.getArrivalPlace();
        train = schedule.getLinkedTrain();
        company = train.getLinkedCompany();
        departureTimeCalendar = schedule.getDepartureDate();

        year = String.valueOf( departureTimeCalendar.get( Calendar.YEAR));
        month = String.valueOf( departureTimeCalendar.get( Calendar.MONTH));
        day = String.valueOf( departureTimeCalendar.get( Calendar.DAY_OF_MONTH));
        hour = String.valueOf( departureTimeCalendar.get( Calendar.HOUR_OF_DAY));
        minute = String.valueOf( departureTimeCalendar.get( Calendar.MINUTE));

        if ( month.length() == 1) {
            month = "0" + month;
        }
        if ( day.length() == 1) {
            day = "0" + day;
        }
        if ( hour.length() == 1) {
            hour = "0" + hour;
        }
        if ( minute.length() == 1) {
            minute = "0" + minute;
        }
        departureTime = Long.parseLong( year + month + day + hour + minute);

        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COMPANY_ID + " = " + company.getCompanyId() + " AND "
                + TRAIN_ID + " = " + train.getId() + " AND "
                + DEPARTURE_TIME + " = " + departureTime + " AND "
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
     * Sends query to get a list of given customer's tickets
     * @param customer customer, owner of tickets
     * @return list of tickets
     */
    public ArrayList<Ticket> getBoughtTickets( Customer customer) {
        // Variables
        SQLiteDatabase db;
        Cursor data;

        // Code
        db = this.getWritableDatabase();
        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + OWNER + " = '" + customer.getId() + "';", null);
        return dataToArrayList( data);
    }

    /**
     * Sends query to get a list of given company's tickets sold in last week
     * @param company company
     * @return list of tickets
     */
    public ArrayList<Ticket> getRecentlySoldTickets( Company company) {
        // Variables
        SQLiteDatabase db;
        Cursor data;
        long departureTimeStart;
        long departureTimeEnd;
        Calendar calendarStart;
        Calendar calendarEnd;

        // Code

        calendarEnd = Calendar.getInstance();
        departureTimeEnd = getLongFromCalendar( calendarEnd);

        calendarStart = calendarEnd;
        calendarStart.add( Calendar.DAY_OF_MONTH, -7);
        departureTimeStart = getLongFromCalendar( calendarStart);

        db = this.getWritableDatabase();
        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + COMPANY_ID + " = '" + company.getCompanyId() + "' AND " + DEPARTURE_TIME + " >= " + departureTimeStart + " AND " + DEPARTURE_TIME + " <= " + departureTimeEnd + ";", null);
        return dataToArrayList( data);
    }

    /**
     * Sends query to get a list of tickets available to buy
     * @param departure departure place
     * @param arrival arrival place
     * @param departureTime departure time
     * @return list of tickets
     */
    public ArrayList<Ticket> getQueriedTickets( Place departure, Place arrival, Calendar departureTime) {
        // Variables
        SQLiteDatabase db;
        Cursor data;
        long departureTimeStart;
        long departureTimeEnd;
        Calendar calendarStart;
        Calendar calendarEnd;

        // Code
        calendarEnd = departureTime;
        departureTimeEnd = getLongFromCalendar( calendarEnd);

        calendarStart = calendarEnd;
        calendarStart.add( Calendar.DAY_OF_MONTH, 1);
        departureTimeStart = getLongFromCalendar( calendarStart);

        db = this.getWritableDatabase();
        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + DEPARTURE + " = '" + departure.getName() + "' AND " + ARRIVAL + " = '" + arrival.getName() + "' AND " + DEPARTURE_TIME + " >= " + departureTimeStart + " AND " + DEPARTURE_TIME + " <= " + departureTimeEnd + " AND " + OWNER + " = ''" + NULL +"'';", null);
        return dataToArrayList( data);
    }

    /**
     * Creates long representation of the given calendar object
     * @param calendar calendar to be converted
     * @return long representation
     */
    private long getLongFromCalendar( Calendar calendar) {
        // Variables
        String year;
        String month;
        String day;
        String hour;
        String minute;

        // Code
        year = String.valueOf( calendar.get( Calendar.YEAR));
        month = String.valueOf( calendar.get( Calendar.MONTH));
        day = String.valueOf( calendar.get( Calendar.DAY_OF_MONTH));
        hour = String.valueOf( calendar.get( Calendar.HOUR_OF_DAY));
        minute = String.valueOf( calendar.get( Calendar.MINUTE));

        if ( month.length() == 1) {
            month = "0" + month;
        }
        if ( day.length() == 1) {
            day = "0" + day;
        }
        if ( hour.length() == 1) {
            hour = "0" + hour;
        }
        if ( minute.length() == 1) {
            minute = "0" + minute;
        }

        return Long.parseLong( year + month + day + hour + minute);
    }

    /**
     * Extracts tickets from given data
     * @param data data to be analyzed
     * @return list of tickets
     */
    private ArrayList<Ticket> dataToArrayList( Cursor data) {
        // Variables
        ArrayList<Ticket> tickets;
        String companyName;
        String companyId;
        String trainId;
        String departureTime;
        String departurePlaceName;
        String arrivalPlaceName;
        int wagonNo;
        String seatNo;
        int businessWagonNo;
        int economyWagonNo;
        double businessPrice;
        double economyPrice;
        String customerId;
        Company company;
        Train train;
        Place defaultPlace;
        Schedule schedule;
        Line line;
        Place departurePlace;
        Place arrivalPlace;
        Wagon wagon;
        Seat seat;
        Customer customer;
        Ticket ticket;

        // Code
        tickets = new ArrayList<>();
        if ( data.getCount() != 0) {
            data.moveToFirst();
            do {
                // Get ticket info
                companyName = data.getString( data.getColumnIndex( COMPANY_NAME));
                companyId = data.getString( data.getColumnIndex( COMPANY_ID));
                trainId = data.getString( data.getColumnIndex( TRAIN_ID));
                departureTime = String.valueOf( data.getLong( data.getColumnIndex( DEPARTURE_TIME)));
                departurePlaceName = data.getString( data.getColumnIndex( DEPARTURE));
                arrivalPlaceName = data.getString( data.getColumnIndex( ARRIVAL));
                wagonNo = data.getInt( data.getColumnIndex( WAGON_NO));
                seatNo = data.getString( data.getColumnIndex( SEAT_NO));
                businessWagonNo = data.getInt( data.getColumnIndex( BUSINESS_WAGON_NO));
                economyWagonNo = data.getInt( data.getColumnIndex( ECONOMY_WAGON_NO));
                businessPrice = data.getLong( data.getColumnIndex( BUSINESS_PRICE));
                economyPrice = data.getLong( data.getColumnIndex( ECONOMY_PRICE));
                customerId = data.getString( data.getColumnIndex( OWNER));

                // Create ticket
                company = new Company( companyName, companyId, context);

                defaultPlace = new Place( "Train" + trainId, 0, 0);
                train = new Train( company, defaultPlace, businessWagonNo, economyWagonNo, businessPrice, economyPrice, trainId);

                departurePlace = new Place( departurePlaceName, 0, 0); // TODO: Get coordinates
                arrivalPlace = new Place( arrivalPlaceName, 0, 0); // TODO: Get coordinates
                line = new Line( departurePlace, arrivalPlace);
                schedule = new Schedule( departureTime, "", line, businessWagonNo, economyWagonNo, train);
                train.addSchedule( schedule);

                wagon = schedule.getWagon( wagonNo - 1);
                seat = wagon.getSeat( seatNo);

                customer = new Customer( customerId, context);

                ticket = new Ticket( seat, customer);
                tickets.add( ticket);
            } while ( data.moveToNext());
        }

        return tickets;
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
     * Saves ticket data on local database to server
     * @param listener ServerSyncListener interface that is called
     *                 when data is sent to server
     */
    private void saveToServer( ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        SQLiteDatabase db;
        Cursor data;
        String companyId;
        String trainId;
        String departureTime;
        String wagonNo;
        String seatNo;
        String customerId;

        // Code
        if ( isConnectedToInternet()) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Companies/");

            db = this.getWritableDatabase();
            data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + ";", null);

            if ( !isEmpty()) {
                data.moveToFirst();
                do {
                    companyId = data.getString( data.getColumnIndex( COMPANY_ID));
                    trainId = data.getString( data.getColumnIndex( TRAIN_ID));
                    departureTime = String.valueOf( data.getLong( data.getColumnIndex( DEPARTURE_TIME)));
                    wagonNo = String.valueOf( data.getInt( data.getColumnIndex( WAGON_NO)));
                    seatNo = data.getString( data.getColumnIndex( SEAT_NO));
                    customerId = data.getString( data.getColumnIndex( OWNER));

                    reference.child( companyId).child( TRAINS).child( trainId).child( SCHEDULES).child( departureTime).child( WAGONS).child( wagonNo).child( seatNo).setValue( customerId);
                } while ( data.moveToNext());
            }

            listener.onSync( true);
        }
        else {
            listener.onSync( false);
        }
    }

    /**
     * Saves ticket data on local database to server
     * @param dbId single database id to be saved to server
     * @param listener ServerSyncListener interface that is called
     *                 when data is sent to server
     */
    private void saveToServer( int dbId, ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        SQLiteDatabase db;
        Cursor data;
        String companyId;
        String trainId;
        String departureTime;
        String wagonNo;
        String seatNo;
        String customerId;

        // Code
        if ( isConnectedToInternet()) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Companies/");

            db = this.getWritableDatabase();
            data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " = " + dbId + ";", null);

            companyId = data.getString( data.getColumnIndex( COMPANY_ID));
            trainId = data.getString( data.getColumnIndex( TRAIN_ID));
            departureTime = String.valueOf( data.getLong( data.getColumnIndex( DEPARTURE_TIME)));
            wagonNo = String.valueOf( data.getInt( data.getColumnIndex( WAGON_NO)));
            seatNo = data.getString( data.getColumnIndex( SEAT_NO));
            customerId = data.getString( data.getColumnIndex( OWNER));

            reference.child( companyId).child( TRAINS).child( trainId).child( SCHEDULES).child( departureTime).child( WAGONS).child( wagonNo).child( seatNo).setValue( customerId);

            listener.onSync( true);
        }
        else {
            listener.onSync( false);
        }
    }

    /**
     * Updates local database tickets with data retrieved from server
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server
     */
    public void update( ServerSyncListener listener) { // Has the same function with saveToServer() method of other classes
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
                    String companyName;
                    String companyId;
                    String trainId;
                    String departureTimeStr;
                    long departureTime;
                    String from;
                    String to;
                    int wagonNo;
                    String seatNo;
                    int businessWagonNo;
                    int economyWagonNo;
                    double businessPrice;
                    double economyPrice;
                    String customerId;

                    // Code
                    reference.removeEventListener( this);

                    // Erase all database data and refill with server data
                    reset( db);
                    if ( dataSnapshot.exists()) {
                        for ( DataSnapshot company : dataSnapshot.getChildren()) {
                            companyId = company.getKey();
                            companyName = company.child( NAME).getValue( String.class);

                            for ( DataSnapshot train : company.child( TRAINS).getChildren()) {
                                trainId = train.getKey();
                                businessWagonNo = Integer.parseInt( train.child( BUSINESS_WAGON_NO_FB).getValue( String.class));
                                economyWagonNo = Integer.parseInt( train.child( ECONOMY_WAGON_NO_FB).getValue( String.class));
                                businessPrice = Double.parseDouble( train.child( BUSINESS_PRICE_FB).getValue( String.class));
                                economyPrice = Double.parseDouble( train.child( ECONOMY_PRICE_FB).getValue( String.class));

                                for ( DataSnapshot schedule : train.child( SCHEDULES).getChildren()) {
                                    departureTimeStr = schedule.getKey();
                                    departureTime = Long.parseLong( departureTimeStr);
                                    from = schedule.child( FROM).getValue( String.class);
                                    to = schedule.child( TO).getValue( String.class);

                                    for ( DataSnapshot wagon : schedule.child( WAGONS).getChildren()) {
                                        wagonNo = Integer.parseInt( wagon.getKey());

                                        for ( DataSnapshot seat : wagon.getChildren()) {
                                            seatNo = seat.getKey();
                                            customerId = seat.getValue( String.class);

                                            add( companyName, companyId, trainId, departureTime, from, to, wagonNo, seatNo, businessWagonNo, economyWagonNo, businessPrice, economyPrice, customerId);
                                        }
                                    }
                                }
                            }
                        }
                    }

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
            listener.onSync( false);
        }
    }

    public interface ServerSyncListener {
        void onSync( boolean isSynced);
    }
}
