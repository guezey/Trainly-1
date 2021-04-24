package com.fliers.trainly.models;

import java.util.ArrayList;

/*
* A class for wagons. It will contain properties indicating prices for economy class and business class,
* an ArrayList for seats, a boolean property indicating wagons business condition and a Schedule
* @author Erkin Aydın
* @version 23-04-2021__v/1.1
*/

public class Wagon {
    final int BUSINESS_SEAT_NUM = 15;
    final int ECONOMY_SEAT_NUM = 30;
    ArrayList<Seat> seats;
    boolean business;
    Schedule linkedSchedule;

    /**
    * The constructor of the Wagon class
    * @param schedule
    * @param b
    */
    public Wagon( Schedule schedule, boolean b) {

        linkedSchedule = schedule;
        business = b;
        createSeats();
    }

    /**
    * Creates empty seats
    */
    private void createSeats() {

        if( isBusiness()) {
            seats = new ArrayList<Seat>( BUSINESS_SEAT_NUM);
        }
        else {
            seats = new ArrayList<Seat>( ECONOMY_SEAT_NUM);
        }
    }

    /**
    * @return business
    */
    public boolean isBusiness() {
        return business;
    }

    /**
    * @param i
    * @return the specified seat
    */
    public Seat getSeat( int i) {
        return seats.get(i);
    }

    /**
    * @return total seat number depending on whether the wagon is business class or not
    */
    public int getTotalSeatNo() {
        if( isBusiness()) {
            return BUSINESS_SEAT_NUM;
        }
        else {
            return ECONOMY_SEAT_NUM;
        }
    }

    /**
    * @return linkedSchedule
    */
    public Schedule getLinkedSchedule() {
        return linkedSchedule;
    }
}
