package edu.ucsd.cse110.lab4.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

class TimestampAdapter extends TypeAdapter<Long> {
    @Override
    public void write(com.google.gson.stream.JsonWriter out, Long value) throws IOException {
        Instant instant = Instant.ofEpochSecond(value);
        out.value(instant.toString());
    }

    @Override
    public Long read(com.google.gson.stream.JsonReader in) throws IOException {
        Instant instant = Instant.parse(in.nextString());
        return instant.getEpochSecond();
    }
}
@Entity(tableName = "users")
public class User {

    /**
     * The UUID of the code. Used as the primary key for shared locations
     * (public_code on the cloud)
     */
    @PrimaryKey
    @SerializedName("public_code")
    @NotNull
    public String uniqueID;
    /**
     * The name of the user (label on the cloud)
     */
    @SerializedName("label")
    public String label;

    /**
     * Latitude of user's location
     */
    @SerializedName("latitude")
    @NotNull
    public String latitude;

    /**
     * Longitude of user's location
     */
    @SerializedName("longitude")
    @NotNull
    public String longitude;

    @JsonAdapter(TimestampAdapter.class)
    @SerializedName(value = "updated_at", alternate = "updatedAt")
    public long updatedAt = 0;

    /**
     * List of friends
     */
//    public List<User> friends;


//    public User(@NotNull String uniqueID, String label, @NotNull String latitude,
//                @NotNull String longitude, long updatedAt, List<User> friends) {
//        this.uniqueID = uniqueID;
//        this.label = label;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.updatedAt = 0;
////        this.friends = friends;
//    }

    public User(@NotNull String uniqueID, @NotNull String latitude, @NotNull String longitude) {
        this.uniqueID = uniqueID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = 0;
    }

    @Ignore
    public User(@NotNull String uniqueID, @NotNull String latitude, @NotNull String longitude, long updatedAt) {
        this.uniqueID = uniqueID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.updatedAt = updatedAt;
    }

    @Ignore
    public User() {
    }

    // For testing purpose
    public static List<User> loadJSON (Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<User>>(){}.getType();
            return gson.fromJson(reader,type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public static User fromJSON(String json) {
        return new Gson().fromJson(json, User.class);
    }

    @Override
    public String toString() {
        return "User{" +
                "uniqueID='" + uniqueID + '\'' +
                ", label='" + label + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
    /*
    @SuppressLint("SetTextI18n")
    private void checkMyStatus() {
        SharedPreferences preferences = this.getSharedPreferences("UUID", MODE_PRIVATE);
        String id = preferences.getString("myUUID","");
        ImageView offline = findViewById(R.id.offline_four_zoom);
        TextView status = findViewById(R.id.status_four_zoom);
        ImageView online = findViewById(R.id.online_four_zoom);

        var myUser = userViewModel.getUserLocal(id);
        if (myUser == null) {
            offline.setVisibility(View.INVISIBLE);
            status.setVisibility(View.INVISIBLE);
            online.setVisibility(View.INVISIBLE);
            return;
        }

        Log.d("MY USER", myUser.toString());
        Log.d("MY USER UPDATE AT", String.valueOf(myUser.updatedAt));

        long time = lastUpdate(myUser.updatedAt);

        if (time < 1) {
            offline.setVisibility(View.INVISIBLE);
            status.setVisibility(View.INVISIBLE);
            online.setVisibility(View.VISIBLE);
        } else {
            offline.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            online.setVisibility(View.INVISIBLE);

            int minute;
            int hour;
            hour = (int) time / 60;
            minute = (int) time % 60;

            if (hour < 1) {
                status.setText(minute + "m");
            } else {
                status.setText(hour + "h"
                        + minute + "m");
            }

        }
    }
     */
}