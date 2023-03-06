package edu.ucsd.cse110.lab4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // This one is for testing purpose
        List<User> users = User.loadJSON(this, "db_demo.json");
        Log.d("UserActivity", users.toString());
    }
}