package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.loadProfile();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        this.saveProfile();
    }

    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);

        String latitudeString = preferences.getString("latitudeString", "0");
        String longitudeString = preferences.getString("longitudeString", "0");
        String labelString = preferences.getString("labelString", "Label");
        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);
        TextView label = this.findViewById(R.id.label);

        latitude.setText(latitudeString);
        longitude.setText(longitudeString);
        label.setText(labelString);
    }

    public boolean saveProfile() {
        double lat = 0;
        double longt = 0;
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);
        TextView label = this.findViewById(R.id.label);

        // Check valid input longitude and latitude
        if (!(latitude.getText().toString().isEmpty()) &&
                !(longitude.getText().toString().isEmpty())) {


            // Convert latitude and longitude to double
            try {
                lat = Double.parseDouble(latitude.getText().toString());
                longt = Double.parseDouble(longitude.getText().toString());
            } catch (Exception e) {
                this.showAlert(this, "Please enter a valid number.");
                latitude.setText("0");
                longitude.setText("0");
                return false;
            }

            editor.putString("latitudeString", latitude.getText().toString());
            editor.putString("longitudeString", longitude.getText().toString());
            editor.putString("labelString", label.getText().toString());


        } else {

            // If invalid, profile won't be saved
            this.showAlert(this,
                    "Value for latitude/longitude cannot be empty!");
            latitude.setText("0");
            longitude.setText("0");
            return false;
        }

        editor.apply();
        return true;

        //editor.putString("latitudeString", latitude.getText().toString());
        //editor.putString("longitudeString", longitude.getText().toString());

    }

    public static void showAlert(Activity activity, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle("Alert!")
                .setMessage(message)
                .setPositiveButton("Ok",(dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }


    public void onLaunchSaveClicked(View view) {
        if (saveProfile()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onLaunchExitButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}