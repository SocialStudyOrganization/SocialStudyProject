package com.ndlp.socialstudy.Answers;

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



public class AnswerRecyclerAdapter extends RecyclerView.Adapter<com.ndlp.socialstudy.Answers.AnswerRecyclerAdapter.AnswerViewHolder> {

    private Context context;
    private ArrayList<AnswerObject> answerObjects;

    public AnswerRecyclerAdapter() {
    }

    //  conastructor
    public AnswerRecyclerAdapter(Context context, ArrayList<AnswerObject> answerObjects) {
        this.context = context;
        this.answerObjects = answerObjects;
    }

    //  set context constructor
    public void setContext(Context context) {
        this.context = context;
    }

    //  Object Constructor
    public void setAnswerList(ArrayList<AnswerObject> answerObjects) {
        this.answerObjects = answerObjects;
    }


    //  get the predefined layout create holder for data
    @Override
    public com.ndlp.socialstudy.Answers.AnswerRecyclerAdapter.AnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_item, null);
        return new com.ndlp.socialstudy.Answers.AnswerRecyclerAdapter.AnswerViewHolder(itemView);
    }

    //  pass data n the recommended fields and set clickListener to open pdf
    @Override
    public void onBindViewHolder(com.ndlp.socialstudy.Answers.AnswerRecyclerAdapter.AnswerViewHolder holder, int position) {

        final AnswerObject currentAnswer = answerObjects.get(position);

        holder.answerName.setText(currentAnswer.getAnswerName());
        holder.answerDate.setText(currentAnswer.getAnswerDate());
        holder.answerUser.setText(currentAnswer.getAnswerUser());


        //WEBVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(context, LoginActivity.class);
                registerIntent.putExtra("pdf_name", currentAnswer.getAnswerName());
                context.startActivity(registerIntent);
            }
        });
    }

    //  counts the items listed
    @Override
    public int getItemCount() {
        return answerObjects.size();
    }

    //  ScriptViewHolder to connect variables with items on xml
    public class AnswerViewHolder extends RecyclerView.ViewHolder {

        ImageView answerIcon;
        TextView answerName;
        TextView answerDate;
        TextView answerUser;


        public AnswerViewHolder(View itemView) {
            super(itemView);

            answerIcon = (ImageView) itemView.findViewById(R.id.script_icon);
            answerName = (TextView) itemView.findViewById(R.id.script_name);
            answerDate = (TextView) itemView.findViewById(R.id.script_date);
            answerUser = (TextView) itemView.findViewById(R.id.script_user);
        }
    }
}
