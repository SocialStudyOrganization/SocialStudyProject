package com.ndlp.socialstudy.GetScriptData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * class to handle the download/ actualize the data
 */

public class Downloader extends AsyncTask<Void,Void,String> {


    Context c;
    String urlAddress;
    RecyclerView rv_skripteInformatik;
    ProgressDialog pd;


    //  constructor
    public Downloader(Context c, String urlAddress, RecyclerView rv_skripteInformatik) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.rv_skripteInformatik = rv_skripteInformatik;
    }

    //  make loading ProgressDialog when searching on server for uploaded skripte
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setTitle("Retrieve");
        pd.setMessage("Retrieving..Please wait");
        pd.show();
    }


    @Override
    protected String doInBackground(Void... params) {
        return this.downloadData();
    }

    //  if error from php get toast else start data parser to handle the incoming data
    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);

        pd.dismiss();

        if(jsonData.startsWith("Error"))
        {
            Toast.makeText(c,"Unsuccessful "+jsonData, Toast.LENGTH_SHORT).show();
        }else
        {
            //PARSE
            new DataParser(c,jsonData,rv_skripteInformatik).execute();
        }
    }


    private String downloadData()
    {
        Object connection = Connector.connect(urlAddress);
        if(connection.toString().startsWith("Error"))
        {
            return connection.toString();
        }
        try {
            HttpURLConnection con= (HttpURLConnection) connection;

            InputStream is=new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;

            StringBuffer jsonData=new StringBuffer();

            while ((line=br.readLine()) != null)
            {
                jsonData.append(line+"n");
            }

            br.close();
            is.close();

            return jsonData.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();
        }
    }
}