package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.TinyDB;

import java.util.ArrayList;



public class UmfrageAnzeigenRecyclerAdapter extends RecyclerView.Adapter<UmfrageAnzeigenRecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<UmfrageAnzeigenObject> data;
    ArrayList<String> checked_items= new ArrayList<>();
    ArrayList<Integer> checked_ids = new ArrayList<>();

    public UmfrageAnzeigenRecyclerAdapter(Context context, ArrayList<UmfrageAnzeigenObject> data){
        this.context = context;
        this.data = data;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    // Object Constructor
    public void setObjectList(ArrayList<UmfrageAnzeigenObject> umfrageAnzeigenObjects) {
        this.data = umfrageAnzeigenObjects;
    }

    @Override
    public UmfrageAnzeigenRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.voting_item, null);

        return new UmfrageAnzeigenRecyclerAdapter.MyViewHolder(itemView);
    }

    public ArrayList<String> passArrayToActivity(){
        return checked_items;
    }

    public ArrayList<Integer> passIdsToActivity(){
        return checked_ids;
    }

    public void deleteArray(){
        checked_items.clear();
        checked_ids.clear();
    }



    @Override
    public void onBindViewHolder(final UmfrageAnzeigenRecyclerAdapter.MyViewHolder holder, final int position) {

        final UmfrageAnzeigenObject current = data.get(position);

        String text = current.getText();

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.tv_text.setTypeface(quicksand_regular);
        holder.checkBox.setTypeface(quicksand_regular);

        holder.tv_text.setText(current.getText());

        holder.checkBox.setChecked(false);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    checked_items.add(current.getText());
                    checked_ids.add(current.getId());
                    holder.checkBox.setChecked(true);

                }
                else
                {
                    checked_items.remove(current.getText());
                    checked_ids.remove(current.getId());
                    holder.checkBox.setChecked(false);
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_text;
        CheckBox checkBox;



        public MyViewHolder(View itemView) {
            super(itemView);


            tv_text = (TextView) itemView.findViewById(R.id.votingitemtext);
            checkBox = (CheckBox) itemView.findViewById(R.id.votingitemcheckbox);


        }

    }
}
