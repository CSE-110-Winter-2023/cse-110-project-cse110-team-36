package edu.ucsd.cse110.lab4.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserAPI;
import edu.ucsd.cse110.lab4.model.UserDao;
import edu.ucsd.cse110.lab4.model.UserDatabase;
import edu.ucsd.cse110.lab4.model.UserRepository;

public class ListViewModel extends AndroidViewModel {
    private LiveData<List<User>> users;
    private final UserRepository repo;

    public ListViewModel(@NonNull Application application) {
        super(application);
        android.content.Context context = application.getApplicationContext();
        UserDatabase db = UserDatabase.provide(context);
        UserDao dao = db.getDao();
        this.repo = new UserRepository(dao);
    }

    public LiveData<List<User>> getUsers() {
        if (users == null) {
            users = repo.getAllLocal();
        }
        return users;
    }

    public LiveData<User> getOrCreateUser(String public_code) {
        if (!repo.existsLocal(public_code)) {
            User user = new  User(public_code, "0", "0");
            Log.d("getOrCreate", user.toString());
            repo.upsertLocal(user);
        }
        Log.d("OUTSIDE", "body " + repo.getLocal(public_code).toString());

        return repo.getLocal(public_code);
    }

    public void delete(User user) {
        repo.deleteLocal(user);
    }
}
