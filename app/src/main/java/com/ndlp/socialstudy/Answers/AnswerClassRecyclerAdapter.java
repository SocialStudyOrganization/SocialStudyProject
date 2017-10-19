package com.ndlp.socialstudy.Answers;

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


import java.util.ArrayList;



public class AnswerClassRecyclerAdapter extends RecyclerView.Adapter<com.ndlp.socialstudy.Answers.AnswerClassRecyclerAdapter.MyViewHolder>{
    private Context context;

    private ArrayList<ClassObject> data;


    //  Konstruktor gets the data from ClassesActivity
    public AnswerClassRecyclerAdapter(Context context, ArrayList<ClassObject> data){
        this.context = context;
        this.data = data;
    }


    //  get the xml file, give it to the viewholder
    @Override
    public com.ndlp.socialstudy.Answers.AnswerClassRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, null);

        return new com.ndlp.socialstudy.Answers.AnswerClassRecyclerAdapter.MyViewHolder(itemView);
    }

    //Viewholder wird genutzt um die items vom adapter zu zeigen
    //gets the item position id and handles on click events -> starts activities
    @Override
    public void onBindViewHolder(final com.ndlp.socialstudy.Answers.AnswerClassRecyclerAdapter.MyViewHolder holder, int position) {

        final ClassObject current = data.get(position);
        holder.className.setText(current.getClassTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (holder.getAdapterPosition()){
                    case 0:
                        ElektrotechnikAnswersFragment elektrotechnikAnswersFragment = new ElektrotechnikAnswersFragment();
                        FragmentManager fragmentManager0 = ((MainActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction0 = fragmentManager0.beginTransaction();
                        fragmentTransaction0.replace(R.id.frame_layout, elektrotechnikAnswersFragment);
                        fragmentTransaction0.addToBackStack(null);
                        fragmentTransaction0.commit();

                        break;

                    case 1:
                        MarketingAnswersFrament marketingAnswersFrament = new MarketingAnswersFrament();
                        FragmentManager fragmentManager1 = ((MainActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.frame_layout, marketingAnswersFrament);
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
