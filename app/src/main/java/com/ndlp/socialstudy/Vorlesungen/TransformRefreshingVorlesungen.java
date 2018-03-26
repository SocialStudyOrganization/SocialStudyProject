package com.ndlp.socialstudy.Vorlesungen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ndlp on 11.03.2018.
 */

public class TransformRefreshingVorlesungen extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private JSONArray jsonData;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    private ArrayList<VorlesungenObject> vorlesungenObjects = new ArrayList<>();
    private VorlesungenRecyclerAdapter vorlesungenRecyclerAdapter;


    //  Constructor
    public TransformRefreshingVorlesungen(Context context, JSONArray jsonData, RecyclerView recyclerView, VorlesungenRecyclerAdapter vorlesungenRecyclerAdapter) {
        this.context = context;
        this.jsonData = jsonData;
        this.recyclerView = recyclerView;
        this.vorlesungenRecyclerAdapter = vorlesungenRecyclerAdapter;
    }

    //  shows progressDialog to actualize data displayed
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setTitle("Parse");
        pd.setMessage("Loading your Files from the server...");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return parseData();
    }

    //  if result true handle set recyclerView
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        pd.dismiss();


        if (result) {


            vorlesungenRecyclerAdapter.setVorlesungsList(vorlesungenObjects);
            vorlesungenRecyclerAdapter.notifyDataSetChanged();


        }
    }


    //  transform the given jsonData and get an scriptObject out of every skript
    private Boolean parseData() {



        try {


            JSONObject jo;
            vorlesungenObjects.clear();
            for (int i = 0; i < jsonData.length(); i++) {
                jo = jsonData.getJSONObject(i);
                VorlesungenObject vorlesungenObject = new VorlesungenObject(jo.getString("Vorlesungsbezeichnung"), jo.getString("vorlesungs_ID"));
                vorlesungenObjects.add(vorlesungenObject);

            }
            return true;
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        return false;

    }
}
