package edu.ucsd.cse110.lab4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

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

        User getUser1 = dao.getLocal(user1.uniqueID);
        assertEquals(getUser1.uniqueID, "test-db");
        assertEquals(getUser1.latitude, "-41");
        assertEquals(getUser1.longitude, "-10");


        User getUser2 = dao.getLocal(user2.uniqueID);
        assertEquals(getUser2.uniqueID, "test-db-2");
        assertEquals(getUser2.latitude, "0");
        assertEquals(getUser2.longitude, "0");

        assertTrue(dao.exists(getUser1.uniqueID));
        assertTrue(dao.exists(getUser2.uniqueID));

        assertNotEquals(user1.uniqueID, user2.uniqueID);
        assertNotEquals(user1.latitude, user2.latitude);
        assertNotEquals(user1.longitude, user2.longitude);
        assertNotEquals(id1, id2);
    }

    @Test
    public void testGet() {
        User user1 = new User("test-db", "1", "-10");
        long id1 = dao.upsert(user1);

        User getUser1 = dao.getLocal(user1.uniqueID);
        assertEquals(getUser1.uniqueID, "test-db");
        assertEquals(getUser1.latitude, "1");
        assertEquals(getUser1.longitude, "-10");

        assertTrue(dao.exists(getUser1.uniqueID));
    }
}