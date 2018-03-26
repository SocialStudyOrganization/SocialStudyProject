package com.ndlp.socialstudy.Notifications;


import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class DateienAsyncTask extends AsyncTask<String, Integer, String> {

    private String subfolder, dateiname, categorie, kursid;

    public DateienAsyncTask(String dateiname, String categorie, String subfolder, String kursid){
        this.dateiname = dateiname;
        this.categorie = categorie;
        this.subfolder = subfolder;
        this.kursid = kursid;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("message",
                        "Eine neue Datei " + dateiname + " bei den " + subfolder + " in " + categorie + " wurde hochgeladen!")
                .add("title", "Neue Datei :)")
                .add("kursid", kursid)
                .add("clickaction", subfolder + "!" + categorie)
                .build();

        Request request = new Request.Builder()
                .url("http://h2774251.stratoserver.net/PHP-Dateien/Notifications/push_notificationclickaction.php")
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
