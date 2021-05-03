package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.fliers.trainly.R;

/**
 * Activity class verifying email link and logging user in
 * @version 03.05.2021
 * @author Alp Afyonluoğlu
 */
public class EmailVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState);
        setContentView( R.layout.activity_email_verification);

        // Variables
        String emailLink;

        // Code
        emailLink = getIntent().getData().toString();

        // TODO: user.completeLogin( emailLink, new User.LoginListener() { ... });
    }
}