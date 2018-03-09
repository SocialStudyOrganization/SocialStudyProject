package com.ndlp.socialstudy.GeneralFileFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.ndlp.socialstudy.Skripte.IndividualSkripteRecyclerAdapter;
import com.ndlp.socialstudy.Skripte.SkripteObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * transforms the given data and sends it to the recyclerview
 */


public class TransformRefreshingData extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private String subFolder;
    private JSONArray jsonData;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    private ArrayList<SkripteObject> skriptarrayList = new ArrayList<>();
    private IndividualSkripteRecyclerAdapter individualSkripteRecyclerAdapter;


    //  Constructor
    public TransformRefreshingData(Context context, JSONArray jsonData, RecyclerView recyclerView, String subFolder, IndividualSkripteRecyclerAdapter individualSkripteRecyclerAdapter) {
        this.context = context;
        this.jsonData = jsonData;
        this.recyclerView = recyclerView;
        this.subFolder = subFolder;
        this.individualSkripteRecyclerAdapter = individualSkripteRecyclerAdapter;
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


            individualSkripteRecyclerAdapter.setScriptList(skriptarrayList);
            individualSkripteRecyclerAdapter.setSubFolder(subFolder);
            individualSkripteRecyclerAdapter.notifyDataSetChanged();


        }
    }


    //  transform the given jsonData and get an scriptObject out of every skript
    private Boolean parseData() {

        String fileName = null;
        String id = null;

        if (subFolder.equals("Skripte")) {

            fileName = "skriptname";
            id = "skript_id";
        } else if (subFolder.equals("Tasks")) {

            fileName = "taskname";
            id = "task_id";
        } else if (subFolder.equals("Answers")) {

            fileName = "answername";
            id = "answer_id";
        }


        try {

            if (fileName == null || id == null)
                throw new NullPointerException("Fehler bei der JSON Datentransformation");

            JSONObject jo;
            skriptarrayList.clear();
            for (int i = 0; i < jsonData.length(); i++) {
                jo = jsonData.getJSONObject(i);
                SkripteObject scriptObject = new SkripteObject(jo.getInt(id), jo.getString(fileName)
                        , jo.getString("format"), jo.getString("category"), jo.getString("date"), jo.getString("user"));
                skriptarrayList.add(scriptObject);

            }
            return true;
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }

        return false;

    }
}