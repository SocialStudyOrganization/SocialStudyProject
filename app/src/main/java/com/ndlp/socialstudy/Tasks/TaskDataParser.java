package com.ndlp.socialstudy.Tasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TaskDataParser extends AsyncTask<Void,Void,Boolean> {

    private Context context;
    private JSONArray jsonData;
    private RecyclerView rv_task;
    private ProgressDialog pd;
    private ArrayList<TaskObject> arrayList = new ArrayList<>();
    private TaskRecyclerAdapter mRecyclerAdapter = new TaskRecyclerAdapter();

    //  Constructor
    public TaskDataParser(Context context, JSONArray jsonData, RecyclerView rv_task) {
        this.context = context;
        this.jsonData = jsonData;
        this.rv_task = rv_task;
    }

    //  shows progressDialog to actualize data displayed
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(context);
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
            rv_task.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerAdapter.setContext(context);
            mRecyclerAdapter.setTaskList(arrayList);
            rv_task.setAdapter(mRecyclerAdapter);
        }
    }


    //  transform the given jsonData and get an Object out of every
    private Boolean parseData()
    {
        try
        {
            JSONObject jo;
            arrayList.clear();
            for (int i = 0; i < jsonData.length(); i++) {
                jo = jsonData.getJSONObject(i);
                TaskObject taskObject = new TaskObject(jo.getInt("task_id"),jo.getString("taskname")
                        , jo.getString("format"), jo.getString("category"), jo.getString("date"), jo.getString("user"));
                arrayList.add(taskObject);
                mRecyclerAdapter.notifyDataSetChanged();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
