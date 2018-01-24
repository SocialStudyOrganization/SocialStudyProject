package com.ndlp.socialstudy.Tasks;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndlp.socialstudy.GeneralFileFolder.VorlesungenObject;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteVorlesungenRecyclerAdapter;
import com.ndlp.socialstudy.Skripte.Vorlesungen.AussenwirtschaftSkripteFragment;
import com.ndlp.socialstudy.Skripte.Vorlesungen.InformatikSkripteFragment;
import com.ndlp.socialstudy.Skripte.Vorlesungen.KonstruktionslehreSkripteFragment;
import com.ndlp.socialstudy.Tasks.Vorlesungen.AussenwirtschaftTasksFragment;

import java.util.ArrayList;

/**
 * Created by ndlp on 16.12.2017.
 */

public class TasksVorlesungenRecyclerAdapter extends RecyclerView.Adapter<TasksVorlesungenRecyclerAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<VorlesungenObject> data;

    //  Konstruktor gets the data from ClassesActivity
    public TasksVorlesungenRecyclerAdapter(Context context, ArrayList<VorlesungenObject> data){
        this.context = context;
        this.data = data;
    }


    //  get the xml file, give it to the viewholder
    @Override
    public TasksVorlesungenRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, null);

        return new TasksVorlesungenRecyclerAdapter.MyViewHolder(itemView);
    }

    //Viewholder wird genutzt um die items vom adapter zu zeigen
    //gets the item position id and handles on click events -> starts activities
    @Override
    public void onBindViewHolder(final TasksVorlesungenRecyclerAdapter.MyViewHolder holder, int position) {

        final VorlesungenObject current = data.get(position);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.className.setTypeface(quicksand_regular);

        holder.className.setText(current.getClassTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (holder.getAdapterPosition()){
                    case 0:
                        AussenwirtschaftTasksFragment aussenwirtschaftTasksFragment = new AussenwirtschaftTasksFragment();
                        FragmentManager fragmentManager1 = ((MainActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.frame_layout, aussenwirtschaftTasksFragment);
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        break;


                    default:
                        break;
                }

            }
        });

    }

    //  returns the total count of the items hold by the adapter
    @Override
    public int getItemCount() {
        return data.size();
    }

    //  classifies the view holder and prevents always using findViewById
    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView className;

        public MyViewHolder(View itemView) {
            super(itemView);


            className = (TextView) itemView.findViewById(R.id.tv_classname);


        }

    }
}
