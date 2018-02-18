package com.ndlp.socialstudy.NewsFeed;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;
import com.ndlp.socialstudy.R;

import java.util.ArrayList;

/**
 * Created by ndlp on 04.02.2018.
 */

public class NewsFeedRecyclerAdapter extends RecyclerView.Adapter<NewsFeedRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<NewsFeedObject> newsFeedObjectArrayList;

    public NewsFeedRecyclerAdapter(Context context, ArrayList<NewsFeedObject> newsFeedObjectArrayList){
        this.context = context;
        this.newsFeedObjectArrayList = newsFeedObjectArrayList;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setNewsFeedList(ArrayList<NewsFeedObject> newsFeedObjectArrayList){
        this.newsFeedObjectArrayList = newsFeedObjectArrayList;
    }

    @Override
    public NewsFeedRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, null);

        return new NewsFeedRecyclerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsFeedRecyclerAdapter.MyViewHolder holder, int position) {

        final NewsFeedObject currentObject = newsFeedObjectArrayList.get(position);

        String category = currentObject.getCategory();
        String uploaddate = currentObject.getUploaddate();
        String uploadtime = currentObject.getUploadtime();
        String topic = currentObject.getTopic();

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Bold.otf");

        holder.tv_header.setTypeface(quicksand_regular);
        holder.tv_topic.setTypeface(quicksand_regular);
        holder.tv_more.setTypeface(quicksand_regular);

        holder.tv_header.setText(category + ", am: " + uploaddate + ", " + uploadtime);
        holder.tv_topic.setText(currentObject.getTopic());
        holder.rl_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity mainActivity = (MainActivity)context;
                NewsAnzeigeFragment newsAnzeigeFragment = new NewsAnzeigeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("message", currentObject.getMessage());
                bundle.putString("header", currentObject.getCategory() + ", " + currentObject.getUploaddate() + ", " + currentObject.getUploadtime());
                bundle.putString("topic", currentObject.getTopic());
                newsAnzeigeFragment.setArguments(bundle);
                mainActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, newsAnzeigeFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        holder.iv_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Work in Progress", Toast.LENGTH_LONG).show();
            }
        });

        String imageName = "icon_newsfeednonumber";
        int resourceId;

        switch (category) {
            case "Aufgaben":
                imageName = "icon_exercise";
                break;
            case "DHBW News":
                imageName = "icon_dhbwnews";
                break;
            case "Kurssprecher":
                imageName = "icon_newsfeednonumber";
                break;
            case "Lösungen":
                imageName = "icon_solution";
                break;
            case "Loesungen":
                imageName = "icon_solution";
                break;
            case "Partys":
                imageName = "icon_party";
                break;
            case "Postfach":
                imageName = "icon_inbox";
                break;
            case "Seminare":
                imageName = "icon_newsfeednonumber";
                break;
            case "Skripe":
                imageName = "icon_script";
                break;
            case "StuV":
                imageName = "icon_dhbwstuv";
                break;
            case "Umfrage":
                imageName = "icon_poll";
                break;
            case "DHBW":
                imageName = "icon_dhbw";
                break;

        }
        resourceId = context.getResources().getIdentifier(imageName, "drawable" , context.getPackageName());
        holder.iv_image.setImageDrawable(ContextCompat.getDrawable(context, resourceId));
    }



    @Override
    public int getItemCount() {
        return newsFeedObjectArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_topic;
        ImageView iv_options;
        TextView tv_header;
        TextView tv_more;
        ImageView iv_image;
        RelativeLayout rl_news;


        public MyViewHolder(View itemView) {
            super(itemView);

            tv_header = (TextView) itemView.findViewById(R.id.tv_newsitemheader);
            iv_options = (ImageView) itemView.findViewById(R.id.newsoptions);
            tv_topic = (TextView) itemView.findViewById(R.id.tv_newsitemtopic);
            tv_more = (TextView) itemView.findViewById(R.id.tv_newsitemmore);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_newsitemimage);
            rl_news = (RelativeLayout) itemView.findViewById(R.id.rl_newsitem);

        }

    }
}
