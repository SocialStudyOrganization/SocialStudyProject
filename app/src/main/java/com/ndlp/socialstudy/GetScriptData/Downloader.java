package com.ndlp.socialstudy.GetScriptData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * class to handle the download/ actualize the data
 */

public class Downloader {


   // private Context context;
    private String urlAddress;
    //private RecyclerView rv_skripte;
    private ProgressDialog pd;
    private String category;


    //  constructor
    public Downloader(Context context, String urlAddress, RecyclerView rv_skripte, String category) {
        //this.context = context;
        this.urlAddress = urlAddress;
        //this.rv_skripte = rv_skripte;
        this.category = category;

        downloadData(context, rv_skripte);
    }
//
//    //  make loading ProgressDialog when searching on server for uploaded skripte
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        pd = new ProgressDialog(context);
//        pd.setTitle("Retrieve");
//        pd.setMessage("Retrieving..Please wait");
//        pd.show();
//    }
//
//
//    @Override
//    protected String doInBackground(Void... params) {
//
//        downloadData(context, rv_skripte);
//        return jsonString;
//    }
//
//    //  if error from php get toast else start data parser to handle the incoming data
//    @Override
//    protected void onPostExecute(String jsonData) {
//        super.onPostExecute(jsonData);
//
//        pd.dismiss();
//
////        if(jsonData.startsWith("Error"))
////        {
////            Toast.makeText(context,"Unsuccessful "+jsonData, Toast.LENGTH_SHORT).show();
////        }else
////        {
////            //PARSE
////            downloadData(context, rv_skripte);
////        }
//
//        if(jsonString.startsWith("Error"))
//        {
//            Toast.makeText(context,"Unsuccessful "+jsonData, Toast.LENGTH_SHORT).show();
//        }else
//        {
//            //PARSE
//            if (jsonArray != null)
//                new DataParser(context, jsonArray, rv_skripte).execute();
//            else
//                Toast.makeText(context, "NullPointer JsonArray", Toast.LENGTH_SHORT).show();
//        }
//    }
//

    private void downloadData(final Context context, final RecyclerView rv_skripte)
    {

        StringRequest requestScripts = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            new DataParser(context, jsonArray, rv_skripte).execute();

                        } catch (JSONException e) {
                            Log.e(Downloader.class.getSimpleName(), e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("category", category);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(requestScripts);

//        Object connection = Connector.connect(urlAddress);
//        if(connection.toString().startsWith("Error"))
//        {
//            return connection.toString();
//        }
//        try {
//            HttpURLConnection con= (HttpURLConnection) connection;
//
//            InputStream is=new BufferedInputStream(con.getInputStream());
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//            String line;
//
//            StringBuffer jsonData=new StringBuffer();
//
//            while ((line=br.readLine()) != null)
//            {
//                jsonData.append(line+"n");
//            }
//
//            br.close();
//            is.close();
//
//            return jsonData.toString();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Error "+e.getMessage();
//        }
    }
}