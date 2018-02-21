package com.ndlp.socialstudy.Notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String REG_TOKEN = "REG_TOKEN";



    //gets called when app is new installed
    @Override
    public void onTokenRefresh() {

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN, recent_token);

        registerToken(recent_token);
    }

    private void registerToken(String token){

        Integer matrikelnummer;
        SharedPreferences sharedPrefLoginData = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        matrikelnummer = sharedPrefLoginData.getInt("matrikelnummer", 1);

        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .add("Matrikelnummer", matrikelnummer+"")
                .build();

        Request request = new Request.Builder()
                .url("http://hellownero.de/SocialStudy/PHP-Dateien/Notifications/TokenEintragen.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
