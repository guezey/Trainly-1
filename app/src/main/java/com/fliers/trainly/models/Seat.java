package com.fliers.trainly.models;

/**
* A class for seats in a Wagon
*@author Erkin Aydın
*@version 23-04-2021__v/1.1
*/
public class Seat {

    boolean isEmpty;
    String seatNumber;
    Wagon linkedWagon;

    /**
    * The constructor of the Seat class
    * @param seatNumber
    * @param linkedWagon
    */
    public Seat( String seatNumber, Wagon linkedWagon) {
        isEmpty = true;
        this.seatNumber = seatNumber;
        this.linkedWagon = linkedWagon;
    }

    /**
    * @return linkedWagon
    */
    public Wagon getLinkedWagon() {
        return linkedWagon;
    }

    /**
    * @return seatNumber
    */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
    * @return isEmpty
    */
    public boolean isEmpty() {
        return isEmpty;
    }

    /**
    * Sets the seat empty
    */
    public void setEmpty( boolean isEmpty) {
        this.isEmpty = isEmpty;
    }
}
