package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.LoginSystem.RegisterActivity;
import com.ndlp.socialstudy.LoginSystem.RegisterRequest;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.TinyDB;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

        holder.header.setText(user + ", gültig bis: " + enddate + ", " + endtime + " Uhr");
        holder.topic.setText(current.getTopic());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity myActivity = (MainActivity)context;
                OpenUmfrageToVoteFragment openUmfrageToVoteFragment = new OpenUmfrageToVoteFragment();
                myActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, openUmfrageToVoteFragment)
                        .addToBackStack(null)
                        .commit();
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
                            case R.id.umfragen_auswerten:
                                //handle menu1 click
                                break;
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
