package com.fliers.trainly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Customer;
import com.fliers.trainly.models.User;
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
//        Customer customer = new Customer( getApplicationContext());
//        customer.completeLogin( emailLink, new User.LoginListener() {
//            @Override
//            public void onLogin( boolean isLoggedIn) {
//                if ( isLoggedIn) {
//                    Log.d( "TRAINLY_APP", "Logged in");
//
//                    customer.setName( "Alp Afyonluoglu");
//                    customer.setDiscountPoints( 20);
//                    customer.saveToServer( new User.ServerSyncListener() {
//                        @Override
//                        public void onSync( boolean isSynced) {
//                            Log.d( "TRAINLY_APP", "isSynced: " + isSynced);
//                        }
//                    });
//                }
//                else {
//                    Log.d( "TRAINLY_APP", "Login failed");
//                }
//            }
//        });
    }
}