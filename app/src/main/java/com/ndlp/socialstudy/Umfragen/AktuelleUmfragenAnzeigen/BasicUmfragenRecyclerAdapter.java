package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.LoginSystem.RegisterActivity;
import com.ndlp.socialstudy.LoginSystem.RegisterRequest;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Umfragen.UmfrageAuswerten.UmfrageAuswertenFragment;
import com.ndlp.socialstudy.Umfragen.UmfrageErstellen.NewUmfrageActivity;
import com.ndlp.socialstudy.activity.TinyDB;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class BasicUmfragenRecyclerAdapter extends RecyclerView.Adapter<BasicUmfragenRecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GeneralObject> data;

    public BasicUmfragenRecyclerAdapter(Context context, ArrayList<GeneralObject> data){
        this.context = context;
        this.data = data;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Object Constructor
    public void setUmfragenList(ArrayList<GeneralObject> generalObjects) {
        this.data = generalObjects;
    }

    @Override
    public BasicUmfragenRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.umfrage_item, null);

        return new BasicUmfragenRecyclerAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final BasicUmfragenRecyclerAdapter.MyViewHolder holder, final int position) {

        final GeneralObject current = data.get(position);

        String user = current.getUser();
        String enddate = current.getEnddate();
        String endtime = current.getEndtime();

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.header.setTypeface(quicksand_regular);
        holder.topic.setTypeface(quicksand_regular);
        holder.more.setTypeface(quicksand_regular);

        holder.header.setText(user + ", gültig bis: " + enddate + ", " + endtime + " Uhr");
        holder.topic.setText(current.getTopic());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/GetUmfrageToVote.php";

                StringRequest request = new StringRequest(Request.Method.POST, urlAddress,

                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                try {
                                    Log.i("tagconvertstr", response);
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");


                                    if (success) {

                                        Toast.makeText(context, jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                                        TinyDB tinyDB = new TinyDB(context);

                                        tinyDB.remove("umfang");
                                        tinyDB.remove("topic");
                                        tinyDB.remove("teilnehmerzahl");

                                        Integer deletezaehler;

                                        for (deletezaehler = 1; deletezaehler<=10; deletezaehler++){
                                            tinyDB.remove("answers" + deletezaehler);
                                            tinyDB.remove("question" + deletezaehler);
                                            tinyDB.remove("answerIDs" + deletezaehler);
                                        }



                                        tinyDB.putInt("umfang", jsonResponse.getInt("umfang"));
                                        Integer umfang = jsonResponse.getInt("umfang");
                                        String teilnehmerzahl = jsonResponse.getString("teilnehmerzahl");
                                        tinyDB.putString("teilnehmerzahl", teilnehmerzahl);

                                        Integer zaehler;
                                        ArrayList<String> antworten = new ArrayList<>();
                                        ArrayList<Integer> answerIDs = new ArrayList<>();

                                        for(zaehler = 1; zaehler<=umfang; zaehler++){
                                            JSONArray jArray = jsonResponse.getJSONArray("answers" + zaehler);
                                            JSONArray jArrayIDs = jsonResponse.getJSONArray("answerIDs" + zaehler);
                                            if (jArray != null) {
                                                for (int i=0;i<jArray.length();i++){
                                                    antworten.add(jArray.getString(i));
                                                }
                                                tinyDB.putListString("answers" + zaehler, antworten);
                                                tinyDB.putString("question" + zaehler, jsonResponse.getString("question" + zaehler));
                                                tinyDB.putString("topic", current.getTopic().toString());

                                                Log.i("question" + zaehler, tinyDB.getString("question" + zaehler));
                                                Log.i("answers" + zaehler, tinyDB.getListString("answers" + zaehler).toString());
                                            }
                                            if (jArrayIDs != null) {
                                                for (int i=0;i<jArrayIDs.length();i++){
                                                    answerIDs.add(jArrayIDs.getInt(i));
                                                }

                                                tinyDB.putListInt("answerIDs" + zaehler, answerIDs);


                                                Log.i("answerIDs" + zaehler, tinyDB.getString("answerIDs" + zaehler));

                                            }
                                            antworten.clear();
                                            answerIDs.clear();
                                        }

                                        if (jsonResponse.getString("alreadyvoted?").equals("ja")){
                                            MainActivity myActivity = (MainActivity)context;
                                            UmfrageAuswertenFragment umfrageAuswertenFragment = new UmfrageAuswertenFragment();
                                            myActivity.getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.frame_layout, umfrageAuswertenFragment)
                                                    .addToBackStack(null)
                                                    .commit();
                                        }else{
                                            MainActivity myActivity = (MainActivity)context;
                                            OpenUmfrageToVoteFragment openUmfrageToVoteFragment = new OpenUmfrageToVoteFragment();
                                            myActivity.getSupportFragmentManager().beginTransaction()
                                                    .replace(R.id.frame_layout, openUmfrageToVoteFragment)
                                                    .addToBackStack(null)
                                                    .commit();
                                        }






                                    } else {

                                        Toast.makeText(context, jsonResponse.getString("error_msg"), LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Log.e(RefreshfromDatabase.class.getSimpleName(), e.getMessage());
                                }
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }){

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Integer userint;

                        SharedPreferences sharedPrefLoginData = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        userint = sharedPrefLoginData.getInt("matrikelnummer", 1);
                        String user = userint.toString();

                        Map<String, String> params = new HashMap<>();
                        params.put("topic", current.getTopic());
                        params.put("matrikelnummer", user);
                        Log.i("TOpic: " , current.getTopic());

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);

            }
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(context, holder.options);
                //inflating menu from xml resource
                popup.inflate(R.menu.umfragen_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.umfragen_löschen:
                                DeleteUmfrage deleteUmfrage = new DeleteUmfrage(context, current.getTopic());
                                break;
                        }
                        return false;
                    }
                });

                SharedPreferences prfs = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String shareduser = prfs.getString("firstname", "");

                if (shareduser.equals(current.getUser())) {
                    popup.show();

                }else {
                    Toast.makeText(context, "You have no permissions to edit the poll", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView header;
        ImageView options;
        TextView topic;
        TextView more;
        ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);


            header = (TextView) itemView.findViewById(R.id.tv_umfrageitemheader);
            options = (ImageView) itemView.findViewById(R.id.umfrageitemoptions);
            topic = (TextView) itemView.findViewById(R.id.tv_umfrageitemtopic);
            more = (TextView) itemView.findViewById(R.id.tv_umfrageitemmore);
            image = (ImageView) itemView.findViewById(R.id.iv_umfragenImage);

        }

    }
}
