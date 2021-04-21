package com.fliers.trainly;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Abstract User class to be extended by Customer and Company classes
 * @author Alp Afyonluoğlu
 * @version 22.04.2021
 */
abstract class User {
    // Properties
    protected static final String SERVER_KEY = "KEY_Tr21iwuS3obrslfL4";
    private static final int EMPTY_USER = -1;
    private static final int LOGGED_IN = 0;
    private static final int AUTH_FAILED = 1;
    private static final int USER_ALREADY_EXISTS = 2;

    private String username;
    private String name;
    private String email;
    private String password;
    private int status;

    // Constructors
    /**
     * Initializes a new user
     */
    public User() {
        status = EMPTY_USER;
    }

    // Methods
    /**
     * Creates a new user account and saves to server
     * @param username username with digits and lower-case letters
     * @param password user password
     * @param name name and surname
     * @param email email address
     * @param listener LoginAndRegisterListener interface that is called
     *                when data is retrieved from server
     */
    public void createNewUser( String username, String password, String name, String email, LoginAndRegisterListener listener) {
        if ( status != LOGGED_IN) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.email = email;

            checkUsername( username, new UsernameCheckListener() {
                @Override
                public void onUsernameCheck( String username, boolean isAvailable) {
                    if ( isAvailable) {
                        status = LOGGED_IN;
                        saveToServer();
                    }
                    else {
                        status = USER_ALREADY_EXISTS;
                    }

                    listener.onLoginOrRegister();
                }
            });
        }
    }

    /**
     * Logs-in to an existing user account and retrieves user data from server
     * @param username username with digits and lower-case letters
     * @param password user password
     * @param listener LoginAndRegisterListener interface that is called
     *                when data is retrieved from server
     */
    public void login( String username, String password, LoginAndRegisterListener listener) {
        if ( status != LOGGED_IN) {
            this.username = username;
            this.password = password;

            checkCredentials( username, password, new CredentialCheckListener() {
                @Override
                public void onCredentialCheck( boolean isValid) {
                    if ( isValid) {
                        status = LOGGED_IN;
                        getFromServer( new ServerSyncListener() {
                            @Override
                            public void onSync() {
                                listener.onLoginOrRegister();
                            }
                        });
                    }
                    else {
                        status = AUTH_FAILED;
                        listener.onLoginOrRegister();
                    }
                }
            });
        }
    }

    /**
     * Checks whether username is in use or not
     * @param username username to check
     * @param listener UsernameCheckListener interface that is called
     *                when data is retrieved from server
     */
    public void checkUsername( String username, UsernameCheckListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;

        // Code
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( SERVER_KEY + "/Users/" + username);

        // Retrieve data of given username from server
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                reference.removeEventListener( this);

                // Check if username entry exists on server or not
                if ( dataSnapshot.exists()) {
                    listener.onUsernameCheck( username, false);
                }
                else {
                    listener.onUsernameCheck( username, true);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Database error occurred
                reference.removeEventListener( this);

                listener.onUsernameCheck( username, false);
            }
        });
    }

    /**
     * Checks whether username and password matches with the credentials saved on server side
     * @param username username to check
     * @param password user password to check
     * @param listener CredentialCheckListener interface that is called
     *                when data is retrieved from server
     */
    public void checkCredentials( String username, String password, CredentialCheckListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;

        // Code
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( SERVER_KEY + "/Users/" + username + "/password");

        // Retrieve password data of given username from server
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // Variables
                String value;

                // Code
                reference.removeEventListener( this);

                // Check if username entry exists on server or not
                if ( dataSnapshot.exists()) {
                    value = dataSnapshot.getValue( String.class);

                    // Check whether passwords match or not
                    if ( value.equals( password)) {
                        listener.onCredentialCheck( true);
                    }
                    else {
                        listener.onCredentialCheck( false);
                    }
                }
                else {
                    listener.onCredentialCheck( false);
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Database error occurred
                reference.removeEventListener( this);

                listener.onCredentialCheck( false);
            }
        });
    }

    /**
     * Saves local user data to server
     */
    protected void saveToServer() {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;
        HashMap<String, String> userData;

        // Code
        if ( status == LOGGED_IN) {
            database = FirebaseDatabase.getInstance();
            reference = database.getReference( SERVER_KEY + "/Users/" + username);

            // Create hash map with given user data
            userData = new HashMap<>();
            userData.put( "password", password);
            userData.put( "name", name);
            userData.put( "email", email);

            // Save map to server
            reference.setValue( userData);
        }
    }

    /**
     * Updates local user data with data retrieved from server
     * @param listener ServerSyncListener interface that is called
     *                when data is retrieved from server
     */
    protected void getFromServer( ServerSyncListener listener) {
        // Variables
        FirebaseDatabase database;
        DatabaseReference reference;

        // Code
        database = FirebaseDatabase.getInstance();
        reference = database.getReference( SERVER_KEY + "/Users/" + username);

        // Retrieve password data of given username from server
        reference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                // Variables
                HashMap<String, String> userData;

                // Code
                reference.removeEventListener( this);

                // Check if username entry exists on server or not
                if ( dataSnapshot.exists()) {
                    userData = (HashMap<String, String>) dataSnapshot.getValue();


                    // Check whether passwords match or not
                    if ( userData.get( "password").equals( password)) {
                        name = userData.get( "name");
                        email = userData.get( "email");

                        listener.onSync();
                    }
                    else {
                        status = AUTH_FAILED;
                        listener.onSync();
                    }
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                // Database error occurred
                reference.removeEventListener( this);

                listener.onSync();
            }
        });
    }

    public boolean checkEmailValidity( String email) {
        // Variables
        String[] temp;
        String username;
        String domainName;
        String domain;

        // Code
        // Split email address into its parts
        if ( email.contains( "@")) {
            temp = email.split( "@");
            username = temp[0];

            if ( temp[1].contains( ".")) {
                temp = temp[1].split( ".");
                domainName = temp[0];
                domain = temp[1];

                return 6 <= username.length() && username.length() <= 30 && 2 <= domainName.length() && 2 <= domain.length();
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Getter method for username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter method for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method for email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method for status
     * @return status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for password
     * @param password new password
     */
    public void setPassword( String password) {
        this.password = password;
    }

    /**
     * Setter method for name
     * @param name new name
     */
    public void setName( String name) {
        this.name = name;
    }

    /**
     * Setter method for email
     * @param email new email
     */
    public boolean setEmail( String email) {
        if ( checkEmailValidity( email)) {
            this.email = email;
            return true;
        }
        else {
            return false;
        }
    }

    public interface UsernameCheckListener {
        void onUsernameCheck( String username, boolean isAvailable);
    }

    public interface CredentialCheckListener {
        void onCredentialCheck( boolean isValid);
    }

    public interface LoginAndRegisterListener {
        void onLoginOrRegister();
    }

    private interface ServerSyncListener {
        void onSync();
    }
}
