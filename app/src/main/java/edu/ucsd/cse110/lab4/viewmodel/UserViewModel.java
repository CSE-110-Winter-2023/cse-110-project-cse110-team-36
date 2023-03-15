package edu.ucsd.cse110.lab4.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDao;
import edu.ucsd.cse110.lab4.model.UserDatabase;
import edu.ucsd.cse110.lab4.model.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private LiveData<User> user;
    private LiveData<List<User>> users;
    private final UserRepository repo;


    public UserViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDao dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    public LiveData<User> getUser(String public_code) {
        //if (user == null) {
            user = repo.getSynced(public_code);
        //}
        return user;
    }

    public void add(User user) {
        repo.upsertSynced(user);
    }

}
