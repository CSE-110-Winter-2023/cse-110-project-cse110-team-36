package edu.ucsd.cse110.lab4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDatabase;
import edu.ucsd.cse110.lab4.viewmodel.ListViewModel;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class DisplayUserActivity extends AppCompatActivity {

    private LiveData<User> user;
    TextView nameTextView;
    TextView uidTextView;

    static String name;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayuser);

        nameTextView = findViewById(R.id.user_input_name);
        uidTextView = findViewById(R.id.user_uid);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        id = preferences.getString("myUUID","");
//        name = preferences.getString("myName", "");
//
//        display(name, id);

        String uuid = getIntent().getStringExtra("UUID");
        display(name, uuid);
        var viewModel = setupViewModel();
        user = viewModel.getUser(uuid);

        user.observe(this, this::onUserChanged);
    }

    private void onUserChanged(User user) {
        name = user.label;
    }

    @SuppressLint("SetTextI18n")
    private void display(String name, String uuid) {
        nameTextView.setText("Name: " + name);
        uidTextView.setText("UID : " + uuid);
    }

    private UserViewModel setupViewModel() {
        return new ViewModelProvider(this).get(UserViewModel.class);
    }
    public void onExitClicked (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLaunchCreateClicked(View view) {
        Intent intent = new Intent(this, MyInfoActivity.class);
        startActivity(intent);
        finish();
    }

    public static Intent intentFor(Context context, User user, String label) {
        var intent = new Intent(context, DisplayUserActivity.class);
        intent.putExtra("UUID", user.uniqueID);
        name = label;
        return intent;
    }

}