package com.ndlp.socialstudy.Umfragen;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Tasks.TaskObject;


import java.util.ArrayList;

public class BasicUmfragenRecyclerAdapter extends RecyclerView.Adapter<BasicUmfragenRecyclerAdapter.MyViewHolder>{

    private Context context;
    private ArrayList<GeneralObject> data;

    public BasicUmfragenRecyclerAdapter(Context context, ArrayList<GeneralObject> data){
        this.context = context;
        this.data = data;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // Object Constructor
    public void setUmfragenList(ArrayList<GeneralObject> generalObjects) {
        this.data = generalObjects;
    }

    @Override
    public BasicUmfragenRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.umfrage_item, null);

        return new BasicUmfragenRecyclerAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(BasicUmfragenRecyclerAdapter.MyViewHolder holder, final int position) {

        final GeneralObject current = data.get(position);

        String user = current.getUser();
        String enddate = current.getEnddate();
        String endtime = current.getEndtime();

        holder.header.setText(user + ", gültig bis:" + enddate + ", " + endtime);
        holder.topic.setText(current.getTopic());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked on more...", Toast.LENGTH_LONG).show();
            }
        });

        holder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked on options...", Toast.LENGTH_LONG).show();
            }
        });

        holder.image.setImageResource(R.mipmap.ic_insert_drive_file_black_24dp);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView header;
        ImageView options;
        TextView topic;
        TextView more;
        ImageView image;


        public MyViewHolder(View itemView) {
            super(itemView);


            header = (TextView) itemView.findViewById(R.id.tv_umfrageitemheader);
            options = (ImageView) itemView.findViewById(R.id.umfrageitemoptions);
            topic = (TextView) itemView.findViewById(R.id.tv_umfrageitemtopic);
            more = (TextView) itemView.findViewById(R.id.tv_umfrageitemmore);
            image = (ImageView) itemView.findViewById(R.id.iv_umfragenImage);

        }

    }
}
