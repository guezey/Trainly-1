package com.fliers.trainly.models;

/**
 * Class that represents train lines.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Line {

    //properties
    private Place arrival;
    private Place departure;

    //constructor

    /**
     * Creates a train line with departure and arrival info.
     * @param departure departure place
     * @param arrival arrival place
     */
    public Line( Place departure, Place arrival) {
        this.arrival = arrival;
        this.departure = departure;
    }

    //methods

    /**
     * Returns arrival place.
     * @return arrival
     */
    public Place getArrival() {
        return arrival;
    }

    /**
     * Returns departure place.
     * @return departure
     */
    public Place getDeparture() {
        return departure;
    }
}
