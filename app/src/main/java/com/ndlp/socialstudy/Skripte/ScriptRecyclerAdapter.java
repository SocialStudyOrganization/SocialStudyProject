package com.ndlp.socialstudy.Skripte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.R;


import java.util.ArrayList;

/**
 * Class to handle the recyclerView
 */

public class ScriptRecyclerAdapter extends RecyclerView.Adapter<ScriptRecyclerAdapter.ScriptViewHolder> {

    private Context context;
    private ArrayList<ScriptObject> scriptObjects;

    public ScriptRecyclerAdapter() {
    }

    //  conastructor
    public ScriptRecyclerAdapter(Context context, ArrayList<ScriptObject> scriptObjects) {

        this.context = context;
        this.scriptObjects = scriptObjects;
    }

    //  set context constructor
    public void setContext(Context context) {

        this.context = context;
    }

    //  scriptObject Constructor
    public void setScriptList(ArrayList<ScriptObject> scriptObjects) {

        this.scriptObjects = scriptObjects;
    }

    //  get the predefined layout create holder for data
    @Override
    public ScriptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_item, null);
        return new ScriptViewHolder(itemView);
    }

    //  pass data n the recommended fields and set clickListener to open pdf
    @Override
    public void onBindViewHolder(ScriptViewHolder holder, int position) {

        final ScriptObject currentScript = scriptObjects.get(position);

        holder.scriptName.setText(currentScript.getScriptName());
        holder.scriptDate.setText(currentScript.getScriptDate());
        holder.scriptUser.setText(currentScript.getScriptUser());


        //WEBVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(context, LoginActivity.class);
                registerIntent.putExtra("pdf_name", currentScript.getScriptName());
                context.startActivity(registerIntent);
            }
        });
    }

    //  counts the items listed
    @Override
    public int getItemCount() {
        return scriptObjects.size();
    }

    //  ScriptViewHolder to connect variables with items on xml
    public class ScriptViewHolder extends RecyclerView.ViewHolder {

        ImageView scriptIcon;
        TextView scriptName;
        TextView scriptDate;
        TextView scriptUser;


        public ScriptViewHolder(View itemView) {
            super(itemView);

            scriptIcon = (ImageView) itemView.findViewById(R.id.script_icon);
            scriptName = (TextView) itemView.findViewById(R.id.script_name);
            scriptDate = (TextView) itemView.findViewById(R.id.script_date);
            scriptUser = (TextView) itemView.findViewById(R.id.script_user);
        }
    }
}
