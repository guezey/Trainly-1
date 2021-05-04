package com.fliers.trainly.models.trips;

import com.fliers.trainly.models.trips.Train;

/**
 * Class that represents train machinists.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Employee {

    //properties
    private String name;
    private Train assignedTrain;

    //constructor
    /**
     * Creates an employee with name and train info.
     * @param name employee name
     * @param assignedTrain employee's assigned train
     */
    public Employee( String name, Train assignedTrain) {
        this.name = name;
        this.assignedTrain = assignedTrain;
    }

    //methods
    /**
     * Returns employee's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns employee's assigned train.
     * @return assigned train
     */
    public Train getAssignedTrain() {
        return assignedTrain;
    }

}
