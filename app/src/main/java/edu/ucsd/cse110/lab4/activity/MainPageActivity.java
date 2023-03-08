package edu.ucsd.cse110.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.ucsd.cse110.lab4.activity.SeeCompassActivity;
import edu.ucsd.cse110.lab4.activity.UserActivity;
import edu.ucsd.cse110.lab4.activity.YourInfoActivity;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // your info
    public void setOnClickListener(View view) {
        Intent intent = new Intent(this, YourInfoActivity.class);
        startActivity(intent);
    }
    // add friends
    public void setOnClickListener2(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }
    // see compass
    public void setOnClickListener3(View view) {
        Intent intent = new Intent(this, SeeCompassActivity.class);
        startActivity(intent);
    }
}