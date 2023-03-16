package edu.ucsd.cse110.lab4;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.ucsd.cse110.lab4.activity.AddFriendActivity;
import edu.ucsd.cse110.lab4.model.User;
import edu.ucsd.cse110.lab4.model.UserDao;
import edu.ucsd.cse110.lab4.model.UserDatabase;

@RunWith(AndroidJUnit4.class)
public class MS2US1Tests {
    UserDatabase db;
    UserDao dao;

    private static void forceLayout(RecyclerView recyclerView) {
        recyclerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        recyclerView.layout(0,0,1080,2280);
    }

    @Before
    public void resetDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context,UserDatabase.class)
                .allowMainThreadQueries()
                .build();

        UserDatabase.inject(db);
        List<User> users = User.loadJSON(context,"db_demo.json");
        dao = db.getDao();
        dao.insertAll(users);
    }

    @Test
    public void testAddNewFriend() {
        String newUID = "test-uid";
        ActivityScenario<AddFriendActivity> scenario = ActivityScenario.launch(AddFriendActivity.class);
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            List<User> before =  dao.getAllLocal();

            EditText public_code = activity.findViewById(R.id.user_input_uid);
            Button addBtn = activity.findViewById(R.id.user_add_btn);

            public_code.setText(newUID);
            addBtn.performClick();

            List<User> after = dao.getAllLocal();
            assertEquals(before.size()+1, after.size());
            assertEquals(newUID, after.get(after.size()-1).uniqueID);
        });
    }

}
