package com.fliers.trainly.models;

import java.sql.Date;
import java.util.ArrayList;

/**
* A class for trains. It will contain everything necessary for a Train, from its position to ticket prices;
 from its line to the Company it belongs.
* @author Erkin Aydın
* @version 23-04-2021__v/1.1
*/

public class Train {

    double businessPrice;
    double economyPrice;
    static int idCounter; // Temporary (In TO-DO list)
    int id;
    double lat;
    double lon;
    int businessWagonNum;
    int economyWagonNum;
    Line line;
    ArrayList<Schedule> schedules;
    Company linkedCompany;

    /**
    * Constructor of the Train class
    * @param company
    * @param line
    * @param spawnPlace
    * @param businessWagonNum
    * @param economyWagonNum
    * @param bPrice
    * @param ePrice
    */
    public Train( Company company, Line line, Place spawnPlace, int businessWagonNum,
                int economyWagonNum, double bPrice, double ePrice) {
        linkedCompany = company;
        this.line = line;
        lat = spawnPlace.getLatitude();
        lon = spawnPlace.getLongitude();
        this.businessWagonNum = businessWagonNum;
        this.economyWagonNum = economyWagonNum;
        businessPrice = bPrice;
        economyPrice = ePrice;
        idCounter++; // Temporary (In TO-DO list)
        id = idCounter; // Temporaty (In TO-DO list)
    }
    /**
    * @return id
    */
    public int getId() {
        return id;
    }
    /**
    * Sets the new business wagon number
    * @param a
    */
    public void setBusinessWagonNum( int a) {
        businessWagonNum = a;
    }

    /**
    * Sets the new economy wagon number
    * @param a
    */
    public void setEconomyWagonNum( int a) {
        economyWagonNum = a;
    }

    /**
    * Adds a new schedule
    * @param s
    */
    public void addSchedule( Schedule s) {
        schedules.add(s);
    }

    /**
    * @param d
    * @return the current schedule
    */
    public Schedule getSchedule( Date d) {
        for( int i = 0; i < schedules.size(); i++) {

            if( d.after( schedules.get(i).getDepartureDate()) && d.before( schedules.get(i).getArrivalDate())
                    || d.compareTo( schedules.get(i).getDepartureDate()) == 0) {
                return schedules.get(i);
            }
        }
        return null;
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
    * @return the business class price
    */
    public double getBusinessPrice() {
        return businessPrice;
    }

    /**
    * @return the business price
    */
    public double getEconomyPrice() {
        return economyPrice;
    }

    /**
    * Sets the new line
    * @param a
    */
    public void setLine( Line a) {
        line = a;
    }

    /**
    * @return the company that the train belongs
    */
    public Company getLinkedCompany() {
        return linkedCompany;
    }
}
