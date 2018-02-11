package com.ndlp.socialstudy.NewsFeed;

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


import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class RefreshNewsFeedFromDatabase {

    public String source;

    public String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/refreshNewsFeed.php";

    public RefreshNewsFeedFromDatabase(Context context, RecyclerView recyclerView, String source){
        this.source = source;
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

                            new TransformRefreshingNewsFeedData(context, jsonArray, recyclerView).execute();

                        } catch (JSONException e) {
                            Log.e(RefreshNewsFeedFromDatabase.class.getSimpleName(), e.getMessage());
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
                params.put("source", source);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
