package com.ndlp.socialstudy.NewsFeed;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class TransformRefreshingNewsFeedData extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private JSONArray jsonArray;
    private RecyclerView recyclerView;
    private ProgressDialog pd;
    private ArrayList<NewsFeedObject> newsFeedObjectArrayList = new ArrayList<>();
    private NewsFeedRecyclerAdapter newsFeedRecyclerAdapter = new NewsFeedRecyclerAdapter(context, newsFeedObjectArrayList);

    public TransformRefreshingNewsFeedData(Context context, JSONArray jsonArray, RecyclerView recyclerView){
        this.context = context;
        this.jsonArray = jsonArray;
        this.recyclerView = recyclerView;
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

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        newsFeedRecyclerAdapter.setContext(context);
        newsFeedRecyclerAdapter.setNewsFeedList(newsFeedObjectArrayList);
        recyclerView.setAdapter(newsFeedRecyclerAdapter);
    }


    private Boolean parseData(){

        try {
            JSONObject jo;
            newsFeedObjectArrayList.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                jo = jsonArray.getJSONObject(i);
                NewsFeedObject newsFeedObject = new NewsFeedObject( jo.getString("matrikelnummer"), jo.getString("category")
                        , jo.getString("uploaddate"), jo.getString("uploadtime") ,jo.getString("topic"), jo.getString("message"));
                newsFeedObjectArrayList.add(newsFeedObject);
                newsFeedRecyclerAdapter.notifyDataSetChanged();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

}
