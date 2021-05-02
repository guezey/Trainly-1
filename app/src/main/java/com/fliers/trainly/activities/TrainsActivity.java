package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Train;
import com.fliers.trainly.models.User;

import java.util.ArrayList;

public class TrainsActivity extends AppCompatActivity {

    Company currentUser;
    ArrayList<Train> trains;
    ArrayList<TextView> trainViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains);

        currentUser = (Company) User.getCurrentUserInstance();
        trains = new ArrayList<>(((Company)currentUser).getTrains());
        trainViews = new ArrayList<>(trains.size());
        for( int i = 0; i < trainViews.size(); i++) {
            trainViews.set(i, trains.get(i));
        }
    }
}