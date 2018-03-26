package com.ndlp.socialstudy.Notifications;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;



public class UmfragenAsyncTask  extends AsyncTask<String, Integer, String> {

    private String name, kursid;

    public UmfragenAsyncTask(String name, String kursid){
        this.name = name;
        this.kursid = kursid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("message", "Thema: " + name)
                .add("title", "Neue Umfrage :)")
                .add("kursid", kursid)
                .add("clickaction", "BasicUmfragenFragment")
                .build();

        Request request = new Request.Builder()
                .url("http://hellownero.de/SocialStudy/PHP-Dateien/Notifications/push_notificationclickaction.php")
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
