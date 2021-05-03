package com.fliers.trainly.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;

import com.fliers.trainly.models.Ticket;
import com.fliers.trainly.models.Train;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.w3c.dom.Text;
import java.util.ArrayList;

public class TrainsActivity extends AppCompatActivity {

    Company currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains);

        TextView tvTitle = findViewById(R.id.tvTitle);
        ListView lvTrains = findViewById(R.id.lvTrains);
        ImageView btBack = findViewById(R.id.imageView2);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddTrain();
            }
        });

        currentUser = (Company) Company.getCurrentUserInstance();

        if ( currentUser.getTrains().size() == 0) {
            tvTitle.setText("You have no trains!");
            tvTitle.setTextColor(Color.RED);
        }
        else {
            TrainsActivity.CustomAdaptor customAdaptor = new TrainsActivity.CustomAdaptor();
            lvTrains.setAdapter( customAdaptor);
        }
    }

    public void goToEditSchedule() {
        Intent switchActivityIntent = new Intent( this, EditScheduleActivity.class);
        startActivity( switchActivityIntent);
    }

    public void goToAddTrain() {
        Intent switchActivityIntent = new Intent( this, AddTrainActivity.class);
        startActivity( switchActivityIntent);
    }

    class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {

            return currentUser.getTrains().size();
        }

        @Override
        public Object getItem( int position) {
            return null;
        }

        @Override
        public long getItemId( int position) {
            return 0;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate( R.layout.list_item_trains, null);
            Train train = currentUser.getTrains().get(position);

            // Get view
            TextView tvTrainId = view.findViewById(R.id.tvTrainId);
            TextView tvCapacity = view.findViewById(R.id.tvCapacity);
            TextView tvStatusValue = view.findViewById(R.id.tvStatusValue);

            // Manipulate view
            tvTrainId.setText("ID: " + train.getId());
            tvCapacity.setText("Capacity: " + String.valueOf(train.getBusinessWagonNum()*30 + train.getEconomyWagonNum()*48) );

            if (train.isOnTrip()) {
                tvStatusValue.setText("On trip");
                tvStatusValue.setTextColor(Color.RED);
            }
            else {
                tvStatusValue.setText("Available");
                tvStatusValue.setTextColor(Color.GREEN);
            }

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(TrainsActivity.this);
                    builder.setTitle("Remove Train");
                    builder.setMessage("Are you sure you want to remove this train?");
                    builder.setNegativeButton("No", null);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            currentUser.removeTrain( train);
                        }
                    });
                    builder.show();
                    return true;
                }
            });

            view.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToEditSchedule();
                }
            });

            return view;
        }
    }
}