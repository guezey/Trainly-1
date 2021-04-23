package com.fliers.trainly;

/**
 * Class that represents train machinists.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Employee {

    //properties
    private int id;
    private Train assignedTrain;

    //constructor
    /**
     * Creates an employee with ID and train info.
     * @param id employee ID
     * @param assignedTrain employee's assigned train
     */
    public Employee( int id, Train assignedTrain) {
        this.id = id;
        this.assignedTrain = assignedTrain;
    }

    //methods
    /**
     * Returns employee's ID
     * @return ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns employee's assigned train.
     * @return assigned train
     */
    public Train getAssignedTrain() {
        return assignedTrain;
    }

    /**
     * Starts a trip for assigned train if departure time has come.
     * @return true if departure time has come, otherwise false
     */
    public boolean startTrip() {
        //to be done
        return false;
    }

}
