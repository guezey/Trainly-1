package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.fliers.trainly.R;

public class EmailVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_email_verification);

        // Variables
        Intent intent;
        String emailLink;

        // Code
        intent = getIntent();
        emailLink = intent.getData().toString();

        // TODO: user.completeLogin( emailLink, new User.LoginListener() { ... });
    }
}