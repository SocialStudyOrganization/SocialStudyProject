package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TransformRefreshingUmfragenData extends AsyncTask<Void,Void,Boolean> {

    private Context context;
    private JSONArray jsonArray;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    private ArrayList<GeneralObject> umfragenarraylist = new ArrayList<>();
    private BasicUmfragenRecyclerAdapter basicUmfragenRecyclerAdapter;



    public TransformRefreshingUmfragenData (Context context, JSONArray jsonArray, RecyclerView recyclerView, BasicUmfragenRecyclerAdapter basicUmfragenRecyclerAdapter){
        this.context = context;
        this.jsonArray = jsonArray;
        this.recyclerView = recyclerView;
        this.basicUmfragenRecyclerAdapter = basicUmfragenRecyclerAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setTitle("Parse");
        pd.setMessage("Loading your polls from the server...");
        pd.show();
    }



    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        pd.dismiss();

        basicUmfragenRecyclerAdapter.setUmfragenList(umfragenarraylist);
        basicUmfragenRecyclerAdapter.notifyDataSetChanged();
    }

    private Boolean parseData(){

        try {
            JSONObject jo;
            umfragenarraylist.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                jo = jsonArray.getJSONObject(i);
                GeneralObject generalObject = new GeneralObject( "Umfrage", jo.getString("Matrikelnummer")
                        , jo.getString("Enddate"), jo.getString("Endtime") ,jo.getString("SurveyTitle"), jo.getString("einzelantwort")
                , jo.getInt("Survey_ID"));
                umfragenarraylist.add(generalObject);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    return false;
    }


}
