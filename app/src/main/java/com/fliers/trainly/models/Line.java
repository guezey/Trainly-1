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
    private int distance;

    //constructor

    /**
     * Creates a train line with departure and arrival info.
     * @param departure departure place
     * @param arrival arrival place
     */
    public Line( Place departure, Place arrival) {
        this.arrival = arrival;
        this.departure = departure;
        distance = calculateDistance();
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

    public int getDistance() {
        return distance;
    }

    private int calculateDistance() {
        final int earthRadiusKm = 6371;
        double depLat;
        double arrLat;

        double diffLat = Math.toRadians(arrival.getLatitude() - departure.getLatitude());
        double diffLon = Math.toRadians(arrival.getLongitude() - departure.getLongitude());

        depLat = Math.toRadians(departure.getLatitude());
        arrLat = Math.toRadians(arrival.getLatitude());

        double a = Math.sin(diffLat/2) * Math.sin(diffLat/2) +
                Math.sin(diffLon/2) * Math.sin(diffLon/2) * Math.cos(depLat) * Math.cos(arrLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (int) (earthRadiusKm * c);
    }
}
