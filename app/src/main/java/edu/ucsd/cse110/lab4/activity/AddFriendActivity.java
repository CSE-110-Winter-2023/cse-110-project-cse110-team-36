package edu.ucsd.cse110.lab4.activity;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.view.UsersAdapter;
import edu.ucsd.cse110.lab4.viewmodel.ListViewModel;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class AddFriendActivity extends AppCompatActivity {
    String label;
    String uniqueId;

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriend);

        // This one is for testing purpose
//        List<User> users = User.loadJSON(this, "db_demo.json");
//        Log.d("UserActivity", users.toString());

        var viewModel = setupViewModel();
        var adapter = setupAdapter(viewModel);

        setupViews(viewModel, adapter);
    }

    private ListViewModel setupViewModel() {
        return new ViewModelProvider(this).get(ListViewModel.class);
    }

    private UserViewModel setupUserViewModel() {
        return new ViewModelProvider(this).get(UserViewModel.class);
    }

    @NonNull
    private UsersAdapter setupAdapter(ListViewModel viewModel) {
        UsersAdapter adapter = new UsersAdapter();
        adapter.setHasStableIds(true);
        //adapter.setOnLabelClickedHandler(note -> onLabelClicked(note, viewModel));
        adapter.setOnUserDeleteClickListener(note -> onUserDeleteClicked(note, viewModel));
        viewModel.getUsers().observe(this, adapter::setUsers);
        return adapter;
    }


    private void setupViews(ListViewModel viewModel, UsersAdapter adapter) {
        setupRecycler(adapter);
        setupAddButton(viewModel);
    }

    private void setupAddButton(ListViewModel viewModel) {
        var addBtn = findViewById(R.id.user_add_btn);

        addBtn.setOnClickListener((View v) -> {
            var input = (EditText) findViewById(R.id.user_input_uid);
            assert input != null;
            var uid = input.getText().toString();

            var user = viewModel.getOrCreateUser(uid);

            user.observe(this, userEntity -> {
                user.removeObservers(this);
                UserViewModel userViewModel = setupUserViewModel();
                LiveData<User> userLiveData = userViewModel.getUser(uid);
                // Wait for the data to update from remote
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                userLiveData.observe(this, this::onUserChanged);

                // Display friend's name and uid
                displayUser(label, uniqueId);
            });
        });

    }

    private void onUserChanged(User user) {
        label = user.label;
        uniqueId = user.uniqueID;
    }

    private void displayUser(String name, String uid) {
        TextView labelView = findViewById(R.id.user_item_label);
        TextView uidView = findViewById(R.id.user_item_uid);
        labelView.setText(label);
        uidView.setText(uniqueId);
    }

    @SuppressLint("RestrictedApi")
    private void setupRecycler(UsersAdapter adapter) {
        recyclerView = findViewById(R.id.recycle_user);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void onUserDeleteClicked(User user, ListViewModel viewModel) {
        // Delete the user
        Log.d("UsersAdapter", "Deleted user " + user.uniqueID);
        viewModel.delete(user);
    }

    public void onExitClicked (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}