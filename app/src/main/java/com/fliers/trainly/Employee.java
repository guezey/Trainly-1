package com.fliers.trainly;

/**
 * Class that represents train machinists.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Employee {

    //properties
    private int id;
    Train assignedTrain;

    //constructor
    public Employee( int id, Train assignedTrain) {
        this.id = id;
        this.assignedTrain = assignedTrain;
    }

    //methods

    public int getId() {
        return id;
    }

    public Train getAssignedTrain() {
        return assignedTrain;
    }

    public boolean startTrip() {
        //to be done
        return false;
    }

}
