package com.ndlp.socialstudy.Vorlesungen;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteFragment;

import java.util.ArrayList;

/**
 * Class handeling the RecyclerAdapter
 */

public class VorlesungenRecyclerAdapter extends RecyclerView.Adapter<VorlesungenRecyclerAdapter.MyViewHolder> {

    private Context context;
    private String subFolder;
    private ArrayList<VorlesungenObject> vorlesungenObjects;


    //  Konstruktor gets the data from ClassesActivity
    public VorlesungenRecyclerAdapter(Context context, ArrayList<VorlesungenObject> vorlesungenObjects, String subFolder){
        this.context = context;
        this.subFolder = subFolder;
        this.vorlesungenObjects = vorlesungenObjects;
    }

    public void setVorlesungsList(ArrayList<VorlesungenObject> vorlesungenObjects){
        this.vorlesungenObjects = vorlesungenObjects;
    }

    public void setContext(Context context){
        this.context = context;
    }


    //  get the xml file, give it to the viewholder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, null);

        return new MyViewHolder(itemView);
    }

    //Viewholder wird genutzt um die items vom adapter zu zeigen
    //gets the item position id and handles on click events -> starts activities
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final VorlesungenObject current = vorlesungenObjects.get(position);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.tv_className.setTypeface(quicksand_regular);
        holder.tv_className.setText(current.getBezeichnung());
        holder.tv_anzahl.setText(current.getAnzahl());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vorlesungsname = holder.tv_className.getText().toString();

                Bundle b = new Bundle();

                b.putString("category", vorlesungsname);
                b.putString("subFolder", subFolder);

                SkripteFragment skripteFragment = new SkripteFragment();
                skripteFragment.setArguments(b);
                FragmentManager fragmentManager0 = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction0 = fragmentManager0.beginTransaction();
                fragmentTransaction0.replace(R.id.frame_layout, skripteFragment);
                fragmentTransaction0.addToBackStack(null);
                fragmentTransaction0.commit();

            }
        });

    }

    //  returns the total count of the items hold by the adapter
    @Override
    public int getItemCount() {
        return vorlesungenObjects.size();
    }

    //  classifies the view holder and prevents always using findViewById
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_className;
        TextView tv_anzahl;

        public MyViewHolder(View itemView) {
            super(itemView);


            tv_className = (TextView) itemView.findViewById(R.id.tv_classname);
            tv_anzahl = (TextView) itemView.findViewById(R.id.tv_anzahl);


        }

    }
}
