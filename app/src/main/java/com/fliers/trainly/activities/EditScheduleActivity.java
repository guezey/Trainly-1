package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.view.View;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Train;
import com.fliers.trainly.models.Schedule;
import com.fliers.trainly.models.Line;

import java.util.Calendar;
import java.util.ArrayList;

/**
 * Activity where companies can edit schedules of their trains
 * @author Cengizhan Terzioğlu
 * @version 03.05.2021
 */
public class EditScheduleActivity extends AppCompatActivity {

    TextView idText;
    Spinner lineSpinner;
    int dMinute, dHour, dDay, dMonth, dYear;
    int aMinute, aHour, aDay, aMonth, aYear;
    TextView dTimeText, dDateText;
    TextView aTimeText, aDateText;
    ImageView backButton;
    Train currentTrain;
    ArrayList<Schedule> schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        idText = findViewById( R.id.tvIdSchedule);

        // Get train from previous activity
        Intent intent = getIntent();
        currentTrain = ( Train ) intent.getExtras().get( "train" );
        idText.setText( "Edit schedule information related to the train with the Id number " + currentTrain.getId() );
        lineSpinner = ( Spinner ) findViewById( R.id.spinLine);
        lineSpinner.setOnItemSelectedListener( (AdapterView.OnItemSelectedListener) this);
        Button save = findViewById( R.id.btSaveSchedule);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                // If there are areas unfilled send an error message
                if ( dDateText.toString().equals( "Add date" ) || dTimeText.toString().equals( "Add time" ) ||
                        aDateText.toString().equals( "Add date" ) || aTimeText.toString().equals( "Add time" ) ) {
                    Toast.makeText( getApplicationContext(), "There are areas unfilled!", Toast.LENGTH_SHORT).show();
                }
                else {
                    // If arrival is earlier than departure send an error message
                    String sDDate = dDateText.toString();
                    String sDTime = dTimeText.toString();
                    Calendar departure = null;
                    departure.set( Integer.parseInt( sDDate.substring( 6, 10 ) ), Integer.parseInt( sDDate.substring( 3, 5 ) ),
                            Integer.parseInt( sDDate.substring( 0, 2 ) ), Integer.parseInt( sDTime.substring( 0, 2 ) ),
                            Integer.parseInt( sDTime.substring( 3, 5 ) ) );

                    String sADate = aDateText.toString();
                    String sATime = aTimeText.toString();
                    Calendar arrival = null;
                    arrival.set( Integer.parseInt( sADate.substring( 6, 10 ) ), Integer.parseInt( sADate.substring( 3, 5 ) ),
                            Integer.parseInt( sADate.substring( 0, 2 ) ), Integer.parseInt( sATime.substring( 0, 2 ) ),
                            Integer.parseInt( sATime.substring( 3, 5 ) ) );

                    if ( departure.compareTo( arrival ) > 0 ) {
                        Toast.makeText( getApplicationContext(), "Departure should be earlier than arrival!", Toast.LENGTH_SHORT).show();
                    }
                    // If there is no problem add the new schedule to the train
                    else {
                        Schedule newSchedule = new Schedule( departure, arrival, ( Line ) lineSpinner.getSelectedItem(),
                                currentTrain.getBusinessWagonNum(), currentTrain.getEconomyWagonNum(), currentTrain );
                        currentTrain.addSchedule( newSchedule );
                    }
                }


                // List all the schedules the train has
                ListView listSchedules = findViewById( R.id.listSchedules );
                schedules = currentTrain.getSchedules();

