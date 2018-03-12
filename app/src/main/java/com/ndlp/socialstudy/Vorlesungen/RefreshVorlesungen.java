package com.ndlp.socialstudy.Vorlesungen;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;



public class RefreshVorlesungen {

    private String urlAddress, kursid, subfolder;
    private Context context;
    private VorlesungenRecyclerAdapter vorlesungenRecyclerAdapter;

    public RefreshVorlesungen (Context context, String kursid, RecyclerView recyclerView, VorlesungenRecyclerAdapter vorlesungenRecyclerAdapter, String urlAddress, String subfolder){
        this.context = context;
        this.kursid = kursid;
        this.urlAddress = urlAddress;
        this.vorlesungenRecyclerAdapter = vorlesungenRecyclerAdapter;
        this.subfolder = subfolder;

        downloadData(recyclerView);

    }

    private void downloadData(final RecyclerView recyclerView)
    {

        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {

                            Log.i("response", response.toString());

                            JSONArray jsonArray = new JSONArray(response);



                            Log.i("json Array: ", jsonArray.toString());

                            new TransformRefreshingVorlesungen(context, jsonArray, recyclerView, vorlesungenRecyclerAdapter).execute();

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
                params.put("kursid", kursid);
                params.put("subfolder", subfolder);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
