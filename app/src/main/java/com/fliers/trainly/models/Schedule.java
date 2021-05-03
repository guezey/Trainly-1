package com.fliers.trainly.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

/**
 * Schedule Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Schedule {
    
    // Properties
    private Calendar departureDate;
    private Calendar arrivalDate;
    private ArrayList<Wagon> wagons;
    private Train linkedTrain;
    private Line line;
    private double businessPrice;
    private double economyPrice;

    // Constructors
    public Schedule( Calendar departureDate, Calendar arrivalDate, Line line, int business, int economy, Train linkedTrain) {
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.line = line;
        this.linkedTrain = linkedTrain;
        businessPrice = calculateBusinessPrice();
        economyPrice = calculateEconomyPrice();
        wagons = new ArrayList<>();
        createWagons( business, economy );
    }

    public Schedule( String departureDateId, String arrivalDateId, Line line, int business, int economy, Train linkedTrain) {
        this.departureDate = getDateFromIdRepresentation( departureDateId);
        this.arrivalDate = getDateFromIdRepresentation( arrivalDateId);
        this.line = line;
        this.linkedTrain = linkedTrain;
        wagons = new ArrayList<>();
        createWagons( business, economy );
    }

    // Methods
    public Calendar getDepartureDate() {
        return this.departureDate;
    }

    public Calendar getArrivalDate() {
        return this.arrivalDate;
    }

    public ArrayList<Wagon> getWagons() {
        return this.wagons;
    }

    public Wagon getWagon( int wagonIndex ) {
        return getWagons().get( wagonIndex );
    }

    public Train getLinkedTrain() {
        return this.linkedTrain;
    }

    /**
     * @return the departure place of the train
     */
    public Place getDeparturePlace() {
        return line.getDeparture();
    }

    /**
     * @return the arrival place
     */
    public Place getArrivalPlace() {
        return line.getArrival();
    }

    /**
     * Sets the new line
     * @param a
     */
    public void setLine( Line a) {
        line = a;
    }

    /**
     * Returns schedule's line.
     * @return line
     */
    public Line getLine() {
        return line;
    }

    private void createWagons( int business, int economy ) {
        Wagon currentWagon;

        for (int i = 0; i < business; i++) {
            currentWagon = new Wagon(this, true, i + 1);
            this.getWagons().add(currentWagon);
        }

        for (int j = 0; j < economy; j++) {
            currentWagon = new Wagon(this, false, business + j + 1);
            this.getWagons().add(currentWagon);
        }
    }


    /**
     * Calculates String version of given date to be used in ids
     * @return date as string
     * @author Alp Afyonluoğlu
     */
    public String getIdRepresentation( Calendar date) {
        // Variables
        String year;
        String month;
        String day;
        String hour;
        String minute;

        // Code
        year = String.valueOf( date.get( Calendar.YEAR));
        month = String.valueOf( date.get( Calendar.MONTH));
        day = String.valueOf( date.get( Calendar.DAY_OF_MONTH));
        hour = String.valueOf( date.get( Calendar.HOUR_OF_DAY));
        minute = String.valueOf( date.get( Calendar.MINUTE));

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

        return year + month + day + hour + minute;
    }

    /**
     * Calculates Date version of given id representation string
     * @param idRepresentation date as string
     * @return date as Date object
     * @author Alp Afyonluoğlu
     */
    public Calendar getDateFromIdRepresentation( String idRepresentation) {
        // Variables
        int year;
        int month;
        int day;
        int hour;
        int minute;
        Calendar date;

        // Code
        year = Integer.parseInt( idRepresentation.substring( 0, 4));
        month = Integer.parseInt( idRepresentation.substring( 4, 6));
        day = Integer.parseInt( idRepresentation.substring( 6, 8));
        hour = Integer.parseInt( idRepresentation.substring( 8, 10));
        minute = Integer.parseInt( idRepresentation.substring( 10, 12));

        date = Calendar.getInstance();
        date.set( year, month - 1, day, hour, minute);

        return date;
    }

    /**
     * Calculates and returns schedule's business ticket price.
     * @return business price
     * @author Ali Emir Güzey
     */
    private double calculateBusinessPrice() {
        BigDecimal bd = BigDecimal.valueOf(linkedTrain.getBusinessPrice() * line.getDistance() / 10);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Calculates and returns schedule's economy ticket price.
     * @return economy price
     * @author Ali Emir Güzey
     */
    private double calculateEconomyPrice() {
        BigDecimal bd = BigDecimal.valueOf(linkedTrain.getEconomyPrice() * line.getDistance() / 10);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Returns schedule's business ticket price.
     * @return business price
     * @author Ali Emir Güzey
     */
    public double getBusinessPrice() {
        return businessPrice;
    }

    /**
     * Returns schedule's economy ticket price.
     * @return economy price
     * @author Ali Emir Güzey
     */
    public double getEconomyPrice() {
        return economyPrice;
    }
}