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

//        Button addButton = findViewById(R.id.add_btn);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText userInput = findViewById(R.id.user_input_name);
//                String name = userInput.getText().toString();
//                String uid = generateUid();
//                // create a new user object with the name and uid
//                // add the user object to the recyclerview adapter
//            }
//        });

    }
//    private String generateUid() {
//        // generate a unique id using a random number generator or timestamp
//        // return the id as a string
//        long timestamp = System.currentTimeMillis();
//        int random = new Random().nextInt(1000000);
//        String uid = String.format("%d%d", timestamp, random);
//        return uid;
//    }
    public void onClearClicked (View view) {
        EditText userInput = findViewById(R.id.user_input_name);
        userInput.setText("");
    }
    public void onAddClicked (View view) {
//        Log.d("MyInfoActivity", "onAddClicked called");
        EditText userInput = findViewById(R.id.user_input_name);
        String name = userInput.getText().toString();

//        // generate a unique id
//        String uid = generateUid();

        // create an intent to launch the new activity
        Intent intent = new Intent(this, DisplayUserActivity.class);

        // pass the name and uid as extras in the intent
        intent.putExtra("name", name);
//        intent.putExtra("uid", uid);

        // start the new activity
        startActivity(intent);
    }
}
