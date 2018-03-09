package com.ndlp.socialstudy.GeneralFileFolder;

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
import com.ndlp.socialstudy.Skripte.IndividualSkripteRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * class to handle the download/ actualize the data
 */

public class RefreshfromDatabase {


    private String urlAddress;
    private String category;
    private String subFolder, kursid;
    private IndividualSkripteRecyclerAdapter individualSkripteRecyclerAdapter;


    //  constructor
    public RefreshfromDatabase(Context context, String urlAddress, RecyclerView recyclerView, String category, String subFolder, String kursid, IndividualSkripteRecyclerAdapter individualSkripteRecyclerAdapter) {
        this.kursid = kursid;
        this.urlAddress = urlAddress;
        this.category = category;
        this.subFolder = subFolder;
        this.individualSkripteRecyclerAdapter = individualSkripteRecyclerAdapter;

        downloadData(context, recyclerView, subFolder);
    }


    private void downloadData(final Context context, final RecyclerView recyclerView, final String subFolder)
    {

        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            Log.i("json Array: ", jsonArray.toString());

                            new TransformRefreshingData(context, jsonArray, recyclerView, subFolder, individualSkripteRecyclerAdapter).execute();

                        } catch (JSONException e) {
                            Log.e(RefreshfromDatabase.class.getSimpleName(), e.getMessage());
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
                params.put("kursid", kursid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}