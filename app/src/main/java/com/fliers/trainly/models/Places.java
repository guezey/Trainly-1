package com.fliers.trainly.models;

import java.util.ArrayList;

/**
 * Class to store all places in a list.
 * @author Ali Emir Güzey
 * @version 29.04.2021
 */
public class Places {

    //properties
    ArrayList<Place> places;

    //constructor
    /**
     * creates an empty place list.
     */
    public Places() {
        places = new ArrayList<>();
    }

    /**
     * Creates a place list with given list.
     * @param places given list
     */
    public Places( ArrayList<Place> places) {
        this.places = places;
    }

    //methods
    /**
     * Finds the place with the given name and returns it.
     * @param name place name
     * @return place with the given name
     */
    public Place findByName( String name) {
        for ( Place p: places ) {
            if (p.getName().equals( name))
                return p;
        }

        return null;
    }

    /**
     * Adds the given place to the list.
     * @param p given place
     */
    public void add( Place p) {
        places.add( p);
    }

    /**
     * Removes the given place from the list.
     * @param p given place
     */
    public void remove( Object p) {
        places.remove( p);
    }

    /**
     * Returns the whole list.
     * @return whole place list
     */
    public ArrayList<Place> getAll() {
        return places;
    }
}
