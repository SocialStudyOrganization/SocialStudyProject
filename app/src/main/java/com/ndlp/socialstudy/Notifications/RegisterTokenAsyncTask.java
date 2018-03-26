package com.ndlp.socialstudy.Notifications;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class RegisterTokenAsyncTask extends AsyncTask<String, Integer, String> {

    private Integer matrikelnummer;
    private String token;

    public RegisterTokenAsyncTask(String token, Integer matrikelnummer){
        this.token = token;
        this.matrikelnummer = matrikelnummer;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .add("Matrikelnummer", matrikelnummer+"")
                .build();

        Request request = new Request.Builder()
                .url("http://h2774251.stratoserver.net/PHP-Dateien/Notifications/TokenEintragen.php")
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "useles return statement";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}

