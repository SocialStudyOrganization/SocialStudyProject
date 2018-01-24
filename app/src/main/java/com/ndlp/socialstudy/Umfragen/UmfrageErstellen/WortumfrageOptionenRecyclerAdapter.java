package com.ndlp.socialstudy.Umfragen.UmfrageErstellen;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ndlp.socialstudy.R;

import java.util.ArrayList;



public class WortumfrageOptionenRecyclerAdapter extends RecyclerView.Adapter<WortumfrageOptionenRecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<WortumfragelistenObject> data;

    public WortumfrageOptionenRecyclerAdapter(Context context, ArrayList<WortumfragelistenObject> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wortumfrage_item, null);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final WortumfragelistenObject current = data.get(position);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.itemTitle.setTypeface(quicksand_regular);
        holder.removeButton.setTypeface(quicksand_regular);

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
