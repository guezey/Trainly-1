package com.fliers.trainly.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.fliers.trainly.R;

import static com.fliers.trainly.R.layout.activity_lines_coordinates;

public class LinesCoordinatesActivity extends AppCompatDialogFragment {

    private TextView departureText;
    private EditText posX1;
    private EditText posY1;
    private TextView arrivalText;
    private EditText posX2;
    private EditText posY2;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_lines_coordinates, null);

        builder.setView(view);
        builder.setTitle("Enter Coordinates");
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        departureText = view.findViewById(R.id.departure_text);
        posX1 = view.findViewById(R.id.posX1);
        posY1 = view.findViewById(R.id.posY1);
        arrivalText = view.findViewById(R.id.arrival_text);
        posX2 = view.findViewById(R.id.posX2);
        posY2 = view.findViewById(R.id.posY2);

        return builder.create();
    }
}
