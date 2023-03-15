package edu.ucsd.cse110.lab4.activity;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.LocationService;
import edu.ucsd.cse110.lab4.OrientationService;
import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.Compass;
import edu.ucsd.cse110.lab4.model.Dot;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.viewmodel.ListViewModel;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class FourZoomActivity extends AppCompatActivity {

    public OrientationService orientationService;
    LocationService locationService;
    String UID;
    ListViewModel viewModel;
    UserViewModel userViewModel;
    Compass compass;

    /*
     * Updates compass according to orientation, location, and entered values on profileActivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_zoom);


        getPermissions();

        viewModel = new ViewModelProvider(this).get(ListViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        orientationService = new OrientationService(this);
        locationService = new LocationService(this);
        ImageView compass1 = findViewById(R.id.compass_base);

        Compass compass = new Compass(locationService, orientationService, this, 4, compass1);
        //addUsers();

        checkMyStatus();
    }


    private void addUsers() {
        LiveData<List<User>> userList = viewModel.getUsers();
        List<User> users = userList.getValue();
        if (users == null) {
            return;
        }
        for (User thisUser : users) {
            String UID = thisUser.uniqueID;
            LiveData<User> currUser = userViewModel.getUser(UID);
            ConstraintLayout mainLayout = (ConstraintLayout) findViewById(R.id.include);
            LayoutInflater inflater = getLayoutInflater();
            View myLayout = inflater.inflate(R.layout.dot_layout, mainLayout, false);
            ImageView dotID = myLayout.findViewById(R.id.coordDot);
            TextView label = myLayout.findViewById(R.id.labelView);
            Dot dot = new Dot(currUser, locationService, compass, this, dotID, label);
            mainLayout.addView(myLayout);
        }
        return;
    }

    private void getPermissions() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    /*
     * halts updates to compass on pause (as to not drain battery)
     */
    @Override
    protected void onPause() {
        super.onPause();
        orientationService.unregisterSensorListeners();
    }

    /*
     * Begins profileActivity when "list" is clicked
     */
    public void onLaunchListClicked(View view) {
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLaunchViewClicked(View view) {
        TextView UIDView = this.findViewById(R.id.TextViewUID);
        UID = UIDView.getText().toString();
    }

    public void onZoomInClicked(View view) {
        Intent intent = new Intent(this, ThreeZoomActivity.class);
        startActivity(intent);
        finish();
    }

    public void onLaunchMyInfoClicked(View view) {
        Intent intent = new Intent(this, DisplayUserActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void checkMyStatus() {
        SharedPreferences preferences = this.getSharedPreferences("UUID", MODE_PRIVATE);
        String id = preferences.getString("myUUID","");
        ImageView offline = findViewById(R.id.offline_four_zoom);
        TextView status = findViewById(R.id.status_four_zoom);
        ImageView online = findViewById(R.id.online_four_zoom);

        var myUser = userViewModel.getUserLocal(id);

        Log.d("MY USER", myUser.toString());
        Log.d("MY USER UPDATE AT", String.valueOf(myUser.updatedAt));

        long time = lastUpdate(myUser.updatedAt);

        if (time < 1) {
            offline.setVisibility(View.INVISIBLE);
            status.setVisibility(View.INVISIBLE);
            online.setVisibility(View.VISIBLE);
        } else {
            offline.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            online.setVisibility(View.INVISIBLE);

            int minute;
            int hour;
            hour = (int) time / 60;
            minute = (int) time % 60;

            if (hour < 1) {
                status.setText(minute + "m");
            } else {
                status.setText(hour + "h"
                        + minute + "m");
            }

        }
    }
    public long lastUpdate(long updateAt){
        //covert seconds to milliseconds
        long seconds = updateAt;
        //make date
        Date dateUpdatedAt = new Date(seconds * 1000);
        Date dateCurrent = new Date();

        //find time since this date
        long diff = dateCurrent.getTime() - dateUpdatedAt.getTime();

        //convert distance to minutes
        long inactiveDurationMinutes = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);

        Log.d("inactive minutes", valueOf(inactiveDurationMinutes));

        return inactiveDurationMinutes;
    }
}