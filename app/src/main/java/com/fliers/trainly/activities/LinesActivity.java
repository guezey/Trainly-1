package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Line;
import com.fliers.trainly.models.Place;
import com.fliers.trainly.models.User;

import java.util.ArrayList;

//import java.awt.Button;
//import java.awt.View;

//import javax.swing.text.View;

public class LinesActivity extends AppCompatActivity implements LinesCoordinatesActivity.CoordinatesActivityListener{

    private final String LOGGED_IN_USER_TYPE = "loggedInUserType";
    private final int NO_LOGIN = 0;
    private final int COMPANY_LOGIN = 1;
    private final int CUSTOMER_LOGIN = 2;

    Company currentUser;
    private EditText editTextTextPersonName2;
    private EditText editTextTextPersonName3;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private Button button;
    private SharedPreferences preferences;
    private int loginType;
    private ImageView back;
    ArrayList<Line> lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);

        preferences = getSharedPreferences( String.valueOf( R.string.app_name), Context.MODE_PRIVATE);
        loginType = preferences.getInt( LOGGED_IN_USER_TYPE, NO_LOGIN);

//        if ( loginType == COMPANY_LOGIN) {
            currentUser = (Company) User.getCurrentUserInstance();
//        }
//        else if ( loginType == CUSTOMER_LOGIN) {
//            currentUser = (Customer) User.getCurrentUserInstance();
//        }


        editTextTextPersonName2 = (EditText) findViewById(R.id.editTextTextPersonName2);
        editTextTextPersonName3 = (EditText) findViewById(R.id.editTextTextPersonName3);
        button = (Button) findViewById(R.id.button);
        back = (ImageView) findViewById(R.id.imageView2);

        back.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {
                finish();
            }
        });

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
                        Line newNormalLine;
                        Line newReverseLine;

                        openDialog();
                        departurePlace = new Place( editTextTextPersonName2String, x1, y1);
                        arrivalPlace = new Place( editTextTextPersonName3String, x2, y2);
                        newNormalLine = new Line( departurePlace, arrivalPlace);
                        newReverseLine = new Line( arrivalPlace, departurePlace);

//                        ((Company)currentUser).addLine( newNormalLine);
//                        ((Company)currentUser).addLine( newReverseLine);
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

        ListView listLines = findViewById( R.id.listLines );
        lines = currentUser.getLines();

        if ( lines.size() == 0) {
            Toast.makeText( getApplicationContext(), "No lines found", Toast.LENGTH_SHORT).show();
        }
        else {
            LinesActivity.CustomAdaptor customAdaptor = new LinesActivity.CustomAdaptor();
            listLines.setAdapter( customAdaptor );
        }

    }

    public void openDialog() {

        LinesCoordinatesActivity coordinatesActivity = new LinesCoordinatesActivity();
        coordinatesActivity.show(getSupportFragmentManager(), "Enter Coordinates");
        //coordinatesActivity.departureText.setText("" + editTextTextPersonName2.toString());
        //coordinatesActivity.arrivalText.setText("" + editTextTextPersonName3.toString());
    }

    @Override
    public void applyTexts(double posX1, double posY1, double posX2, double posY2) {

        x1 = posX1;
        y1 = posY1;
        x2 = posX2;
        y2 = posY2;
    }

    class CustomAdaptor extends BaseAdapter {
        @Override public int getCount() {
            return lines.size();
        }

        @Override public Object getItem( int position) {
            return null;
        }

        @Override public long getItemId( int position) {
            return 0;
        }
        @Override public View getView( final int position, View convertView, ViewGroup parent) {
            // View view = getLayoutInflater().inflate( R.layout.list_item_lines, null);
            View view = null;

            // Get title text view
            TextView tvLineTitle = view.findViewById( R.id.tvLine );
            tvLineTitle.setText( currentUser.getLines().get(position) + "" );

            return view;
        }
    }
}