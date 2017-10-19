package com.ndlp.socialstudy.Tasks;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.ClassObject;
import com.ndlp.socialstudy.Skripte.MarketingSkripteFragment;


import java.util.ArrayList;


public class TaskClassRecyclerAdapter extends RecyclerView.Adapter<TaskClassRecyclerAdapter.MyViewHolder>{
    private Context context;

    private ArrayList<ClassObject> data;


    //  Konstruktor gets the data from ClassesActivity
    public TaskClassRecyclerAdapter(Context context, ArrayList<ClassObject> data){
        this.context = context;
        this.data = data;
    }


    //  get the xml file, give it to the viewholder
    @Override
    public TaskClassRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, null);

        return new TaskClassRecyclerAdapter.MyViewHolder(itemView);
    }

    //Viewholder wird genutzt um die items vom adapter zu zeigen
    //gets the item position id and handles on click events -> starts activities
    @Override
    public void onBindViewHolder(final TaskClassRecyclerAdapter.MyViewHolder holder, int position) {

        final ClassObject current = data.get(position);
        holder.className.setText(current.getClassTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (holder.getAdapterPosition()){
                    case 0:
                        ElektrotechnikTasksFragment elektrotechnikTasksFragment = new ElektrotechnikTasksFragment();
                        FragmentManager fragmentManager0 = ((MainActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction0 = fragmentManager0.beginTransaction();
                        fragmentTransaction0.replace(R.id.frame_layout, elektrotechnikTasksFragment);
                        fragmentTransaction0.addToBackStack(null);
                        fragmentTransaction0.commit();

                        break;

                    case 1:
                        MarketingTasksFrament marketingTasksFrament = new MarketingTasksFrament();
                        FragmentManager fragmentManager1 = ((MainActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.frame_layout, marketingTasksFrament);
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
