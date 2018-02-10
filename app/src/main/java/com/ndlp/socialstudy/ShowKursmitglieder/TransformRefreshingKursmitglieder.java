package com.ndlp.socialstudy.ShowKursmitglieder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class TransformRefreshingKursmitglieder extends AsyncTask<Void,Void,Boolean> {

    private Context context;
    private JSONArray jsonData;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    private ArrayList<KursmitgliederObject> kursmitgliederObjectArrayList = new ArrayList<>();

    private KursmitgliederRecyclerAdapter kursmitgliederRecyclerAdapter = new KursmitgliederRecyclerAdapter();

    //  Constructor
    public TransformRefreshingKursmitglieder(Context context, JSONArray jsonData, RecyclerView recyclerView) {
        this.context = context;
        this.jsonData = jsonData;
        this.recyclerView = recyclerView;
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
        return this.parseData();
    }

    //  if result true handle set recyclerView
    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        pd.dismiss();


        if (result) {


                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                kursmitgliederRecyclerAdapter.setContext(context);
                kursmitgliederRecyclerAdapter.setScriptList(kursmitgliederObjectArrayList);
                recyclerView.setAdapter(kursmitgliederRecyclerAdapter);


        }
    }



    private Boolean parseData() {

            try {
                JSONObject jo;
                kursmitgliederObjectArrayList.clear();
                for (int i = 0; i < jsonData.length(); i++) {
                    jo = jsonData.getJSONObject(i);
                    KursmitgliederObject kursmitgliederObject = new KursmitgliederObject(jo.getInt("Matrikelnummer"), jo.getString("email")
                            , jo.getString("firstName"), jo.getString("surname"));
                    kursmitgliederObjectArrayList.add(kursmitgliederObject);
                    kursmitgliederRecyclerAdapter.notifyDataSetChanged();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        return false;

    }
}

