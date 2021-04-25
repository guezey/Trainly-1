package com.fliers.trainly.models;

import android.content.Context;

/**
 * Customer Object 
 * @author Cengizhan Terzioğlu
 * @version 22.04.2021
*/
public class Customer extends User {
    
    // Properties
    private double discountPoints;

    // Constructors
    public Customer( Context context) {
        super( context);
        //to be done
        setDiscountPoints( 0 );
    }

    // Methods
    public double getDiscountPoints() {
        return this.discountPoints;
    }

    public void setDiscountPoints( double discountPoints ) {
        this.discountPoints = discountPoints;
    }

    public void buyTicket( Ticket ticket ) {
        //to be done
    }
}