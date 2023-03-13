package edu.ucsd.cse110.lab4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;

public class MyInfoActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);


    }
    public void onClearClicked (View view) {
        EditText userInput = findViewById(R.id.user_input_name);
        userInput.setText("");
    }
    public void onAddClicked (View view) {
//        Log.d("MyInfoActivity", "onAddClicked called");
        EditText userInput = findViewById(R.id.user_input_name);
        String name = userInput.getText().toString();

        // create an intent to launch the new activity
        Intent intent = new Intent(this, DisplayUserActivity.class);

        // pass the name and uid as extras in the intent
        intent.putExtra("name", name);

        // start the new activity
        startActivity(intent);
    }
}
