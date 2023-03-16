//package edu.ucsd.cse110.lab4;
//
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import org.junit.runner.RunWith;
//
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import edu.ucsd.cse110.lab4.activity.ProfileActivity;
//
//@RunWith(AndroidJUnit4.class)
//public class ExampleUnitTest {
//    @Test
//    public void testLoadProfile() {
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//
//            activity.loadProfile(); //  load user profile
//
//            TextView lat  = (TextView) activity.findViewById(R.id.latitude);
//            TextView lon  = (TextView) activity.findViewById(R.id.longitude);
//            TextView label = (TextView) activity.findViewById(R.id.label);
//            TextView mockOrientation = (TextView) activity.findViewById(R.id.editMockOrientation);
//
//            lat.setText("0.0");
//            lon.setText("0.0");
//            label.setText("");
//            mockOrientation.setText("");
//
//            assertEquals("0.0", lat.getText().toString()); // get initial values
//            assertEquals("0.0", lon.getText().toString());
//            assertEquals("", label.getText().toString());
//            assertEquals("", mockOrientation.getText().toString());
//
//            lat.setText("0.02");
//            lon.setText("-0.01");
//            label.setText("my house");
//            mockOrientation.setText("North");
//
//            assertEquals("0.02", lat.getText().toString());
//            assertEquals("-0.01", lon.getText().toString());
//            assertEquals("my house", label.getText().toString());
//            assertEquals("North", mockOrientation.getText().toString());
//        });
//    }
//
//    @Test
//    public void testEditLongLat() {
//        // launch new page
//        ActivityScenario<ProfileActivity> scenario_1 = ActivityScenario.launch(ProfileActivity.class);
//        scenario_1.moveToState(Lifecycle.State.CREATED);
//        scenario_1.moveToState(Lifecycle.State.STARTED);
//        scenario_1.onActivity(activity_1 -> {
//            activity_1.loadProfile();
//            TextView lat_1  = (TextView) activity_1.findViewById(R.id.latitude);
//            TextView lon_1  = (TextView) activity_1.findViewById(R.id.longitude);
//
//            lat_1.setText("-0.05");
//            lon_1.setText("10");
//
//            assertEquals("-0.05", lat_1.getText().toString()); // initial long/lat
//            assertEquals("10", lon_1.getText().toString());
//        });
//    }
//
//    @Test
//    public void testSaveProfileInvalid() throws Exception {
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//            TextView lat  = (TextView) activity.findViewById(R.id.latitude);
//            TextView lon  = (TextView) activity.findViewById(R.id.longitude);
//
//            lat.setText("");
//            try {
//                double lats = Double.parseDouble(lat.getText().toString());
//                fail("should throw exception");
//            } catch (Exception e) {
//                assertFalse(activity.saveProfile());
//            }
//
//            lon.setText("");
//            try {
//                double lons = Double.parseDouble(lon.getText().toString());
//                fail("should throw exception");
//            } catch (Exception e) {
//                assertFalse(activity.saveProfile());
//            }
//
//            lat.setText("abc");
//            try {
//                double lats = Double.parseDouble(lat.getText().toString());
//                fail("should throw exception");
//            } catch (Exception e) {
//                assertFalse(activity.saveProfile());
//            }
//
//            lon.setText("x?!");
//            try {
//                double lons = Double.parseDouble(lon.getText().toString());
//                fail("should throw exception");
//            } catch (Exception e) {
//                assertFalse(activity.saveProfile());
//            }
//
//        });
//    }
//
//    @Test
//    public void testSaveProfileValid() {
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//            TextView lat = (TextView) activity.findViewById(R.id.latitude);
//            TextView lon = (TextView) activity.findViewById(R.id.longitude);
//
//            lat.setText("10");
//            assertTrue(activity.saveProfile());
//
//            lon.setText("200");
//            assertTrue(activity.saveProfile());
//
//            assertEquals("10", lat.getText().toString());
//            assertEquals("200", lon.getText().toString());
//        });
//    }
//
//    @Test
//    public void testShowAlertWorking() { // test
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//            activity.showAlert(activity, "testing alert");
//        });
//    }
//
//    @Test
//    public void testSaveButtonClickedWorking() {
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//
//            // perform click
//            Button btn;
//            btn = (Button) activity.findViewById(R.id.saveButton);
//            boolean clicked = btn.performClick();
//
//            assertTrue(clicked); // assert if clicked
//
//        });
//
//    }
//
//    @Test
//    public void testExitButtonClickedWorking() {
//        ActivityScenario<ProfileActivity> scenario = ActivityScenario.launch(ProfileActivity.class);
//        scenario.moveToState(Lifecycle.State.CREATED);
//        scenario.moveToState(Lifecycle.State.STARTED);
//        scenario.onActivity(activity -> {
//
//            // perform click
//            Button btn;
//            btn = (Button) activity.findViewById(R.id.exitButton);
//            boolean clicked = btn.performClick();
//
//            assertTrue(clicked); // assert if clicked
//
//        });
//
//    }
//}
