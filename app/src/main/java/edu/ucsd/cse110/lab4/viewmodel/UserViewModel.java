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
    private User userLocal;
    private LiveData<List<User>> users;
    private final UserRepository repo;
    private UserDao dao;


    public UserViewModel(@NonNull Application application) {
        super(application);
        Context context = application.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    public LiveData<User> getUser(String public_code) {
        //if (user == null) {
        UserRepository newRepo = new UserRepository(dao);
        LiveData<User> user = newRepo.getSynced(public_code);
        //}
        return user;
    }

    public void add(User user) {
        repo.upsertSynced(user);
    }

    public User getUserLocal (String public_code) {
        if (userLocal == null) {
            userLocal = repo.getUserLocal(public_code);
        }
        return userLocal;
    }


}
