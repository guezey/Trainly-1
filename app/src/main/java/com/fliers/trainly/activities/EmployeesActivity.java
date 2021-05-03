package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;
import com.fliers.trainly.models.Employee;
import com.fliers.trainly.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class EmployeesActivity extends AppCompatActivity {

    private ImageView back;
    private FloatingActionButton addEmployee;
    ArrayList<Employee> employees;
    Company currentUser;

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

        currentUser = (Company) User.getCurrentUserInstance();
        ListView listEmployees = findViewById( R.id.listEmployees );
        employees = currentUser.getEmployees();

        if ( employees.size() == 0) {
            Toast.makeText( getApplicationContext(), "No employees found", Toast.LENGTH_SHORT).show();
        }
        else {
            EmployeesActivity.CustomAdaptor customAdaptor = new EmployeesActivity.CustomAdaptor();
            listEmployees.setAdapter( customAdaptor );
        }
    }

    class CustomAdaptor extends BaseAdapter {
        @Override public int getCount() {
            return employees.size();
        }

        @Override public Object getItem( int position) {
            return null;
        }

        @Override public long getItemId( int position) {
            return 0;
        }
        @Override public View getView( final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate( R.layout.list_item_employees, null);
            
            // Get title text view
            TextView tvEmployeeName = view.findViewById( R.id.tvEmployeeName );
            tvEmployeeName.setText( "Name: " + employees.get( position).getName() );

            TextView tvEmployeeLinkedTrain = view.findViewById( R.id.tvEmployeeLinkedTrain );
            tvEmployeeLinkedTrain.setText( "Linked train: " + employees.get( position ).getAssignedTrain().getId() );

            TextView tvEmployeeStatus = view.findViewById( R.id.tvEmployeeStatus );
            if ( employees.get( position ).getAssignedTrain().isOnTrip() ) {
                tvEmployeeStatus.setText( "On duty" );
                tvEmployeeStatus.setTextColor( Color.RED );
            } else {
                tvEmployeeStatus.setText( "Available" );
                tvEmployeeStatus.setTextColor( Color.GREEN );
            }

            return view;
        }
    }◘
}