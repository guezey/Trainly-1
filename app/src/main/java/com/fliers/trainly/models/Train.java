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
    String id;
    double lat;
    double lon;
    int businessWagonNum;
    int economyWagonNum;
    ArrayList<Schedule> schedules;
    Company linkedCompany;

    /**
    * Constructor of the Train class
    * @param company
    * @param spawnPlace
    * @param businessWagonNum
    * @param economyWagonNum
    * @param bPrice
    * @param ePrice
    * @param id
     */
    public Train( Company company, Place spawnPlace, int businessWagonNum,
                int economyWagonNum, double bPrice, double ePrice, String id) {
        linkedCompany = company;
        lat = spawnPlace.getLatitude();
        lon = spawnPlace.getLongitude();
        this.businessWagonNum = businessWagonNum;
        this.economyWagonNum = economyWagonNum;
        this.schedules = schedules;
        businessPrice = bPrice;
        economyPrice = ePrice;
        schedules = new ArrayList<>();
        this.id = id;
    }
    /**
    * @return id
    */
    public String getId() {
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

            // TODO: Modify for Calendar class
            /* if( d.after( schedules.get(i).getDepartureDate()) && d.before( schedules.get(i).getArrivalDate())
                    || d.compareTo( schedules.get(i).getDepartureDate()) == 0) {
                return schedules.get(i);
            } */
        }
        return null;
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
    * @return the company that the train belongs
    */
    public Company getLinkedCompany() {
        return linkedCompany;
    }

    /**
     * Getter method for business wagon number
     * @return business wagon number
     * @author Alp Afyonluoğlu
     */
    public int getBusinessWagonNum() {
        return businessWagonNum;
    }

    /**
     * Getter method for economy wagon number
     * @return economy wagon number
     * @author Alp Afyonluoğlu
     */
    public int getEconomyWagonNum() {
        return economyWagonNum;
    }
}
