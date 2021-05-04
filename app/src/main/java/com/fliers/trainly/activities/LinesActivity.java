package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.fliers.trainly.models.Places;
import com.fliers.trainly.models.User;

import java.util.ArrayList;

//import java.awt.Button;
//import java.awt.View;

//import javax.swing.text.View;

/**
 * Controls Lines page.
 * @author Ali Emir Güzey
 * @author Erkin Aydın
 * @version 04.05.2021
 */
public class LinesActivity extends AppCompatActivity {

//    private final String LOGGED_IN_USER_TYPE = "loggedInUserType";
//    private final int NO_LOGIN = 0;
//    private final int COMPANY_LOGIN = 1;
//    private final int CUSTOMER_LOGIN = 2;

    Company currentUser;
    Places placeManager;
    private EditText etPlace1;
    private EditText etPlace2;
    private EditText etLatitude1;
    private EditText etLongitude1;
    private EditText etLatitude2;
    private EditText etLongitude2;
    private double lat1;
    private double lon1;
    private double lat2;
    private double lon2;
    private Button button;
//    private SharedPreferences preferences;
//    private int loginType;
    private ImageView back;
    ArrayList<Line> lines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lines);

//        preferences = getSharedPreferences( String.valueOf( R.string.app_name), Context.MODE_PRIVATE);
//        loginType = preferences.getInt( LOGGED_IN_USER_TYPE, NO_LOGIN);


        currentUser = (Company) User.getCurrentUserInstance();
        placeManager = Places.getInstance();
        etPlace1 = (EditText) findViewById(R.id.etPlace1);
        etPlace2 = (EditText) findViewById(R.id.etPlace2);
        etLatitude1 = findViewById(R.id.etLatitude1);
        etLongitude1 = findViewById(R.id.etLongitude1);
        etLatitude2 = findViewById(R.id.etLatitude2);
        etLongitude2 = findViewById(R.id.etLongitude2);
        button = (Button) findViewById(R.id.button);
        back = (ImageView) findViewById(R.id.imageView2);

        back.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {
                finish();
            }
        });

        button.setOnClickListener( new View.OnClickListener() {

            public void onClick(View view) {

                if( !(currentUser.isConnectedToInternet())) {

                    Toast.makeText( getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (!(etPlace1.getText().toString().equals("")
                            || etPlace2.getText().toString().equals("")
                            || etLatitude1.getText().toString().equals("")
                            || etLongitude1.getText().toString().equals("")
                            || etLatitude2.getText().toString().equals("")
                            || etLongitude2.getText().toString().equals(""))) {

                        Place departurePlace;
                        Place arrivalPlace;
                        Line newNormalLine;
                        Line newReverseLine;

                        lat1 = Double.parseDouble(etLatitude1.getText().toString());
                        lon1 = Double.parseDouble(etLongitude1.getText().toString());
                        lat2 = Double.parseDouble(etLatitude2.getText().toString());
                        lon2 = Double.parseDouble(etLongitude2.getText().toString());

                        if ( etLatitude1.isEnabled())
                            departurePlace = new Place(etPlace1.getText().toString(), lat1, lon1);
                        else
                            departurePlace = placeManager.findByName(etPlace1.getText().toString());

                        if ( etLatitude2.isEnabled())
                            arrivalPlace = new Place(etPlace2.getText().toString(), lat2, lon2);
                        else
                            arrivalPlace = placeManager.findByName(etPlace2.getText().toString());

                        newNormalLine = new Line( departurePlace, arrivalPlace);
                        newReverseLine = new Line( arrivalPlace, departurePlace);

                        currentUser.addLine( newNormalLine);
                        currentUser.addLine( newReverseLine);
                        currentUser.saveToServer( new User.ServerSyncListener() {
                            @Override
                            public void onSync(boolean isSynced) {
                                if (isSynced)
                                    Toast.makeText( getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText( getApplicationContext(), "Couldn't save. Try again", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else {
                        Toast.makeText( getApplicationContext(), "Please check your inputs", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        etPlace1.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after) {
                // Empty method
            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count) {
                if ( placeManager.findByName(etPlace1.getText().toString()) != null) {
                    etLatitude1.setEnabled(false);
                    etLatitude1.setText(String.valueOf(placeManager.findByName(etPlace1.getText().toString()).getLatitude()));
                    etLongitude1.setEnabled(false);
                    etLongitude1.setText(String.valueOf(placeManager.findByName(etPlace1.getText().toString()).getLongitude()));
                }
            }

            @Override
            public void afterTextChanged( Editable s) {
                // Empty method
            }
        });

        etPlace2.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after) {
                // Empty method
            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count) {
                if ( placeManager.findByName(etPlace2.getText().toString()) != null) {
                    etLatitude2.setEnabled(false);
                    etLatitude2.setText(String.valueOf(placeManager.findByName(etPlace2.getText().toString()).getLatitude()));
                    etLongitude2.setEnabled(false);
                    etLongitude2.setText(String.valueOf(placeManager.findByName(etPlace2.getText().toString()).getLongitude()));
                }
            }

            @Override
            public void afterTextChanged( Editable s) {
                // Empty method
            }
        });

        ListView listLines = findViewById( R.id.listLines );
        lines = currentUser.getLines();

        if ( lines.size() == 0 ) {
            Toast.makeText( getApplicationContext(), "No lines found", Toast.LENGTH_SHORT).show();
        }
        else {
            LinesActivity.CustomAdaptor customAdaptor = new LinesActivity.CustomAdaptor();
            listLines.setAdapter( customAdaptor );
        }

    }

//    public void openDialog() {
//
//        LinesCoordinatesActivity coordinatesActivity = new LinesCoordinatesActivity();
//        coordinatesActivity.show(getSupportFragmentManager(), "Enter Coordinates");
//        //coordinatesActivity.departureText.setText("" + editTextTextPersonName2.toString());
//        //coordinatesActivity.arrivalText.setText("" + editTextTextPersonName3.toString());
//    }

//    @Override
//    public void applyTexts(double posX1, double posY1, double posX2, double posY2) {
//
//        x1 = posX1;
//        y1 = posY1;
//        x2 = posX2;
//        y2 = posY2;
//    }

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
            View view = getLayoutInflater().inflate( R.layout.list_item_lines, null);

            // Get title text view
            TextView tvLineTitle = view.findViewById( R.id.tvLine );
            tvLineTitle.setText( currentUser.getLines().get(position).toString() );

            return view;
        }
    }
}