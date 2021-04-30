package com.fliers.trainly.models;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that represents company users.
 * @author Ali Emir Güzey
 * @version 22.04.2021
 */
public class Company extends User {
    // Properties
    private final String COMPANY_ID = "companyId";
    private final String BALANCE = "balance";
    private final String TRAINS = "trainIds";
    private final String SCHEDULES = "schedules";
    private final String ESTIMATED_ARRIVAL = "estimatedArrival";
    private final String BUSINESS_WAGON_NO = "businessWagonNo";
    private final String ECONOMY_WAGON_NO = "economyWagonNo";
    private final String BUSINESS_PRICE = "businessPrice";
    private final String ECONOMY_PRICE = "economyPrice";
    private final String CURRENT_LOCATION = "currentLocation";
    private final String FROM = "from";
    private final String TO = "to";
    private final String EMPLOYEE_NAMES = "employeeNames";
    private final String EMPLOYEE_LINKED_TRAIN_IDS = "employeeLinkedTrainIds";
    private final String FEEDBACK_STARS = "feedbackStars";
    private final String FEEDBACK_COMMENTS = "feedbackComments";
    private final String AVERAGE_POINT = "averagePoint";
    private final String LINE_DEPARTURES = "lineDepartures";
    private final String LINE_ARRIVALS = "lineArrivals";
    private final String DEPARTURE = "departure";
    private final String ARRIVAL = "arrival";

    private String companyId;
    private int balance;
    private ArrayList<Train> trains;
    private ArrayList<Line> lines;
    private ArrayList<Ticket> anonymousFeedback;
    private ArrayList<Employee> employees;
    private double averagePoint;

    // Constructor
    /**
     * Initializes a new company
     * @param context application context
     */
    public Company( Context context) {
        super( context);
        companyId = "000";
        balance = 0;
        trains = new ArrayList<>();
        lines = new ArrayList<>();
        anonymousFeedback = new ArrayList<>();
        employees = new ArrayList<>();
        averagePoint = 0;
    }

    /**
     * Initializes a new company with given company id
     * @param name name of the company
     * @param companyId company id
     * @param context application context
     */
    public Company( String name, String companyId, Context context) {
        super( context);
        this.name = name;
        this.companyId = companyId;
        balance = 0;
        trains = new ArrayList<>();
        lines = new ArrayList<>();
        anonymousFeedback = new ArrayList<>();
        employees = new ArrayList<>();
        averagePoint = 0;
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

    /**
     * Getter method for company id
     * @return company id
     * @author Alp Afyonluoğlu
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * Getter method for tickets with anonymous feedback
     * @return list of tickets
     * @author Alp Afyonluoğlu
     */
    public ArrayList<Ticket> getAnonymousFeedback() {
        return anonymousFeedback;
    }

    /**
     * Getter method for average point
     * @return average point
     * @author Alp Afyonluoğlu
     */
    public double getAveragePoint() {
        return averagePoint;
    }

    /**
     * Calculates average point by using feedback data
     * @author Alp Afyonluoğlu
     */
    private void calculateAveragePoint() {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        int sum;

        // Code
        sum = 0;
        for ( int count = 0; count < anonymousFeedback.size(); count++) {
            sum = sum + anonymousFeedback.get( count).getStarRating();
        }

        if ( anonymousFeedback.size() == 0) {
            averagePoint = 0;
        }
        else {
            averagePoint = (double) sum / anonymousFeedback.size();
        }

        // Save to server
        if ( isLoggedIn && isConnectedToInternet()) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Companies/" + companyId);
            reference.child( AVERAGE_POINT).setValue( String.valueOf( averagePoint));
        }
    }

