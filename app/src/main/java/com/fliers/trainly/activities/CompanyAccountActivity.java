package com.fliers.trainly.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fliers.trainly.R;
import com.fliers.trainly.models.Company;

/**
 * Controls Company Home page.
 * @author Ali Emir Güzey
 * @version 03.05.2021
 */
public class CompanyAccountActivity extends AppCompatActivity {

    /**
     * Manipulates view once available.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_account);

        Company c = (Company) Company.getCurrentUserInstance();
        ImageView btBack = findViewById(R.id.imageView2);
        TextView tvAccountName = findViewById(R.id.tvAccountName);
        TextView tvTrainNum = findViewById(R.id.tvTrainNum);
        TextView tvEmailValue = findViewById(R.id.tvEmailValue);
        EditText etChangeName = findViewById(R.id.etChangeName);
        Button btChangeName = findViewById(R.id.button);

        btBack.setOnClickListener(new View.OnClickListener() {
            /**
             * Returns to previous page on click.
             * @param view view
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btChangeName.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets company's name to entered value on click.
             * @param view view
             */
            @Override
            public void onClick(View view) {
                c.setName( etChangeName.getText().toString());
            }
        });

        tvEmailValue.setText( c.getEmail());
        tvTrainNum.setText( "Train number: " + String.valueOf(c.getTrains().size()));
        tvAccountName.setText( c.getName());
    }
}