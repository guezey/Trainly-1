package com.fliers.trainly.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Place;
import com.fliers.trainly.models.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.Calendar;

public class CustomerHomeActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private static final LatLng NORTHEASTERN_BOUND = new LatLng( 42.632028, 45.675553);
    private static final LatLng SOUTHWESTERN_BOUND = new LatLng( 34.879173, 24.824881);

    private GoogleMap mMap;
    private Spinner departureSpinner;
    private Spinner arrivalSpinner;
    private Marker prevDepMarker;
    private Marker prevArrMarker;
    private int mYear, mMonth, mDay;
    private TextView txtDate, txtAdvanced;
    private ImageView menuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        departureSpinner = (Spinner)findViewById(R.id.departureSpinner);
        arrivalSpinner = (Spinner)findViewById(R.id.arrivalSpinner);
        departureSpinner.setOnItemSelectedListener(this);
        arrivalSpinner.setOnItemSelectedListener(this);
        Button search = findViewById(R.id.buttonSearch);
        menuButton = findViewById(R.id.imageView2);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtDate = (TextView) findViewById(R.id.textView28);
        txtAdvanced = (TextView) findViewById(R.id.textView29);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivitiesWithData();
            }
        });
        txtAdvanced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switchToAdvanced();
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            // Get Current Date
            @Override
            public void onClick( View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CustomerHomeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Istanbul, Turkey.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setLatLngBoundsForCameraTarget( new LatLngBounds( SOUTHWESTERN_BOUND, NORTHEASTERN_BOUND));
        mMap.moveCamera(CameraUpdateFactory.newLatLng( new LatLng(39.93677, 32.84647)));
        mMap.setMinZoomPreference( 4);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if ( parent == departureSpinner) {
            if (parent.getItemAtPosition(pos).toString().equals("Istanbul")) {
                if ( prevDepMarker != null)
                    prevDepMarker.remove();
                String text = parent.getItemAtPosition(pos).toString();
                // Add a marker in Istanbul and move the camera
                LatLng istanbul = new LatLng(41.02935, 28.99392 );
                prevDepMarker = mMap.addMarker(new MarkerOptions().position(istanbul).title("Departure: " + text));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(istanbul));
            }
            else if (parent.getItemAtPosition(pos).toString().equals("Ankara")) {
                if ( prevDepMarker != null)
                    prevDepMarker.remove();
                String text = parent.getItemAtPosition(pos).toString();
                // Add a marker in Istanbul and move the camera
                LatLng ankara = new LatLng(39.93677, 32.84647 );
                prevDepMarker = mMap.addMarker(new MarkerOptions().position(ankara).title("Departure: " + text));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(ankara));
            }
            else if (parent.getItemAtPosition(pos).toString().equals("Izmir")) {
                if ( prevDepMarker != null)
                    prevDepMarker.remove();
                String text = parent.getItemAtPosition(pos).toString();
                // Add a marker in Istanbul and move the camera
                LatLng izmir = new LatLng(38.43506, 27.14353 );
                prevDepMarker = mMap.addMarker(new MarkerOptions().position(izmir).title("Departure: " + text));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(izmir));
            }
//            Places places = new Places();
//            places.getFromServer();
//            if ( prevDepMarker != null)
//                prevDepMarker.remove();
//            Place place = places.findByName( parent.getItemAtPosition(pos).toString());
//            // Add a marker and move the camera
//            LatLng location = new LatLng( place.getLatitude(), place.getLongitude() );
//            prevDepMarker = mMap.addMarker(
//                    new MarkerOptions().position(location).title("Departure: " + parent.getItemAtPosition(pos).toString()));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
        }
        else if ( parent == arrivalSpinner) {
            if (parent.getItemAtPosition(pos).toString().equals("Istanbul")) {
                if ( prevArrMarker != null )
                    prevArrMarker.remove();
                String text = parent.getItemAtPosition(pos).toString();
                // Add a marker in Istanbul and move the camera
                LatLng istanbul = new LatLng(41.02935, 28.99392 );
                prevArrMarker = mMap.addMarker(new MarkerOptions().position(istanbul).title("Arrival: " + text));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(istanbul));
            }
            else if (parent.getItemAtPosition(pos).toString().equals("Ankara")) {
                if ( prevArrMarker != null )
                    prevArrMarker.remove();
                String text = parent.getItemAtPosition(pos).toString();
                // Add a marker in Istanbul and move the camera
                LatLng ankara = new LatLng(39.93677, 32.84647 );
                prevArrMarker = mMap.addMarker(new MarkerOptions().position(ankara).title("Arrival: " + text));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(ankara));
            }
            else if (parent.getItemAtPosition(pos).toString().equals("Izmir")) {
                if (prevArrMarker != null)
                    prevArrMarker.remove();
                String text = parent.getItemAtPosition(pos).toString();
                // Add a marker in Istanbul and move the camera
                LatLng izmir = new LatLng(38.43506, 27.14353);
                prevArrMarker = mMap.addMarker(new MarkerOptions().position(izmir).title("Arrival: " + text));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(izmir));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void switchActivitiesWithData() {
        Intent switchActivityIntent = new Intent(this, TicketsActivity.class);
        switchActivityIntent.putExtra("message", "Tickets for trips from " + departureSpinner.getSelectedItem().toString() +
                " to " + arrivalSpinner.getSelectedItem().toString() + " having departure dates of " + txtDate.getText());
        startActivity(switchActivityIntent);
    }

//    private void switchToAdvanced() {
//        Intent switchActivityIntent = new Intent(this, AdvancedSearchActivity.class);
//        startActivity(switchActivityIntent);
//    }
}