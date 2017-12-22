package com.ndlp.socialstudy.Umfragen;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.newUmfrageitem_header.setText(current.getWortumFrageFrage());

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, data.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView newUmfrageitem_header;
        Button removeButton;

        public MyViewHolder(View itemView) {
            super(itemView);


            newUmfrageitem_header = (TextView) itemView.findViewById(R.id.tv_newUmfrageitem_header);
            removeButton = (Button) itemView.findViewById(R.id.b_newUmfrageitem_deleteItem);


        }

    }
}
