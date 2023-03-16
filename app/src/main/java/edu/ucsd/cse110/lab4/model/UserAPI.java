package edu.ucsd.cse110.lab4.model;

import android.util.Log;

import java.time.Instant;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UserAPI {

    private volatile static UserAPI instance = null;
    private OkHttpClient client;
    private UserDao userDao;

    public UserAPI() {
        this.client = new OkHttpClient();
    }

    public static UserAPI provide() {
        if (instance == null) {
            instance = new UserAPI();
        }
        return instance;
    }

    public User getByPublicCode (String public_code) {
        public_code = public_code.replace(" ", "%20");
        Request request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + public_code)
                .method("GET", null)
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String body = response.body().string();
            Log.i("GET BY PUBLIC CODE", body);
            return User.fromJSON(body);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new User(public_code," ", "");
    }

    public void putByPublicCode (User user) {
        //URLs cannot contain spaces, so we replace them with %20.
        String public_code = user.uniqueID;
        public_code = public_code.replace(" ", "%20");
        String json = "{\"private_code\":\"" + UUID.randomUUID().toString().substring(0,12)
                + "\",\"label\":\""  + user.label
                + "\",\"latitude\":\"" + user.latitude
                + "\",\"longitude\":\"" + user.longitude + "\"}";

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("https://socialcompass.goto.ucsd.edu/location/" + public_code)
                .method("PUT", body)
                .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String result = response.body().string();
            Log.i("PUT BY PUBLIC CODE", result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
