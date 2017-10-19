package com.ndlp.socialstudy.Tasks;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.R;

import java.util.ArrayList;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<com.ndlp.socialstudy.Tasks.TaskRecyclerAdapter.TaskViewHolder> {

    private Context context;
    private ArrayList<TaskObject> taskObjects;

    public TaskRecyclerAdapter() {
    }

    //  conastructor
    public TaskRecyclerAdapter(Context context, ArrayList<TaskObject> taskObjects) {
        this.context = context;
        this.taskObjects = taskObjects;
    }

    //  set context constructor
    public void setContext(Context context) {
        this.context = context;
    }

    //  Object Constructor
    public void setTaskList(ArrayList<TaskObject> taskObjects) {
        this.taskObjects = taskObjects;
    }


    //  get the predefined layout create holder for data
    @Override
    public com.ndlp.socialstudy.Tasks.TaskRecyclerAdapter.TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_item, null);
        return new com.ndlp.socialstudy.Tasks.TaskRecyclerAdapter.TaskViewHolder(itemView);
    }

    //  pass data n the recommended fields and set clickListener to open pdf
    @Override
    public void onBindViewHolder(com.ndlp.socialstudy.Tasks.TaskRecyclerAdapter.TaskViewHolder holder, int position) {

        final TaskObject currentTask = taskObjects.get(position);

        holder.taskName.setText(currentTask.getTaskName());
        holder.taskDate.setText(currentTask.getTaskDate());
        holder.taskUser.setText(currentTask.getTaskUser());


        //WEBVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(context, LoginActivity.class);
                registerIntent.putExtra("pdf_name", currentTask.getTaskName());
                context.startActivity(registerIntent);
            }
        });
    }

    //  counts the items listed
    @Override
    public int getItemCount() {
        return taskObjects.size();
    }

    //  ScriptViewHolder to connect variables with items on xml
    public class TaskViewHolder extends RecyclerView.ViewHolder {

        ImageView taskIcon;
        TextView taskName;
        TextView taskDate;
        TextView taskUser;


        public TaskViewHolder(View itemView) {
            super(itemView);

            taskIcon = (ImageView) itemView.findViewById(R.id.script_icon);
            taskName = (TextView) itemView.findViewById(R.id.script_name);
            taskDate = (TextView) itemView.findViewById(R.id.script_date);
            taskUser = (TextView) itemView.findViewById(R.id.script_user);
        }
    }
}

