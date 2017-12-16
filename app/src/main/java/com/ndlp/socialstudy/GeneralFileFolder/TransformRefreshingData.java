package com.ndlp.socialstudy.GeneralFileFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ndlp.socialstudy.Skripte.IndividualSkripteRecyclerAdapter;
import com.ndlp.socialstudy.Skripte.SkripteObject;
import com.ndlp.socialstudy.Tasks.IndividualTasksRecyclerAdapter;
import com.ndlp.socialstudy.Tasks.TaskObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * transforms the given data and sends it to the recyclerview
 */


public class TransformRefreshingData extends AsyncTask<Void,Void,Boolean> {

    private Context context;
    private String subFolder;
    private JSONArray jsonData;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    private ArrayList<SkripteObject> skriptarrayList = new ArrayList<>();
    private ArrayList<TaskObject> taskarrayList = new ArrayList<>();

    private IndividualSkripteRecyclerAdapter skripteRecyclerAdapter = new IndividualSkripteRecyclerAdapter();
    private IndividualTasksRecyclerAdapter tasksRecyclerAdapter = new IndividualTasksRecyclerAdapter();

    //  Constructor
    public TransformRefreshingData(Context context, JSONArray jsonData, RecyclerView recyclerView, String subFolder) {
        this.context = context;
        this.jsonData = jsonData;
        this.recyclerView = recyclerView;
        this.subFolder = subFolder;
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

            if (subFolder=="Skripte"){
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                skripteRecyclerAdapter.setContext(context);
                skripteRecyclerAdapter.setScriptList(skriptarrayList);
                skripteRecyclerAdapter.setSubFolder(subFolder);
                recyclerView.setAdapter(skripteRecyclerAdapter);
            }

            if (subFolder == "Tasks"){
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                tasksRecyclerAdapter.setContext(context);
                tasksRecyclerAdapter.setTaskList(taskarrayList);
                tasksRecyclerAdapter.setSubFolder(subFolder);
                recyclerView.setAdapter(tasksRecyclerAdapter);
            }

        }
    }


    //  transform the given jsonData and get an scriptObject out of every skript
    private Boolean parseData() {

        if (subFolder == "Skripte") {
            try {
                JSONObject jo;
                skriptarrayList.clear();
                for (int i = 0; i < jsonData.length(); i++) {
                    jo = jsonData.getJSONObject(i);
                    SkripteObject scriptObject = new SkripteObject(jo.getInt("skript_id"), jo.getString("skriptname")
                            , jo.getString("format"), jo.getString("category"), jo.getString("date"), jo.getString("user"));
                    skriptarrayList.add(scriptObject);
                    skripteRecyclerAdapter.notifyDataSetChanged();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (subFolder == "Tasks") {
            try {
                JSONObject jo;
                taskarrayList.clear();
                for (int i = 0; i < jsonData.length(); i++) {
                    jo = jsonData.getJSONObject(i);
                    TaskObject taskObject = new TaskObject(jo.getInt("task_id"), jo.getString("taskname")
                            , jo.getString("format"), jo.getString("category"), jo.getString("date"), jo.getString("user"));
                    taskarrayList.add(taskObject);
                    tasksRecyclerAdapter.notifyDataSetChanged();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return false;

    }
}