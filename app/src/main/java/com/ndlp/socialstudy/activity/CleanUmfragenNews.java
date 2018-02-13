package com.ndlp.socialstudy.activity;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen.DeleteUmfrage;


import org.json.JSONException;
import org.json.JSONObject;


import java.security.spec.ECField;
import java.util.HashMap;
import java.util.Map;


public class CleanUmfragenNews {

    Context context;

    public CleanUmfragenNews(Context context) {

        this.context = context;

    }

    public void deleteoldDataonServer(){

        String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/ServerBereinigen/deleteolddate.php";

        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("response", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if (success) {

                                try {
                                    String topic = jsonResponse.getString("topic");

                                    DeleteUmfrage deleteUmfrage = new DeleteUmfrage(context, topic);

                                }catch(Exception e){
                                    Log.i("exception", e.toString());
                                }


                            } else {
                                Toast.makeText(context, "Failed to clean database", Toast.LENGTH_SHORT).show();
                                Log.i("error", jsonResponse.getString("error_msg"));

                            }



                        } catch (JSONException e) {
                            Toast.makeText(context, "Failed to clean database", Toast.LENGTH_SHORT).show();
                            Log.i("errorexception", e.toString());
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(context, "Failed to clean database", Toast.LENGTH_SHORT).show();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("random", "random");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
