package com.fliers.trainly.models;

/**
 * Ticket Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Ticket {
    
    // Properties
    private Seat seat;
    private Customer customer;
    private String comment;
    private int starRating;

    // Constructors
    public Ticket( Seat seat, Customer customer ) {
        this.seat = seat;
        this.customer = customer;
        setComment( "" );
        setStarRating(0);
    }

    // Methods
    public String getId() {
        // TODO: return this.id;
        return "";
    }

    public Seat getSeat() {
        return this.seat;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment( String comment ) {
        this.comment = comment;
    }

    public int getStarRating() {
        return this.starRating;
    }

    public void setStarRating( int starRating ) {
        this.starRating = starRating;
    }
}