package com.fliers.trainly.models;

import android.content.Context;

import java.util.ArrayList;

/**
 * Class that represents company users.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Company extends User {
    // Properties
    private final String COMPANY_ID = "company_id";
    private final String BALANCE = "balance";

    private String companyId;
    private int balance;
    private ArrayList<Train> trains;
    private ArrayList<Line> lines;
    private ArrayList<Employee> employees;

    // Constructor
    /**
     * Initializes a new company
     */
    public Company( Context context) {
        super( context);
        companyId = "000";
        balance = 0;
        trains = new ArrayList<>();
        lines = new ArrayList<>();
        employees = new ArrayList<>();
    }

    // Methods
    /**
     * Sets the balance.
     * @param newBalance new balance value
     */
    public void setBalance( int newBalance) {
        balance = newBalance;
    }

    /**
     * Returns balance.
     * @return balance
     */
    public int getBalance() {
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
     * @param newTrain a new train
     */
    public void addTrain( Train newTrain) {
        trains.add( newTrain);
    }

    /**
     * Adds a new line to lines list.
     * @param newLine a new line
     */
    public void addLine( Line newLine) {
        lines.add( newLine);
    }

    /**
     * Adds a new employee to employees list.
     * @param newEmployee a new employee
     */
    public void addEmployee( Employee newEmployee) {
        employees.add( newEmployee);
    }

    /**
     * Removes a train from trains list.
     * @param train a train from list
     */
    public void removeTrain( Object train) {
        trains.remove( train);
    }

    /**
     * Removes a line from lines list.
     * @param line a line from list
     */
    public void removeLine( Object line) {
        lines.remove( line);
    }

    /**
     * Removes an employee from employees list.
     * @param employee an employee from list
     */
    public void removeEmployee( Object employee) {
        employees.remove( employee);
    }

    /**
     * Returns last week's customer number for this company.
     * @return customer number
     */
    public int getLastWeeksCustomerNum() {
        // TODO: to be done
        return 0;
    }

    /**
     * Returns last week's customer number for this company.
     * @return revenue
     */
    public double getLastWeeksRevenue() {
        // TODO: to be done
        return 0;
    }

}
