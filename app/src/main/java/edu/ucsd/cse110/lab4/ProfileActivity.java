package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;

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

        String latitudeString = preferences.getString("latitudeString", "");
        String longitudeString = preferences.getString("longitudeString", "");
        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);

        latitude.setText(latitudeString);
        longitude.setText(longitudeString);
    }

    public void saveProfile() {
        SharedPreferences preferences = getSharedPreferences("coordinates", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        TextView latitude = this.findViewById(R.id.latitude);
        TextView longitude = this.findViewById(R.id.longitude);
        editor.putString("latitudeString", latitude.getText().toString());
        editor.putString("longitudeString", longitude.getText().toString());
        editor.apply();
    }


    public void onLaunchSaveClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        saveProfile();
    }

    public void onLaunchExitButtonClicked(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}