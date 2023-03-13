package edu.ucsd.cse110.lab4.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private volatile static UserDatabase instance = null;

    public abstract UserDao getDao();

    public synchronized static UserDatabase provide(Context context) {
        if (instance == null) {
            instance = UserDatabase.make(context);
        }
        return instance;
    }

    private static UserDatabase make(Context context) {
        return Room.databaseBuilder(context, UserDatabase.class, "user_db.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @VisibleForTesting
    public static void inject(UserDatabase testDatabase) {
        if (instance != null ) {
            instance.close();
        }
        instance = testDatabase;
    }

    /*start*/

    /*end*/

}
