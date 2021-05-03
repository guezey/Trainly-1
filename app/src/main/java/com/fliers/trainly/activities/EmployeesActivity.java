package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fliers.trainly.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmployeesActivity extends AppCompatActivity {

    private ImageView back;
    private FloatingActionButton addEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        back = (ImageView) findViewById(R.id.imageView2);
        addEmployee = (FloatingActionButton) findViewById(R.id.fabAdd);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getApplicationContext(), AddEmployeeActivity.class);
                startActivity( intent);
            }
        });
    }
}