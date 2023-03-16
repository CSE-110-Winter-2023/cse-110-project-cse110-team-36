package edu.ucsd.cse110.lab4.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
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
//    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//    SharedPreferences.Editor editor = preferences.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        var viewModel = setupViewModel();
        setUpAddButton(viewModel);



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
//                userEntity.longitude = "10";
//                userEntity.latitude ="10";
//                locationService.getLocation().observe(this, coords -> {
//                    String latitude = Double.toString(coords.first);
//                    String longitude = Double.toString(coords.second);
//
//                    userEntity.latitude = latitude;
//                    userEntity.longitude = longitude;
//                });

                UserViewModel userViewModel = setupUserViewModel();
                userViewModel.add(userEntity);

//                //create an intent to launch the new activity
//                Intent intent = new Intent(this, DisplayUserActivity.class);
//                // pass the name and uid as extras in the intent
//                intent.putExtra("UUID", uuid);
//                // start the new activity
//                startActivity(intent);


                //user.removeObservers(this);
                var intent = DisplayUserActivity.intentFor(this, userEntity);
                startActivity(intent);
            });

//            // create an intent to launch the new activity
//            Intent intent = new Intent(this, DisplayUserActivity.class);
//            // pass the name and uid as extras in the intent
//            intent.putExtra("NAME", name);
//            // start the new activity
//            startActivity(intent);
        });
//        Log.d("MyInfoActivity", "onAddClicked called");
    }

    public void onExitClicked(View view) {
        Intent intent = new Intent(this, TwoZoomActivity.class);
        startActivity(intent);
        finish();
    }
}
