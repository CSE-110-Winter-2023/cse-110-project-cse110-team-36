package edu.ucsd.cse110.lab4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.UUID;

import edu.ucsd.cse110.lab4.R;

public class UIDActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid);

        Intent receiveIntent = getIntent();
        String receiveMessage = receiveIntent.getStringExtra("message");
        TextView name = findViewById(R.id.name);
        name.setText(receiveMessage);
        // UID
        //UUID = UUID.randomUUID();
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