package com.fliers.trainly.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Tickets class managing multiple instances of the Ticket class
 * @author Alp Afyonluoğlu
 * @version 29.04.2021
 */
public class Tickets extends SQLiteOpenHelper {
    // Properties
    private final String TABLE_NAME = "tickets";
    private final String ID = "id";
    private final String COMPANY_ID = "company_id";
    private final String TRAIN_ID = "train_id";
    private final String DEPARTURE_YEAR = "dep_year";
    private final String DEPARTURE_MONTH = "dep_month";
    private final String DEPARTURE_DAY = "dep_day";
    private final String DEPARTURE_HOUR = "dep_hour";
    private final String DEPARTURE_MINUTE = "dep_minute";
    private final String WAGON_NO = "wagon_no";
    private final String SEAT_NO = "seat_no";
    private final String OWNER = "owner";
    private final String NULL = "null";

    private ArrayList<Ticket> tickets;

    // Constructors
    /**
     * Initializes the tickets database
     * @param context application context
     */
    public Tickets( Context context) {
        super( context, "TICKETS", null, 1);
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
    public void reset( SQLiteDatabase db) {
        // TODO: Make private

        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate( db);
    }

    /**
     * Adds given ticket to the database
     * @param ticket ticket to be added
     * @return whether ticket added successfully or not
     */
    public void add( Ticket ticket) {
        // Variables
        SQLiteDatabase db;
        ContentValues values;
        Seat seat;
        Wagon wagon;
        Schedule schedule;
        Train train;
        Company company;
        Calendar departureTime;
        Customer customer;

        // Code
        db = this.getWritableDatabase();
        values = new ContentValues();

        seat = ticket.getSeat();
        wagon = seat.getLinkedWagon();
        schedule = wagon.getLinkedSchedule();
        train = schedule.getLinkedTrain();
        company = train.getLinkedCompany();
        departureTime = schedule.getDepartureDate();
        customer = ticket.getCustomer();

        values.put( COMPANY_ID, company.getCompanyId());
        values.put( TRAIN_ID, train.getId());
        values.put( DEPARTURE_YEAR, departureTime.get( Calendar.YEAR));
        values.put( DEPARTURE_MONTH, departureTime.get( Calendar.MONTH));
        values.put( DEPARTURE_DAY, departureTime.get( Calendar.DAY_OF_MONTH));
        values.put( DEPARTURE_HOUR, departureTime.get( Calendar.HOUR_OF_DAY));
        values.put( DEPARTURE_MINUTE, departureTime.get( Calendar.MINUTE));
        values.put( WAGON_NO, wagon.getWagonNumber());
        values.put( SEAT_NO, seat.getSeatNumber());

        if ( customer == null || customer.getEmail().equals( "")) {
            values.put( OWNER, NULL);
        }
        else {
            values.put( OWNER, customer.getId());
        }

        db.insert( TABLE_NAME, null, values);
    }

    /**
     * Getter method for database id of the given ticket
     * @param ticket ticket to be searched
     * @return database id of the ticket
     */
    private int getTicketDbId( Ticket ticket) {
        // Variables
        SQLiteDatabase db;
        ContentValues values;
        Cursor data;
        Seat seat;
        Wagon wagon;
        Schedule schedule;
        Train train;
        Company company;
        Calendar departureTime;
        Customer customer;

        // Code
        db = this.getWritableDatabase();

        seat = ticket.getSeat();
        wagon = seat.getLinkedWagon();
        schedule = wagon.getLinkedSchedule();
        train = schedule.getLinkedTrain();
        company = train.getLinkedCompany();
        departureTime = schedule.getDepartureDate();
        customer = ticket.getCustomer();

        data = db.rawQuery( "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COMPANY_ID + " = " + company.getCompanyId() + " AND "
                + TRAIN_ID + " = " + train.getId() + " AND "
                + DEPARTURE_YEAR + " = " + departureTime.get( Calendar.YEAR) + " AND "
                + DEPARTURE_MONTH + " = " + departureTime.get( Calendar.MONTH) + " AND "
                + DEPARTURE_DAY + " = " + departureTime.get( Calendar.DAY_OF_MONTH) + " AND "
                + DEPARTURE_HOUR + " = " + departureTime.get( Calendar.HOUR_OF_DAY) + " AND "
                + DEPARTURE_MINUTE + " = " + departureTime.get( Calendar.MINUTE) + " AND "
                + WAGON_NO + " = " + wagon.getWagonNumber() + " AND "
                + SEAT_NO + " = " + seat.getSeatNumber() + ";", null);

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
}
