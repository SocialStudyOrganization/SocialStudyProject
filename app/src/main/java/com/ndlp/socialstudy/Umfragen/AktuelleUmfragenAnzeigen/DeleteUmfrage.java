package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;


import android.content.Context;


import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;


public class DeleteUmfrage {

    String topic;
    private Integer survey_id;
    private String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/Umfragen/DeleteUmfrage.php";

    public DeleteUmfrage (Context context, Integer survey_id){
        this.survey_id = survey_id;

        deleteUmfrage(context, survey_id);
    }

    private void deleteUmfrage(final Context context, Integer survey_id)
    {
        final String surveyidstring = survey_id+"";

        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if (success) {
                                Toast.makeText(context, jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                            } else {

                                Toast.makeText(context, jsonResponse.getString("error_msg"), LENGTH_LONG).show();
                            }
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
                params.put("surveyid", surveyidstring);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
