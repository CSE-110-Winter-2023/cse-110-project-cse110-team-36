package edu.ucsd.cse110.lab4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDao;
import edu.ucsd.cse110.lab4.model.UserDatabase;

@RunWith(AndroidJUnit4.class)
public class UserDatabaseTest {
    private UserDao dao;
    private UserDatabase db;
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, UserDatabase.class)
                .allowMainThreadQueries()
                .build();

        dao = db.getDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void testInsert() {
        User user1 = new User("test-db", "-41", "-10");
        User user2 = new User("test-db-2", "0", "0");

        long id1 = dao.upsert(user1);
        long id2 = dao.upsert(user2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void testGet() {
        User user1 = new User("test-db", "-41", "-10");
        dao.upsert(user1);

        LiveData<User> getUser1 = dao.get(user1.uniqueID);

        assertEquals(user1.uniqueID, getUser1.uniqueID);
    }

    @Test
    public void testGetAll() {
        User user1 = new User("test-db", "-41", "-10");
        dao.upsert(user1);

        LiveData<User> getUser1 = dao.get(user1.uniqueID);

        assertEquals(user1.uniqueID, getUser1.uniqueID);
    }

    @Test
    public void testGetAll() {
        User user1 = new User("test-db", "-41", "-10");
        dao.upsert(user1);

        LiveData<User> getUser1 = dao.get(user1.uniqueID);

        assertEquals(user1.uniqueID, getUser1.uniqueID);
    }
}
