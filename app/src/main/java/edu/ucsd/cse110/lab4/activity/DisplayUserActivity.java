package edu.ucsd.cse110.lab4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class DisplayUserActivity extends AppCompatActivity {

    public LiveData<User> user;
    TextView nameTextView;
    TextView uidTextView;
    String label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayuser);

        nameTextView = findViewById(R.id.user_name);
        uidTextView = findViewById(R.id.user_uid);

        loadProfile();
        disabledEdit();

        String uuid = getIntent().getStringExtra("UUID");
        var viewModel = setupViewModel();

        user = viewModel.getUser(uuid);
    }

    private void loadProfile() {
        SharedPreferences preferences = this.getSharedPreferences("UUID", MODE_PRIVATE);
        String id = preferences.getString("myUUID","");
        String name = preferences.getString("myName", "");
        display(name, id);
    }

    @SuppressLint("SetTextI18n")
    private void display(String name, String uuid) {
        nameTextView.setText(name);
        uidTextView.setText(uuid);
    }

    private void disabledEdit() {
        uidTextView.setFocusable(false);
        uidTextView.setCursorVisible(false);
        uidTextView.setKeyListener(null);

        nameTextView.setFocusable(false);
        nameTextView.setCursorVisible(false);
        nameTextView.setKeyListener(null);
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

    public static Intent intentFor(Context context, User user) {
        var intent = new Intent(context, DisplayUserActivity.class);
        intent.putExtra("UUID", user.uniqueID);
        return intent;
    }
}