    /**
     * Overridable method to be modified by sub classes to assign ids, if
     * required, before server sync
     * @param listener LoginListener interface of completeLogin method to
     *                 be called when server sync is completed
     * @author Alp Afyonluoğlu
     */
    @Override
    protected void onLoginEmailVerified( LoginListener listener) {
        createCompanyId( new IdCreateListener() {
            @Override
            public void onIdCreated( boolean isCreated) {
                if ( isCreated) {
                    Company.super.onLoginEmailVerified( new LoginListener() {
                        @Override
                        public void onLogin( boolean isLoggedIn) {
                            listener.onLogin( isLoggedIn);
                        }
                    });
                }
                else {
                    listener.onLogin( false);
                }
            }
        });
    }

    /**
     * Creates company id
     * @param listener IdCreateListener interface that is called
     *                 id is created with data retrieved from server
     * @author Alp Afyonluoğlu
     */
    private void createCompanyId( IdCreateListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;

        // Code
        if ( isNewAccount) {
            // Assign company id
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Companies/");

            // Retrieve company id with the highest value registered so far
            reference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange( DataSnapshot dataSnapshot) {
                    // Variables
                    int idVal;
                    int highestIdVal;

                    // Code
                    reference.removeEventListener( this);

                    // Get highest id value
                    highestIdVal = 1;
                    if ( dataSnapshot.exists()) {
                        for ( DataSnapshot company : dataSnapshot.getChildren()) {
                            idVal = Integer.parseInt( company.getKey());
                            if ( idVal > highestIdVal) {
                                highestIdVal = idVal;
                            }
                        }
                    }

                    // Create company id
                    companyId = String.valueOf( highestIdVal + 1);
                    while ( companyId.length() != 3) {
                        companyId = "0" + companyId;
                    }

                    listener.onIdCreated( true);
                }

                @Override
                public void onCancelled( DatabaseError error) {
                    // Database error occurred
                    reference.removeEventListener( this);

                    listener.onIdCreated( false);
                }
            });
        }
    }

    /**
     * Logs in the current user account
     * @param update whether user data should be updated with the
     *               server data or not
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server or loaded
     *                 from local storage
     * @author Alp Afyonluoğlu
     */
    @Override
    public void getCurrentUser( boolean update, ServerSyncListener listener) {
        // Variables
        final Company THIS_COMPANY = this;

        // Code
        super.getCurrentUser( update, new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                // Variables
                Set<String> trainIds;
                Set<String> employeeNames;
                Set<String> employeeLinkedIds;
                Set<String> feedbackStars;
                Set<String> feedbackComments;
                Set<String> lineDepartures;
                Set<String> lineArrivals;
                String[] trainIdsArray;
                String[] employeeNamesArray;
                String[] employeeLinkedIdsArray;
                String[] feedbackStarsArray;
                String[] feedbackCommentsArray;
                String[] lineDeparturesArray;
                String[] lineArrivalsArray;
                Train train;
                Place defaultPlace;
                Employee employee;
                boolean islinkedTrainFound;
                Ticket ticket;
                Line line;
                Place departure;
                Place arrival;

                // Code
                if ( isSynced && !update) {
                    companyId = preferences.getString( COMPANY_ID, "000");
                    balance = preferences.getInt( BALANCE, 0);

                    // Create default place
                    defaultPlace = new Place( "Unknown", 0, 0);

                    // Create trains
                    trainIds = preferences.getStringSet( TRAINS, null);
                    trains = new ArrayList<>();
                    if ( trainIds != null) {
                         trainIdsArray = trainIds.toArray( new String[trainIds.size()]);

                         for ( String trainId : trainIdsArray) {
                             train = new Train( THIS_COMPANY, defaultPlace, 0, 0, 0, 0, trainId);
                             trains.add( train);
                         }
                    }

                    // Create employees
                    employeeNames = preferences.getStringSet( EMPLOYEE_NAMES, null);
                    employeeLinkedIds = preferences.getStringSet( EMPLOYEE_LINKED_TRAIN_IDS, null);
                    employees = new ArrayList<>();
                    if ( employeeNames != null) {
                        employeeNamesArray = employeeNames.toArray( new String[employeeNames.size()]);
                        employeeLinkedIdsArray = employeeLinkedIds.toArray( new String[employeeLinkedIds.size()]);

                        for ( int count = 0; count < employeeLinkedIdsArray.length; count++) {
                            // Find linked train of the employee
                            islinkedTrainFound = false;
                            for ( Train linkedTrain : trains) {
                                if ( linkedTrain.getId().equals( employeeLinkedIdsArray[count])) {
                                    employee = new Employee( employeeNamesArray[count], linkedTrain);
                                    employees.add( employee);

                                    islinkedTrainFound = true;
                                    break;
                                }
                            }

                            if ( !islinkedTrainFound) {
                                employee = new Employee( employeeNamesArray[count], null);
                                employees.add( employee);
                            }
                        }
                    }

                    // Create anonymous feedback tickets
                    feedbackStars = preferences.getStringSet( FEEDBACK_STARS, null);
                    feedbackComments = preferences.getStringSet( FEEDBACK_COMMENTS, null);
                    anonymousFeedback = new ArrayList<>();
                    if ( feedbackStars != null) {
                        feedbackStarsArray = feedbackStars.toArray( new String[feedbackStars.size()]);
                        feedbackCommentsArray = feedbackComments.toArray( new String[feedbackComments.size()]);

                        for ( int count = 0; count < feedbackStarsArray.length; count++) {
                            ticket = new Ticket( null, null);
                            ticket.setStarRating( Integer.parseInt( feedbackStarsArray[count]));
                            ticket.setComment( feedbackCommentsArray[count]);
                            anonymousFeedback.add( ticket);
                        }
                    }
                    calculateAveragePoint();

                    // Create lines
                    lineDepartures = preferences.getStringSet( LINE_DEPARTURES, null);
                    lineArrivals = preferences.getStringSet( LINE_ARRIVALS, null);
                    lines = new ArrayList<>();
                    if ( lineDepartures != null) {
                        lineDeparturesArray = lineDepartures.toArray( new String[lineDepartures.size()]);
                        lineArrivalsArray = lineArrivals.toArray( new String[lineArrivals.size()]);

                        for ( int count = 0; count < lineDeparturesArray.length; count++) {
                            arrival = new Place( lineArrivalsArray[count], 0, 0); // TODO: Get coordinates
                            departure = new Place( lineDeparturesArray[count], 0, 0); // TODO: Get coordinates

                            line = new Line( departure, arrival);
                            lines.add( line);
                        }
                    }
                }

                listener.onSync( isSynced);
            }
        });
    }

    /**
     * Saves user data to local storage
     */
    @Override
    protected void saveToLocalStorage() {
        // Variables
        Set<String> trainIds;
        Set<String> employeeLinkIds;
        Set<String> employeeNames;
        Set<String> feedbackStars;
        Set<String> feedbackComments;
        Set<String> lineDepartures;
        Set<String> lineArrivals;

        // Code
        super.saveToLocalStorage();
        preferences.edit().putString( COMPANY_ID, companyId).apply();
        preferences.edit().putString( COMPANY_ID, companyId).apply();
        preferences.edit().putInt( BALANCE, balance).apply();

        // Save trains
        trainIds = new HashSet<String>();
        for ( int count = 0; count < trains.size(); count++) {
            trainIds.add( trains.get( count).getId());
        }
        preferences.edit().putStringSet( TRAINS, trainIds).apply();

        // Save employees
        employeeLinkIds = new HashSet<String>();
        employeeNames = new HashSet<String>();
        for ( int count = 0; count < employees.size(); count++) {
            employeeLinkIds.add( String.valueOf( employees.get( count).getAssignedTrain()));
            employeeNames.add( employees.get( count).getName());
        }
        preferences.edit().putStringSet( EMPLOYEE_LINKED_TRAIN_IDS, employeeLinkIds).apply();
        preferences.edit().putStringSet( EMPLOYEE_NAMES, employeeNames).apply();

        // Save feedback
        feedbackStars = new HashSet<String>();
        feedbackComments = new HashSet<String>();
        for ( int count = 0; count < anonymousFeedback.size(); count++) {
            feedbackStars.add( String.valueOf( anonymousFeedback.get( count).getStarRating()));
            feedbackComments.add( anonymousFeedback.get( count).getComment());
        }
        preferences.edit().putStringSet( FEEDBACK_STARS, feedbackStars).apply();
        preferences.edit().putStringSet( FEEDBACK_COMMENTS, feedbackComments).apply();

        // Save lines
        lineDepartures = new HashSet<String>();
        lineArrivals = new HashSet<String>();
        for ( int count = 0; count < lines.size(); count++) {
            lineDepartures.add( lines.get( count).getDeparture().getName());
            lineArrivals.add( lines.get( count).getArrival().getName());
        }
        preferences.edit().putStringSet( LINE_DEPARTURES, lineDepartures).apply();
        preferences.edit().putStringSet( LINE_ARRIVALS, lineArrivals).apply();
    }

    /**
     * Saves local user data to server
     * @param listener ServerSyncListener interface that is called
     *                 when data is sent to server
     * @author Alp Afyonluoğlu
     */
    @Override
    public void saveToServer( ServerSyncListener listener) {
        super.saveToServer( new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                if ( isSynced) {
                    // Variables
                    FirebaseDatabase database;
                    DatabaseReference reference;
                    HashMap<String, String> data;
                    Train train;
                    ArrayList<Schedule> schedules;
                    Schedule schedule;
                    Employee employee;
                    Line line;

                    // Code
                    database = FirebaseDatabase.getInstance();

                    // Save user data to server
                    reference = database.getReference( SERVER_KEY + "/Users/" + id);
                    reference.child( COMPANY_ID).setValue( companyId);

                    // Save company data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId);
                    reference.child( NAME).setValue( name);
                    reference.child( BALANCE).setValue( String.valueOf( balance));

                    // Save trains data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId + "/trains");
                    for ( int count = 0; count < trains.size(); count++) {
                        train = trains.get( count);

                        // Save train related general info
                        reference.child( train.id).child( BUSINESS_WAGON_NO).setValue( String.valueOf( train.businessWagonNum));
                        reference.child( train.id).child( ECONOMY_WAGON_NO).setValue( String.valueOf( train.economyWagonNum));
                        reference.child( train.id).child( BUSINESS_PRICE).setValue( String.valueOf( train.businessPrice));
                        reference.child( train.id).child( ECONOMY_PRICE).setValue( String.valueOf( train.economyPrice));
                        // TODO: Add availability

                        // Save current location of train
                        data = new HashMap<>();
                        data.put( "x", String.valueOf( train.lon));
                        data.put( "y", String.valueOf( train.lat));
                        reference.child( train.id).child( CURRENT_LOCATION).setValue( data);

                        // Save schedules and their lines
                        schedules = train.schedules;
                        for ( int scheduleCount = 0; scheduleCount < schedules.size(); scheduleCount++) {
                            schedule = schedules.get( scheduleCount);

                            reference.child( train.id).child( SCHEDULES).child( schedule.getIdRepresentation( schedule.getDepartureDate())).child( FROM).setValue( String.valueOf( schedule.getDeparturePlace().getName()));
                            reference.child( train.id).child( SCHEDULES).child( schedule.getIdRepresentation( schedule.getDepartureDate())).child( TO).setValue( String.valueOf( schedule.getArrivalPlace().getName()));
                            reference.child( train.id).child( SCHEDULES).child( schedule.getIdRepresentation( schedule.getDepartureDate())).child( ESTIMATED_ARRIVAL).setValue( schedule.getIdRepresentation( schedule.getArrivalDate()));
                        }
                    }

                    // Save employees data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId + "/employees");
                    data = new HashMap<>();
                    for ( int count = 0; count < employees.size(); count++) {
                        employee = employees.get( count);
                        data.put( employee.getAssignedTrain().getId(), employee.getName());
                    }
                    reference.setValue( data);

                    // Save average point data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId);
                    reference.child( AVERAGE_POINT).setValue( String.valueOf( averagePoint));

                    // Save lines data to server
                    reference = database.getReference( SERVER_KEY + "/Companies/" + companyId + "/lines");
                    for ( int count = 1; count <= lines.size(); count++) {
                        line = lines.get( count);

                        data = new HashMap<>();
                        data.put( ARRIVAL, line.getArrival().getName());
                        data.put( DEPARTURE, line.getDeparture().getName());
                        reference.child( String.valueOf( count)).setValue( data);
                    }

                    listener.onSync( true);
                }
                else {
                    listener.onSync( false);
                }
            }
        });
    }

    /**
     * Updates local user data with data retrieved from server
     * @param listener ServerSyncListener interface that is called
     *                 when data is retrieved from server
     * @author Alp Afyonluoğlu
     */
    @Override
    public void getFromServer( ServerSyncListener listener) {
        // Variables
        final Company THIS_COMPANY = this;

        // Code
        super.getFromServer( new ServerSyncListener() {
            @Override
            public void onSync( boolean isSynced) {
                if ( isSynced) {
                    // Variables
                    FirebaseDatabase database;
                    DatabaseReference reference;

                    // Code
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference( SERVER_KEY + "/Users/" + id);

                    // Retrieve user data from server
                    reference.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange( DataSnapshot dataSnapshot) {
                            // Variables
                            HashMap<String, String> userData;
                            DatabaseReference referenceCompany;

                            // Code
                            reference.removeEventListener( this);
                            if ( dataSnapshot.exists()) {
                                userData = (HashMap<String, String>) dataSnapshot.getValue();
                                companyId = userData.get( COMPANY_ID);

                                // Retrieve company data from server
                                referenceCompany = database.getReference( SERVER_KEY + "/Companies/" + companyId);

                                // Retrieve user data from server
                                referenceCompany.addValueEventListener( new ValueEventListener() {
                                    @Override
                                    public void onDataChange( DataSnapshot dataSnapshot) {
                                        // Variables
                                        HashMap<String, Object> companyData;
                                        Train train;
                                        String trainId;
                                        int businessWagonNo;
                                        int economyWagonNo;
                                        int businessPrice;
                                        int economyPrice;
                                        int currentX;
                                        int currentY;
                                        Place currentLocation;
                                        ArrayList<Schedule> schedules;
                                        Schedule schedule;
                                        String departureId;
                                        String arrivalId;
                                        String from;
                                        String to;
                                        Place departure;
                                        Place arrival;
                                        Line line;
                                        String employeeLinkedId;
                                        String employeeName;
                                        boolean islinkedTrainFound;
                                        Employee employee;
                                        int feedbackStar;
                                        String feedbackComment;
                                        Ticket ticket;
                                        String lineDeparture;
                                        String lineArrival;

                                        // Code
                                        referenceCompany.removeEventListener( this);
                                        if ( dataSnapshot.exists()) {
                                            companyData = (HashMap<String, Object>) dataSnapshot.getValue();
                                            balance = Integer.parseInt( (String) companyData.get( COMPANY_ID));

                                            // Create trains with server data
                                            trains = new ArrayList<>();
                                            for ( DataSnapshot trainData : dataSnapshot.child( "trains").getChildren()) {
                                                trainId = trainData.getKey();
                                                businessWagonNo = Integer.parseInt( trainData.child( trainId).child( BUSINESS_WAGON_NO).getValue( String.class));
                                                economyWagonNo = Integer.parseInt( trainData.child( trainId).child( ECONOMY_WAGON_NO).getValue( String.class));
                                                businessPrice = Integer.parseInt( trainData.child( trainId).child( BUSINESS_PRICE).getValue( String.class));
                                                economyPrice = Integer.parseInt( trainData.child( trainId).child( ECONOMY_PRICE).getValue( String.class));
                                                currentX = Integer.parseInt( trainData.child( trainId).child( CURRENT_LOCATION).child( "x").getValue( String.class));
                                                currentY = Integer.parseInt( trainData.child( trainId).child( CURRENT_LOCATION).child( "y").getValue( String.class));
                                                currentLocation = new Place( "Train " + trainId, currentY, currentX);

                                                train = new Train( THIS_COMPANY, currentLocation, businessWagonNo, economyWagonNo, businessPrice, economyPrice, trainId);
                                                schedules = new ArrayList<>();
                                                for ( DataSnapshot scheduleData : dataSnapshot.child( trainId).child( SCHEDULES).getChildren()) {
                                                    departureId = scheduleData.getKey();
                                                    arrivalId = trainData.child( ESTIMATED_ARRIVAL).getValue( String.class);
                                                    from = trainData.child( FROM).getValue( String.class);
                                                    to = trainData.child( TO).getValue( String.class);

                                                    departure = new Place( from, 0, 0); // TODO: Get coordinates from place list
                                                    arrival = new Place( to, 0, 0); // TODO: Get coordinates from place list
                                                    line = new Line( departure, arrival);

                                                    schedule = new Schedule( departureId, arrivalId, line, businessWagonNo, economyWagonNo, train);
                                                    train.addSchedule( schedule);
                                                    schedules.add( schedule);
                                                }
                                                trains.add( train);
                                            }

                                            // Create employees with server data
                                            employees = new ArrayList<>();
                                            for ( DataSnapshot employeeData : dataSnapshot.child( "employees").getChildren()) {
                                                employeeLinkedId = employeeData.getKey();
                                                employeeName = employeeData.child( employeeLinkedId).getValue( String.class);

                                                // Find linked train of the employee
                                                islinkedTrainFound = false;
                                                for ( Train linkedTrain : trains) {
                                                    if ( linkedTrain.getId().equals( employeeLinkedId)) {
                                                        employee = new Employee( employeeName, linkedTrain);
                                                        employees.add( employee);

                                                        islinkedTrainFound = true;
                                                        break;
                                                    }
                                                }

                                                if ( !islinkedTrainFound) {
                                                    employee = new Employee( employeeName, null);
                                                    employees.add( employee);
                                                }
                                            }

                                            // Create anonymous feedback tickets with server data
                                            anonymousFeedback = new ArrayList<>();
                                            for ( DataSnapshot ticketData : dataSnapshot.child( "employees").getChildren()) {
                                                feedbackStar = Integer.parseInt( ticketData.child( FEEDBACK_STARS).getValue( String.class));
                                                feedbackComment = ticketData.child( FEEDBACK_COMMENTS).getValue( String.class);

                                                ticket = new Ticket( null, null);
                                                ticket.setComment( feedbackComment);
                                                ticket.setStarRating( feedbackStar);
                                                anonymousFeedback.add( ticket);
                                            }
                                            calculateAveragePoint();

                                            // Create lines  with server data
                                            lines = new ArrayList<>();
                                            for ( DataSnapshot ticketData : dataSnapshot.child( "lines").getChildren()) {
                                                lineDeparture = ticketData.child( DEPARTURE).getValue( String.class);
                                                lineArrival = ticketData.child( ARRIVAL).getValue( String.class);

                                                arrival = new Place( lineArrival, 0, 0); // TODO: Get coordinates
                                                departure = new Place( lineDeparture, 0, 0); // TODO: Get coordinates

                                                line = new Line( departure, arrival);
                                                lines.add( line);
                                            }

                                            listener.onSync( true);
                                        }
                                    }

                                    @Override
                                    public void onCancelled( DatabaseError error) {
                                        // Database error occurred
                                        referenceCompany.removeEventListener( this);

                                        listener.onSync( false);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {
                            // Database error occurred
                            reference.removeEventListener( this);

                            listener.onSync( false);
                        }
                    });
                }
                else {
                    listener.onSync( false);
                }
            }
        });
    }

    private interface IdCreateListener {
        void onIdCreated( boolean isCreated);
    }
}
