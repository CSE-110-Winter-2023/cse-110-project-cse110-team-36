package edu.ucsd.cse110.lab4.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
            String uuid = UUID.randomUUID().toString();

            SharedPreferences preferences = this.getSharedPreferences("UUID", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            // Save preferences
            editor.putString("myUUID", uuid);
            editor.putString("myName", name);
            editor.apply();

            var user = viewModel.getOrCreateUser(uuid);
            user.observe(this, userEntity -> {
                userEntity.label = name;
                userEntity.longitude = "10";
                userEntity.latitude ="10";
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
}
