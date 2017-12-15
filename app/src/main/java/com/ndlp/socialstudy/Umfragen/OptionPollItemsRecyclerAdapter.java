package com.ndlp.socialstudy.Umfragen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndlp.socialstudy.R;

import java.util.ArrayList;



public class OptionPollItemsRecyclerAdapter extends RecyclerView.Adapter<OptionPollItemsRecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<OptionPollItemOpject> data;

    public OptionPollItemsRecyclerAdapter (Context context, ArrayList<OptionPollItemOpject> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_poll_item, null);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final OptionPollItemOpject current = data.get(position);
        holder.itemTitle.setText(current.getItemTitle());

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

        TextView itemTitle;
        Button removeButton;

        public MyViewHolder(View itemView) {
            super(itemView);


            itemTitle = (TextView) itemView.findViewById(R.id.tv_itemTitle);
            removeButton = (Button) itemView.findViewById(R.id.b_deleteOptionPollItem);


        }

    }
}