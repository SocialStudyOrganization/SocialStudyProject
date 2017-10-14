package com.ndlp.socialstudy.GetScriptData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ndlp.socialstudy.Skripte.ScriptObject;
import com.ndlp.socialstudy.Skripte.ScriptRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * transforms the given data and sends it to the recyclerview
 */


public class DataParser extends AsyncTask<Void,Void,Boolean> {

    private Context c;
    private String jsonData;
    private RecyclerView rv_skripteInformatik;
    private ProgressDialog pd;
    private ArrayList<ScriptObject> arrayList = new ArrayList<>();
    private ScriptRecyclerAdapter mRecyclerAdapter = new ScriptRecyclerAdapter();

    //  Constructor
    public DataParser(Context c, String jsonData, RecyclerView rv_skripteInformatik) {
        this.c = c;
        this.jsonData = jsonData;
        this.rv_skripteInformatik = rv_skripteInformatik;
    }

    //  shows progressDialog to actualize data displayed
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Pasring..Please wait");
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
            rv_skripteInformatik.setLayoutManager(new LinearLayoutManager(c));
            mRecyclerAdapter.setContext(c);
            mRecyclerAdapter.setScriptList(arrayList);
            rv_skripteInformatik.setAdapter(mRecyclerAdapter);
        }
    }


    //  transform the given jsonData and get an scriptObject out of every skript
    private Boolean parseData()
    {
        try
        {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo;
            arrayList.clear();
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                ScriptObject scriptObject = new ScriptObject(jo.getInt("skript_id"),jo.getString("skriptname")
                ,jo.getString("date"), jo.getString("user"));
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