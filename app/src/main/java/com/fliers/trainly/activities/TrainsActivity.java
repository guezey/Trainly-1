package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Train;
import com.fliers.trainly.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TrainsActivity extends AppCompatActivity {

    Company currentUser;
    FloatingActionButton addTrainButton;
    ImageView back;
    ArrayList<Train> trains;

    //ArrayList<Train> trains;
    //ArrayList<CardView> trainViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trains);

        currentUser = (Company) User.getCurrentUserInstance();
        addTrainButton = (FloatingActionButton) findViewById(R.id.fabAdd);
        back = (ImageView) findViewById(R.id.imageView2);

//        train1 = (CardView) findViewById(R.id.card12);
//        train2 = (CardView) findViewById(R.id.card2);
//        trains = new ArrayList<>(((Company)currentUser).getTrains());
//        trainViews = new ArrayList<>(trains.size());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addTrainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  Intent intent = new Intent( getApplicationContext(), AddTrainActivity.class);
                  startActivity( intent);
            }
        });

//        train1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //
//            }
//        });
//
//        train2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //
//            }
//        });
    }
}