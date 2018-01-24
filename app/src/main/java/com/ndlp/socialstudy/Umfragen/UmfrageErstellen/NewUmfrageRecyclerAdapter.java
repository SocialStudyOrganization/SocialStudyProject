package com.ndlp.socialstudy.Umfragen.UmfrageErstellen;


import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndlp.socialstudy.R;

import java.util.ArrayList;

public class NewUmfrageRecyclerAdapter extends RecyclerView.Adapter<NewUmfrageRecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<Wortumfragenobject> data;

    public NewUmfrageRecyclerAdapter(Context context, ArrayList<Wortumfragenobject> data){
        this.context = context;
        this.data = data;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.newumfrage_item, null);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Wortumfragenobject current = data.get(position);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.newUmfrageitem_header.setTypeface(quicksand_regular);

        holder.newUmfrageitem_header.setText(current.getWortumFrageFrage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView newUmfrageitem_header;

        public MyViewHolder(View itemView) {
            super(itemView);


            newUmfrageitem_header = (TextView) itemView.findViewById(R.id.tv_newUmfrageitem_header);


        }

    }
}
