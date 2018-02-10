package com.ndlp.socialstudy.ShowKursmitglieder;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.GeneralFileFolder.FileDownloader;
import com.ndlp.socialstudy.GeneralFileFolder.OpenFileClass;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteObject;

import java.io.File;
import java.util.ArrayList;

public class KursmitgliederRecyclerAdapter extends RecyclerView.Adapter<KursmitgliederRecyclerAdapter.ScriptViewHolder> {

    private Context context;
    private ArrayList<KursmitgliederObject> kursmitgliederObjectArrayList;


    public KursmitgliederRecyclerAdapter() {
    }

    //  conastructor
    public KursmitgliederRecyclerAdapter(Context context, ArrayList<KursmitgliederObject> kursmitgliederObjectArrayList) {
        this.context = context;
        this.kursmitgliederObjectArrayList = kursmitgliederObjectArrayList;
    }

    //  set context constructor
    public void setContext(Context context) {
        this.context = context;
    }

    //  scriptObject Constructor
    public void setScriptList(ArrayList<KursmitgliederObject> kursmitgliederObjectArrayList) {
        this.kursmitgliederObjectArrayList = kursmitgliederObjectArrayList;
    }



    //  get the predefined layout create holder for data
    @Override
    public KursmitgliederRecyclerAdapter.ScriptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.kursmitglieder_item, null);
        return new KursmitgliederRecyclerAdapter.ScriptViewHolder(itemView);
    }

    //  pass data n the recommended fields and set clickListener to open pdf
    @Override
    public void onBindViewHolder(KursmitgliederRecyclerAdapter.ScriptViewHolder holder, int position) {

        final KursmitgliederObject current = kursmitgliederObjectArrayList.get(position);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.tv_name.setTypeface(quicksand_regular);
        holder.tv_matrikelnummer.setTypeface(quicksand_regular);
//        holder.tv_email.setTypeface(quicksand_regular);

        holder.tv_matrikelnummer.setText("Matrikelnummer: " + current.getMatrikelnummer());
 //       holder.tv_email.setText(current.getEmail());
        holder.tv_name.setText(current.getFirstname() + " " + current.getSurname());

    }

    //  counts the items listed
    @Override
    public int getItemCount() {
        return kursmitgliederObjectArrayList.size();
    }

    //  ScriptViewHolder to connect variables with items on xml
    public class ScriptViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView tv_matrikelnummer;
        TextView tv_email;


        public ScriptViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_kursmitgliedername);
            tv_matrikelnummer = (TextView) itemView.findViewById(R.id.tv_kursmitgliedermatrikelnummer);
//            tv_email = (TextView) itemView.findViewById(R.id.tv_kursmitgliederemail);
        }
    }
}
