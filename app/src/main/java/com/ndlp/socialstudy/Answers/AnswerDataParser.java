package com.ndlp.socialstudy.Answers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class AnswerDataParser extends AsyncTask<Void,Void,Boolean> {


    private Context context;
    private JSONArray jsonData;
    private RecyclerView rv_answer;
    private ProgressDialog pd;
    private ArrayList<AnswerObject> arrayList = new ArrayList<>();
    private AnswerRecyclerAdapter mRecyclerAdapter = new AnswerRecyclerAdapter();

    //  Constructor
    public AnswerDataParser(Context context, JSONArray jsonData, RecyclerView rv_answer) {
        this.context = context;
        this.jsonData = jsonData;
        this.rv_answer = rv_answer;
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
            rv_answer.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerAdapter.setContext(context);
            mRecyclerAdapter.setAnswerList(arrayList);
            rv_answer.setAdapter(mRecyclerAdapter);
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
                AnswerObject answerObject = new AnswerObject(jo.getInt("answer_id"),jo.getString("answername")
                        , jo.getString("format"), jo.getString("category"), jo.getString("date"), jo.getString("user"));
                arrayList.add(answerObject);
                mRecyclerAdapter.notifyDataSetChanged();
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
