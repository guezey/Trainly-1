package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Customer;
import com.fliers.trainly.models.Line;
import com.fliers.trainly.models.Place;
import com.fliers.trainly.models.User;

//import java.awt.Button;
//import java.awt.View;

//import javax.swing.text.View;

public class LinesActivity extends AppCompatActivity {

    private final String LOGGED_IN_USER_TYPE = "loggedInUserType";
    private final int NO_LOGIN = 0;
    private final int COMPANY_LOGIN = 1;
    private final int CUSTOMER_LOGIN = 2;

    User currentUser;
    private TextView textView20;
    private TextView textView21;
    private EditText editTextTextPersonName2;
    private EditText editTextTextPersonName3;
    private TextView x1;
    private TextView y1;
    private TextView x2;
    private TextView y2;
    private Button button;
    private SharedPreferences preferences;
    private int loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);

//        preferences = getSharedPreferences( String.valueOf( R.string.app_name), Context.MODE_PRIVATE);
//        loginType = preferences.getInt( LOGGED_IN_USER_TYPE, NO_LOGIN);
//
//        if ( loginType == COMPANY_LOGIN) {
//            currentUser = (Company) User.getCurrentUserInstance();
//        }
//        else if ( loginType == CUSTOMER_LOGIN) {
//            currentUser = (Customer) User.getCurrentUserInstance();
//        }

        textView20 = (TextView) findViewById(R.id.textView20);
        textView21 = (TextView) findViewById(R.id.textView21);
        editTextTextPersonName2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {

//                if( !(currentUser.isConnectedToInternet())) {
//
//                    Toast.makeText( getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
                //else {
                    String editTextTextPersonName2String = editTextTextPersonName2.getText().toString();
                    String editTextTextPersonName3String = editTextTextPersonName3.getText().toString();
                    if (!(editTextTextPersonName2String.trim().equalsIgnoreCase("")
                            || editTextTextPersonName3String.trim().equalsIgnoreCase(""))) {

                        Place departurePlace;
                        Place arrivalPlace;
                        Line newLine;

                        openDialog();
                    }
                    if (editTextTextPersonName2String.trim().equalsIgnoreCase("")) {

                        editTextTextPersonName2.setError("Invalid Input");
                    }
                    if (editTextTextPersonName3String.trim().equalsIgnoreCase("")) {

                        editTextTextPersonName3.setError("Invalid Input");
                    }
                }
            //}
        });
    }

    public void openDialog() {

        LinesCoordinatesActivity coordinatesActivity = new LinesCoordinatesActivity();
        coordinatesActivity.show(getSupportFragmentManager(), "Enter Coordinates");
    }
}