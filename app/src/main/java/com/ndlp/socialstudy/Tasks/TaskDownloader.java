package com.ndlp.socialstudy.Tasks;

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



public class TaskDownloader {
    private String urlAddress;
    private String category;


    //  constructor
    public TaskDownloader(Context context, String urlAddress, RecyclerView rv_task, String category) {

        this.urlAddress = urlAddress;
        this.category = category;

        downloadData(context, rv_task);
    }


    private void downloadData(final Context context, final RecyclerView rv_task)
    {

        StringRequest requestTasks = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            new TaskDataParser(context, jsonArray, rv_task).execute();

                        } catch (JSONException e) {
                            Log.e(TaskDownloader.class.getSimpleName(), e.getMessage());
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
        requestQueue.add(requestTasks);
    }
}
