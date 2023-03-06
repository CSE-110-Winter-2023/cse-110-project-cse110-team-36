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

import java.util.UUID;

/*
 * Input page. User inputs coordinates and label to appear on mainActivity
 * (also can set a mock orientation for testing purposes)
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.loadProfile();

        //시작
        // Retrieve UUID from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        String uuid = prefs.getString("MY_UUID", null);

        // Set the UUID in a TextView
        TextView textView = findViewById(R.id.uid);
        textView.setText(uuid);
        //끝

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
     * Load previously entered inputs, or defaults on first load.
     */
    public void loadProfile() {
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);

        //load from preferences
        String latitudeString = preferences.getString("latitudeString", "0");
        String longitudeString = preferences.getString("longitudeString", "0");
        String labelString = preferences.getString("labelString", "");
        String orientationString = preferences.getString("mockOrientation", "");

        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);
        TextView label = this.findViewById(R.id.label);
        TextView mockOrientation = this.findViewById(R.id.editMockOrientation);

        //set values into textViews
        latitude.setText(latitudeString);
        longitude.setText(longitudeString);
        label.setText(labelString);
        mockOrientation.setText(orientationString);
    }

    /*
     * Save entered inputs into sharedPreferences
     */
    public boolean saveProfile() {
        double lat = 0;
        double longt = 0;

        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);
        TextView label = this.findViewById(R.id.label);
        TextView mockOrientation = this.findViewById(R.id.editMockOrientation);

        // Check valid input longitude and latitude
        if (!(latitude.getText().toString().isEmpty()) &&
                !(longitude.getText().toString().isEmpty())) {

            // Convert latitude and longitude to double
            try {
                lat = Double.parseDouble(latitude.getText().toString());
                longt = Double.parseDouble(longitude.getText().toString());
            } catch (Exception e) {
                //error if not a valid number
                this.showAlert(this, "Please enter a valid number.");
                latitude.setText("0");
                longitude.setText("0");
                return false;
            }

            //save values to preferences
            editor.putString("latitudeString", latitude.getText().toString());
            editor.putString("longitudeString", longitude.getText().toString());
            editor.putString("labelString", label.getText().toString());
            editor.putString("mockOrientation", mockOrientation.getText().toString());

        } else {

            //error if empty coords
            this.showAlert(this,
                    "Value for latitude/longitude cannot be empty!");
            latitude.setText("0");
            longitude.setText("0");
            return false;
        }

        editor.apply();
        return true;

    }

    /*
     * Builds alerts/error messages
     */
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


    /*
     * When save button is clicked, save values and go to mainActivity
     */
    public void onLaunchSaveClicked(View view) {
        if (saveProfile()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /*
     * When "x" is clicked, do not save values and go to mainActivity
     */
    public void onLaunchExitButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}