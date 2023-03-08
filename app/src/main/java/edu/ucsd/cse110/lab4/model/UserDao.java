package edu.ucsd.cse110.lab4.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import java.util.List;

@Dao
public abstract class UserDao {
    @Upsert
    public abstract long upsert(User user);

    @Query("SELECT EXISTS(SELECT 1 FROM `users` WHERE `uniqueID` = :uniqueID)")
    public abstract boolean exists(String uniqueID);

    @Query("SELECT * FROM `users` WHERE `uniqueID` = :uniqueID")
    public abstract LiveData<User> get(String uniqueID);

    @Query("SELECT * FROM `users` ORDER BY `uniqueID`")
    public abstract LiveData<List<User>> getAll();

    @Query("SELECT * FROM `users` ORDER BY `uniqueID`")
    public abstract List<User> getAllLocal();


    @Delete
    public abstract int delete(User user);

    @Insert
    public abstract List<Long> insertAll(List<User> users);
}
