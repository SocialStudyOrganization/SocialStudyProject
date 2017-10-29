package com.ndlp.socialstudy.Skripte;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * transforms the given data and sends it to the recyclerview
 */


public class ScripteDataParser extends AsyncTask<Void,Void,Boolean> {

    private Context context;
    private String subFolder;
    private JSONArray jsonData;
    private RecyclerView rv_skripte;
    private ProgressDialog pd;
    private ArrayList<ItemObject> arrayList = new ArrayList<>();
    private ScriptRecyclerAdapter mRecyclerAdapter = new ScriptRecyclerAdapter();

    //  Constructor
    public ScripteDataParser(Context context, JSONArray jsonData, RecyclerView rv_skripte, String subFolder) {
        this.context = context;
        this.jsonData = jsonData;
        this.rv_skripte = rv_skripte;
        this.subFolder = subFolder;
    }

    //  shows progressDialog to actualize data displayed
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(context);
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
        if(result)
        {
            rv_skripte.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerAdapter.setContext(context);
            mRecyclerAdapter.setScriptList(arrayList);
            mRecyclerAdapter.setSubFolder(subFolder);
            rv_skripte.setAdapter(mRecyclerAdapter);
        }
    }


    //  transform the given jsonData and get an scriptObject out of every skript
    private Boolean parseData()
    {
        try
        {
            JSONObject jo;
            arrayList.clear();
            for (int i = 0; i < jsonData.length(); i++) {
                jo = jsonData.getJSONObject(i);
                ItemObject scriptObject = new ItemObject(jo.getInt("skript_id"),jo.getString("skriptname")
                , jo.getString("format"), jo.getString("category"), jo.getString("date"), jo.getString("user"));
                arrayList.add(scriptObject);
                mRecyclerAdapter.notifyDataSetChanged();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}