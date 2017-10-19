package com.ndlp.socialstudy.Answers;

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



public class AnswerDownloader {
    private String urlAddress;
    private String category;


    //  constructor
    public AnswerDownloader(Context context, String urlAddress, RecyclerView rv_answer, String category) {

        this.urlAddress = urlAddress;
        this.category = category;

        downloadData(context, rv_answer);
    }


    private void downloadData(final Context context, final RecyclerView rv_answer)
    {

        StringRequest requestAnswers = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONArray jsonArray = new JSONArray(response);

                            new AnswerDataParser(context, jsonArray, rv_answer).execute();

                        } catch (JSONException e) {
                            Log.e(com.ndlp.socialstudy.Answers.AnswerDownloader.class.getSimpleName(), e.getMessage());
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
        requestQueue.add(requestAnswers);
    }
}

