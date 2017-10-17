package com.ndlp.socialstudy.Skripte;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.LoginSystem.RegisterActivity;
import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.SKripteFragments.ClassesFragment;
import com.ndlp.socialstudy.SKripteFragments.ElektrotechnikSkripteFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class handeling the RecyclerAdapter
 */

public class ClassRecyclerAdapter extends RecyclerView.Adapter<ClassRecyclerAdapter.MyViewHolder> {

    private Context context;

    private ArrayList<ClassObject> data;


    //  Konstruktor gets the data from ClassesActivity
    public ClassRecyclerAdapter(Context context, ArrayList<ClassObject> data){
        this.context = context;
        this.data = data;
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

        final ClassObject current = data.get(position);
        holder.className.setText(current.getClassTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (holder.getAdapterPosition()){
                    case 0:
                        ElektrotechnikSkripteFragment elektrotechnikSkripteFragment = new ElektrotechnikSkripteFragment();
                        FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, elektrotechnikSkripteFragment);
                        fragmentTransaction.commit();

                        break;
                    // case 1...

                    default:
                        intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
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
