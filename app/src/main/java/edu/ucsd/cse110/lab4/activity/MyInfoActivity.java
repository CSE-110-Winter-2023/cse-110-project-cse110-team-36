package edu.ucsd.cse110.lab4.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.viewmodel.ListViewModel;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class MyInfoActivity extends AppCompatActivity {
    LocationService locationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        getPermissions();

        var viewModel = setupViewModel();
        setUpAddButton(viewModel);

        locationService = new LocationService(this);
    }

    private ListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(ListViewModel.class);
    }

    private UserViewModel setupUserViewModel() {
        return new ViewModelProvider(this).get(UserViewModel.class);
    }
    public void onClearClicked (View view) {
        EditText userInput = findViewById(R.id.user_input_name);
        userInput.setText("");
    }
    public void setUpAddButton (ListViewModel viewModel) {
        var addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener((View v) -> {
            EditText userInput = findViewById(R.id.user_input_name);
            String name = userInput.getText().toString();
            String uuid = UUID.randomUUID().toString().substring(0,12);

            SharedPreferences preferences = this.getSharedPreferences("UUID", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            //Save preferences
            editor.putString("myUUID", uuid);
            editor.putString("myName", name);
            editor.apply();

            var user = viewModel.getOrCreateUser(uuid);
            user.observe(this, userEntity -> {
                userEntity.label = name;
                locationService.getLocation().observe(this, coords -> {
                    String latitude = Double.toString(coords.first);
                    String longitude = Double.toString(coords.second);

                    userEntity.latitude = latitude;
                    userEntity.longitude = longitude;
                });

                UserViewModel userViewModel = setupUserViewModel();
                userViewModel.add(userEntity);

                var intent = DisplayUserActivity.intentFor(this, userEntity);
                startActivity(intent);
            });

        });
    }

    public void onExitClicked(View view) {
        Intent intent = new Intent(this, TwoZoomActivity.class);
        startActivity(intent);
        finish();
    }

    private void getPermissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }
}
