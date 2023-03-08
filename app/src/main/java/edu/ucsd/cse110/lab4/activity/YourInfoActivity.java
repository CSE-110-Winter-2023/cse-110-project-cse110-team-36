package edu.ucsd.cse110.lab4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.ucsd.cse110.lab4.R;

public class YourInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourinfo);


    }
    public void setOnAddClickListener(View view) {
        //name
        EditText text = findViewById(R.id.name);
        final String name = text.getText().toString();
        Intent sendIntent = new Intent(this, UIDActivity.class);
        sendIntent.putExtra("message",name);
        startActivity(sendIntent);

    }
    public void setOnClickListenerHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void setOnclearClickListener(View view) {
        EditText clear = findViewById(R.id.name);
        clear.setText("");
    }
}