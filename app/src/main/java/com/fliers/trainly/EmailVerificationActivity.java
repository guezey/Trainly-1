package com.fliers.trainly;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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