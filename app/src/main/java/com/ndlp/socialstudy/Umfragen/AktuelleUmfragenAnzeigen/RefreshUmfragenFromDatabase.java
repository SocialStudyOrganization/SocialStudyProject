package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;

public class RefreshUmfragenFromDatabase {

    public String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/Umfragen/refreshUmfragen.php";

    public RefreshUmfragenFromDatabase(Context context, RecyclerView recyclerView){
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

                            new TransformRefreshingUmfragenData(context, jsonArray, recyclerView).execute();

                        } catch (JSONException e) {
                            Log.e(RefreshUmfragenFromDatabase.class.getSimpleName(), e.getMessage());
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