                if ( schedules.size() == 0) {
                    idText.setText("No schedules were found for the train with the Id number " + currentTrain.getId() );
                    idText.setTextColor(Color.RED);
                }
                else {
                    CustomAdaptor customAdaptor = new CustomAdaptor();
                    listSchedules.setAdapter( customAdaptor );
                }

            }
        });

        // When clicked return to trains activity
        backButton = ( ImageView ) findViewById( R.id.btBackSchedule);
        backButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                Intent intent = new Intent( getApplicationContext(), TrainsActivity.class);
                startActivity( intent);
            }
        });

        // Date and Time picker dialogs
        dTimeText = ( TextView ) findViewById( R.id.tvDepartureTime );
        dDateText  = ( TextView ) findViewById( R.id.tvDepartureDate );
        aTimeText = ( TextView ) findViewById( R.id.tvArrivalTime );
        aDateText = ( TextView ) findViewById( R.id.tvArrivalDate );

        dDateText.setOnClickListener(new View.OnClickListener() {
            // Get Current Date
            @Override
            public void onClick( View view) {
                final Calendar c = Calendar.getInstance();
                dYear = c.get(Calendar.YEAR);
                dMonth = c.get(Calendar.MONTH);
                dDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet( DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth ) {

                                dDateText.setText( dayOfMonth + "/" + (monthOfYear + 1) + "/" + year );

                            }
                        }, dYear, dMonth, dDay);
                datePickerDialog.show();
            }
        });

        dTimeText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                final Calendar c = Calendar.getInstance();
                dHour = c.get( Calendar.HOUR_OF_DAY );
                dMinute = c.get( Calendar.MINUTE );

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                dTimeText.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, dHour, dMinute, true);//Yes 24 hour time
                timePickerDialog.show();
            }
        });

        aDateText.setOnClickListener(new View.OnClickListener() {
            // Get Current Date
            @Override
            public void onClick( View view) {
                final Calendar c = Calendar.getInstance();
                aYear = c.get(Calendar.YEAR);
                aMonth = c.get(Calendar.MONTH);
                aDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditScheduleActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet( DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth ) {

                                aDateText.setText( dayOfMonth + "/" + (monthOfYear + 1) + "/" + year );

                            }
                        }, aYear, aMonth, aDay);
                datePickerDialog.show();
            }
        });

        aTimeText.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                final Calendar c = Calendar.getInstance();
                aHour = c.get( Calendar.HOUR_OF_DAY );
                aMinute = c.get( Calendar.MINUTE );

                TimePickerDialog timePickerDialog = new TimePickerDialog(EditScheduleActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                aTimeText.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, aHour, aMinute, true);//Yes 24 hour time
                timePickerDialog.show();
            }
        });

        // List all the schedules the train has
        ListView listSchedules = findViewById( R.id.listSchedules );
        schedules = currentTrain.getSchedule();

        if ( schedules.size() == 0) {
            idText.setText("No schedules were found for the train with the Id number " + currentTrain.getId() );
            idText.setTextColor(Color.RED);
        }
        else {
            CustomAdaptor customAdaptor = new CustomAdaptor();
            listSchedules.setAdapter( customAdaptor );
        }

    }

    // Arrange the schedule cards
    class CustomAdaptor extends BaseAdapter {
        @Override public int getCount() {
            return currentTrain.getSchedules().size();
        }

        @Override public Object getItem( int position) {
            return null;
        }

        @Override public long getItemId( int position) {
            return 0;
        }
        @Override public View getView( final int position, View convertView, ViewGroup parent ) {
            View view = getLayoutInflater().inflate( R.layout.list_item_schedules, null );
            Schedule schedule = schedules.get( position );

            // Get title text view
            TextView tvScheduleTitle = view.findViewById( R.id.tvScheduleTitle );

            Calendar dDate = schedule.getDepartureDate();
            Calendar aDate = schedule.getArrivalDate();

            TextView dDateSchedule = findViewById( R.id.tvDepartureDateSchedule );
            dDateSchedule.setText( dDate.get( Calendar.DAY_OF_MONTH ) +
                    "/" + dDate.get( Calendar.MONTH ) + "/" + dDate.get( Calendar.YEAR ) );

            TextView dTimeSchedule = findViewById( R.id.tvDepartureTimeSchedule );
            dTimeSchedule.setText( dDate.get( Calendar.HOUR_OF_DAY ) + ":" + dDate.get( Calendar.MINUTE ) );

            TextView aDateSchedule = findViewById( R.id.tvArrivalDateSchedule );
            aDateSchedule.setText( aDate.get( Calendar.DAY_OF_MONTH ) +
                    "/" + aDate.get( Calendar.MONTH ) + "/" + aDate.get( Calendar.YEAR ) );

            TextView aTimeSchedule = findViewById( R.id.tvArrivalTimeSchedule );
            aTimeSchedule.setText( aDate.get( Calendar.HOUR_OF_DAY ) + ":" + aDate.get( Calendar.MINUTE ));

            TextView lineSchedule = findViewById( R.id.tvLineSchedule );
            lineSchedule.setText( schedule.getLine() + "" );

            return view;
        }
}

