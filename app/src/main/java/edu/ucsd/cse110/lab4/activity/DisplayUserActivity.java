package edu.ucsd.cse110.lab4.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import edu.ucsd.cse110.lab4.R;

public class DisplayUserActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayuser);

        String name = getIntent().getStringExtra("name");
        String uid = getIntent().getStringExtra("uid");

        TextView nameTextView = findViewById(R.id.user_input_name);
        TextView uidTextView = findViewById(R.id.user_uid);

        nameTextView.setText("Name: " + name);
        uidTextView.setText("UID: " + uid);



    }
}