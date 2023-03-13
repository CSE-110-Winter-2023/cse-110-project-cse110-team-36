package edu.ucsd.cse110.lab4.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDatabase;

public class DisplayUserActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;
    private UserDatabase userDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayuser);

        String name = getIntent().getStringExtra("name");

        TextView nameTextView = findViewById(R.id.user_input_name);
        TextView uidTextView = findViewById(R.id.user_uid);

        nameTextView.setText("Name: " + name);
        // Generate UUID and store it in SharedPreferences
        String uuid = UUID.randomUUID().toString();
        SharedPreferences prefs = getSharedPreferences("MY_PREFS_NAME", MODE_PRIVATE);
        prefs.edit().putString("MY_UUID", uuid).apply();

        uidTextView.setText("UID : " + uuid);

    }
    public void onExitClicked (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}