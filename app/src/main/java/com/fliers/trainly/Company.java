package com.fliers.trainly;

import java.util.ArrayList;

/**
 * Class that represents company users.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Company extends User {

    //properties
    private int balance;
    private ArrayList<Train> trains;
    private ArrayList<Line> lines;
    private ArrayList<Employee> employees;

    //constructor
    public Company(/*to be done*/) {
        //to be done
    }

    //methods

    /**
     * Sets the balance.
     * @param newBalance new balance value
     */
    public void setBalance( double newBalance) {
        balance = newBalance;
    }

    /**
     * Returns balance.
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns employees list.
     * @return employees
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Returns lines list
     * @return lines
     */
    public ArrayList<Line> getLines() {
        return lines;
    }

    /**
     * Returns trains list
     * @return trains
     */
    public ArrayList<Train> getTrains() {
        return trains;
    }

    /**
     * Adds a new trains to trains list.
     * @param newTrain
     */
    public void addTrain( Train newTrain) {
        trains.add( newTrain);
    }

    /**
     * Adds a new line to lines list.
     * @param newLine
     */
    public void addLine( Line newLine) {
        lines.add( newLine);
    }

    /**
     * Adds a new employee to employees list.
     * @param newEmployee
     */
    public void addEmployee( Employee newEmployee) {
        employees.add( newEmployee);
    }

    /**
     * Removes a train from trains list.
     * @param newEmployee
     */
    public void removeTrain( Object train) {
        trains.remove( train);
    }

    /**
     * Removes a line from lines list.
     * @param newEmployee
     */
    public void removeLine( Object line) {
        lines.remove( line);
    }

    /**
     * Removes an employee from employees list.
     * @param newEmployee
     */
    public void removeEmployee( Object employee) {
        employees.remove( employee);
    }

    /**
     * Returns last week's customer number for this company.
     * @return customer number
     */
    public int getLastWeeksCustomerNum() {
        //to be done
        return 0;
    }

    /**
     * Returns last week's customer number for this company.
     * @return revenue
     */
    public double getLastWeeksRevenue() {
        //to be done
        return 0;
    }
}
