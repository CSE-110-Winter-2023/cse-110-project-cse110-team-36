package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;

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
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);

        String latitudeString = preferences.getString("latitudeString", "");
        String longitudeString = preferences.getString("longitudeString", "");
        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);

        latitude.setText(latitudeString);
        longitude.setText(longitudeString);
    }

    public boolean saveProfile() {
        double lat = 0;
        double longt = 0;
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);

        // Check valid input longitude and latitude
        if (!(latitude.getText().toString().isEmpty() ||
                longitude.getText().toString().isEmpty())) {
            editor.putString("latitudeString", latitude.getText().toString());
            editor.putString("longitudeString", longitude.getText().toString());

            // Convert latitude and longitude to double
            lat = Double.parseDouble(latitude.getText().toString());
            longt = Double.parseDouble(longitude.getText().toString());

        } else {

            // If invalid, profile won't be saved
            Utilities.showAlert(this,
                    "Value for latitude/longitude cannot be empty!");
            return false;
        }
        editor.apply();
        return true;
    }


    public void onLaunchSaveClicked(View view) {
        if (saveProfile()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void onLaunchExitButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}