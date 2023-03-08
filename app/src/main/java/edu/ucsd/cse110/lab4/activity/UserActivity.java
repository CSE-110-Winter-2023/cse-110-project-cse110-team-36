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
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.ucsd.cse110.lab4.R;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.view.UsersAdapter;
import edu.ucsd.cse110.lab4.viewmodel.ListViewModel;
import edu.ucsd.cse110.lab4.viewmodel.UserViewModel;

public class UserActivity extends AppCompatActivity {

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // This one is for testing purpose
//        List<User> users = User.loadJSON(this, "db_demo.json");
//        Log.d("UserActivity", users.toString());

        ListViewModel viewModel = setupViewModel();
        UserViewModel userViewModel = setupUserViewModel();
        UsersAdapter adapter = setupAdapter(viewModel);

        setupViews(viewModel, adapter, userViewModel);
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
        return adapter;
    }

    private void onLabelClicked(User note, UserViewModel viewModel) {
    }

    private void setupViews(ListViewModel viewModel, UsersAdapter adapter,
                            UserViewModel userViewModel) {
        setupRecycler(adapter);
        setupAddButton(viewModel, userViewModel);
    }

    private void setupAddButton(ListViewModel viewModel, UserViewModel userViewModel) {
        Button addBtn = findViewById(R.id.user_add_btn);
        addBtn.setOnClickListener((View v) -> {
            EditText input = (EditText) findViewById(R.id.user_input_uid);
            assert input != null;
            String uid = input.getText().toString();
            //Log.d("UID", uid);
            LiveData<User> user = viewModel.getOrCreateUser(uid);
//            Log.d("Add", user.getValue().uniqueID);
            user.observe(this, userEntity -> {
                user.removeObservers(this);
            });
//            var liveUser = userViewModel.getUser(uid);
//            Log.d("Live User", liveUser.toString());
//            onAddButtonClicked(userViewModel, liveUser);
//             user.observe(this, this::onUserChanged);
        });

    }

    private void onUserChanged(User user) {

    }

    void onAddButtonClicked(UserViewModel viewModel, LiveData<User> user) {
        User localUser = user.getValue();
//        Log.d("onAddButtonClicked", localUser.toString());
        viewModel.add(localUser);
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


}