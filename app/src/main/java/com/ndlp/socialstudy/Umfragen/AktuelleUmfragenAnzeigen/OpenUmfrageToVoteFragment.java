package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.GeneralFileFolder.RefreshfromDatabase;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Umfragen.UmfrageErstellen.NewUmfrageActivity;
import com.ndlp.socialstudy.Umfragen.UmfrageErstellen.Wortumfragenobject;
import com.ndlp.socialstudy.activity.TinyDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;


public class OpenUmfrageToVoteFragment extends Fragment {
    public static OpenUmfrageToVoteFragment newInstance() {
        OpenUmfrageToVoteFragment openUmfrageToVoteFragment = new OpenUmfrageToVoteFragment();
        return openUmfrageToVoteFragment;
    }

    //--------------------Variablendeklaration-----------------------------------------

    Integer currentpageint = 1;
    Integer umfang;
    TextView tv_topic;
    TextView tv_frage;
    TextView tv_progress;

    ArrayList<UmfrageAnzeigenObject> umfrageAnzeigenObjects;
    UmfrageAnzeigenRecyclerAdapter umfrageAnzeigenRecyclerAdapter;
    RecyclerView recyclerView;

    View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_open_umfrage_to_vote, container, false);

        Button b_einreichen = (Button) rootView.findViewById(R.id.umfrageanzeigeneinsenden);
        ImageView iv_next = (ImageView) rootView.findViewById(R.id.umfrageanzeigennächstefrage);
        ImageView iv_previous = (ImageView) rootView.findViewById(R.id.umfrageanzeigenvorherigefrage);
        tv_topic = (TextView) rootView.findViewById(R.id.tv_umfrageanzeigentopic);
        tv_frage = (TextView) rootView.findViewById(R.id.tv_umfrageanzeigenfrage);
        tv_progress = (TextView) rootView.findViewById(R.id.umfrageanzeigenprogress);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_umfrageanzeigenrecyclerview);

        umfrageAnzeigenObjects = new ArrayList<>();
        umfrageAnzeigenObjects.clear();
        umfrageAnzeigenRecyclerAdapter =
                new UmfrageAnzeigenRecyclerAdapter(getContext(),umfrageAnzeigenObjects);

        recyclerView.setAdapter(umfrageAnzeigenRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TinyDB tinyDB = new TinyDB(getContext());

        umfang = tinyDB.getInt("umfang");

        getData(currentpageint);

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentpageint == umfang) {
                    Toast.makeText(getContext(), "There is no question after this one", Toast.LENGTH_LONG).show();
                }else{
                    currentpageint = currentpageint + 1;
                    umfrageAnzeigenObjects.clear();
                    umfrageAnzeigenRecyclerAdapter.notifyDataSetChanged();
                    getData(currentpageint);
                }

            }
        });

        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentpageint == 1) {
                    Toast.makeText(getContext(), "There is no question before this one", Toast.LENGTH_LONG).show();
                }else{
                    currentpageint = currentpageint - 1;
                    umfrageAnzeigenObjects.clear();
                    umfrageAnzeigenRecyclerAdapter.notifyDataSetChanged();
                    getData(currentpageint);

                }


            }
        });

        b_einreichen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> checked_items = new ArrayList<String>();
                checked_items = umfrageAnzeigenRecyclerAdapter.passArrayToActivity();
                ArrayList<Integer> checked_ids = new ArrayList<Integer>();
                checked_ids = umfrageAnzeigenRecyclerAdapter.passIdsToActivity();

                Log.i("Ausgewählte Antworten:", checked_items + "");
                Log.i("Ausgewählte Antworten:", checked_ids + "");

                String checked_idsString = checked_ids + "";

                uploadVote(checked_idsString);


            }
        });

        return rootView;


    }

    public void getData(Integer currentpageint){
        TinyDB tinyDB = new TinyDB(getContext());
        String question = tinyDB.getString("question" + currentpageint);
        ArrayList<String> answers = new ArrayList<>();
        ArrayList<Integer> answerIDs = new ArrayList<>();
        answers = tinyDB.getListString("answers" + currentpageint);
        answerIDs = tinyDB.getListInt("answerIDs" + currentpageint);

        Log.i("answers", answers + "");
        Log.i("answerIDs", answerIDs + "");


        tv_topic.setText(tinyDB.getString("topic"));
        tv_frage.setText(question);
        tv_progress.setText(currentpageint + "/" + umfang);

        String singleanswer;
        Integer singleanswerID;
        Integer counter;

        for (counter = 0; counter < answers.size(); counter++){
            singleanswer = answers.get(counter);
            singleanswerID = answerIDs.get(counter);
            Log.i("sngleanswer", singleanswer);
            Log.i("singleanswerID", singleanswerID + "");
            UmfrageAnzeigenObject umfrageAnzeigenObject = new UmfrageAnzeigenObject(singleanswer, singleanswerID);
            umfrageAnzeigenObjects.add(umfrageAnzeigenObject);
            umfrageAnzeigenRecyclerAdapter.notifyDataSetChanged();
        }
    }

    public void uploadVote(final String checked_idsString){


        String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/VoteIntoDatabase.php";


        StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        try {
                            Log.i("tagconvertstr", response);
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");


                            if (success) {

                                if (currentpageint == umfang) {
                                    Toast.makeText(getContext(), "Vielen Dank für Ihre Teilnahme!", Toast.LENGTH_LONG).show();
                                    umfrageAnzeigenRecyclerAdapter.deleteArray();

                                    //close fragment TODO

                                }else{

                                    currentpageint = currentpageint + 1;
                                    umfrageAnzeigenObjects.clear();
                                    umfrageAnzeigenRecyclerAdapter.notifyDataSetChanged();
                                    getData(currentpageint);
                                    umfrageAnzeigenRecyclerAdapter.deleteArray();
                                }
                                Toast.makeText(getContext(), jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                            } else {

                                Toast.makeText(getContext(), jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            Log.e(RefreshfromDatabase.class.getSimpleName(), e.getMessage());

                        }
                    }


                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Integer userint;
                SharedPreferences sharedPrefLoginData = getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                userint = sharedPrefLoginData.getInt("matrikelnummer", 1);
                String user = userint.toString();

                Map<String, String> params = new HashMap<>();
                params.put("user", user);
                params.put("answerIDs", checked_idsString);
                Log.i("answerIDs zum Server: " , checked_idsString);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

}

