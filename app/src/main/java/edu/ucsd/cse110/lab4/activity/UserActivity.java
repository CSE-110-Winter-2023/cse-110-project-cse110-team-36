package edu.ucsd.cse110.lab4.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDao;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class UserActivity extends AppCompatActivity {
    private LiveData<User> user;
    private UserDao dao;
    private TextView labelView;
    private TextView latView;
    private TextView longView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        labelView = findViewById(R.id.user_info_label);
        latView = findViewById(R.id.user_info_latitude);
        longView = findViewById(R.id.user_info_longitude);

        var intent = getIntent();
        var uid = intent.getStringExtra("user_uniqueId");

        var viewModel = setupViewModel();
        user = viewModel.getUser(uid);
        user.observe(this, this::onUserChanged);
    }

    private void onUserChanged(User user) {
        labelView.setText(user.label);
        latView.setText(user.latitude);
        longView.setText(user.longitude);
        Log.d("Label", labelView.getText().toString());
        Log.d("Lat", latView.getText().toString());
        Log.d("Long", longView.getText().toString());
    }

    private UserViewModel setupViewModel() {
        return new ViewModelProvider(this).get(UserViewModel.class);
    }

    public static Intent intentFor(Context context, User user) {
        var intent = new Intent(context, UserActivity.class);
        intent.putExtra("user_uniqueId", user.uniqueID);
        return intent;
    }

    public void onExitClicked (View view) {
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
        finish();
    }

}