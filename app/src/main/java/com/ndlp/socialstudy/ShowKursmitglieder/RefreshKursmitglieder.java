package com.ndlp.socialstudy.ShowKursmitglieder;

import android.content.Context;
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
import com.ndlp.socialstudy.GeneralFileFolder.RefreshfromDatabase;
import com.ndlp.socialstudy.GeneralFileFolder.TransformRefreshingData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class RefreshKursmitglieder {

    private String urlAddress;

    //  constructor
    public RefreshKursmitglieder(Context context, String urlAddress, RecyclerView recyclerView) {

        this.urlAddress = urlAddress;
        downloadData(context, recyclerView);
    }


    private void downloadData(final Context context, final RecyclerView recyclerView)
    {

        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            new TransformRefreshingKursmitglieder(context, jsonArray, recyclerView).execute();

                        } catch (JSONException e) {
                            Log.e(RefreshKursmitglieder.class.getSimpleName(), e.getMessage());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){


        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
