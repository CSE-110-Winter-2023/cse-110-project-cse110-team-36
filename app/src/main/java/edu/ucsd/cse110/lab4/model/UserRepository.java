package edu.ucsd.cse110.lab4.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class UserRepository {
    private final UserDao dao;
    private UserAPI api;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledFuture = null;

    public UserRepository(UserDao dao) {
        this.dao = dao;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.api = new UserAPI();
    }

    public LiveData<User> getSynced(String public_code) {
        MediatorLiveData<User> user = new MediatorLiveData<User>();

        Observer<User> updateFromRemote = theirUser -> {
            if (theirUser == null) return;
            User ourUser = user.getValue();
            upsertLocal(theirUser);
        };

        // If we get a local update, pass it on.
        user.addSource(getLocal(public_code), user::postValue);
        // If we get a remote update, update the local version (triggering the above observer)
        user.addSource(getRemote(public_code), updateFromRemote);

        return user;
    }

    public void upsertSynced(User user) {
        upsertLocal(user);
        upsertRemote(user);
    }

    public LiveData<User> getLocal(String public_code) {
        return dao.get(public_code);
    }

    public LiveData<List<User>> getAllLocal() {
        return dao.getAll();
    }

    public void upsertLocal(User user) {
        //note.updatedAt = System.currentTimeMillis();
        //user.updatedAt = Instant.now().getEpochSecond();
        dao.upsert(user);
    }

    public boolean existsLocal(String public_code) {
        return dao.exists(public_code);
    }

    public LiveData<User> getRemote(String public_code) {
        MediatorLiveData<User> remoteUser = new MediatorLiveData<>();
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }

        scheduledFuture = scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //Note currData = api.getByTitle(title);
                remoteUser.postValue(api.getByPublicCode(public_code));
            }
        }, 0, 3000, TimeUnit.MILLISECONDS);
        return remoteUser;
    }

    public void upsertRemote(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                api.putByPublicCode(user);
            }
        }).start();
    }

    public void deleteLocal(User user) {
        dao.delete(user);
    }
}
