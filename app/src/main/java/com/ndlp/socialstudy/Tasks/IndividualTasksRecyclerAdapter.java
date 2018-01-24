package com.ndlp.socialstudy.Tasks;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.GeneralFileFolder.FileDownloader;
import com.ndlp.socialstudy.GeneralFileFolder.OpenFileClass;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.IndividualSkripteRecyclerAdapter;
import com.ndlp.socialstudy.Skripte.SkripteObject;

import java.io.File;
import java.util.ArrayList;

public class IndividualTasksRecyclerAdapter extends RecyclerView.Adapter<IndividualTasksRecyclerAdapter.TaskViewHolder>{

    private Context context;
    private ArrayList<TaskObject> taskObjects;
    private String imageName;
    private String subFolder;
    private String url;

    public IndividualTasksRecyclerAdapter() {
    }

    //  conastructor
    public IndividualTasksRecyclerAdapter(Context context, ArrayList<TaskObject> taskObjects, String subFolder) {
        this.context = context;
        this.taskObjects = taskObjects;
        this.subFolder = subFolder;
    }

    //  set context constructor
    public void setContext(Context context) {
        this.context = context;
    }

    // Object Constructor
    public void setTaskList(ArrayList<TaskObject> taskObjects) {
        this.taskObjects = taskObjects;
    }
    public void setSubFolder(String subFolder){
        this.subFolder = subFolder;
    }

    //  get the predefined layout create holder for data
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_item, null);
        return new TaskViewHolder(itemView);
    }


    //  pass data n the recommended fields and set clickListener to open pdf
    @Override
    public void onBindViewHolder(IndividualTasksRecyclerAdapter.TaskViewHolder holder, int position) {

        final TaskObject currentTask = taskObjects.get(position);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.taskDate.setTypeface(quicksand_regular);
        holder.taskName.setTypeface(quicksand_regular);
        holder.taskUser.setTypeface(quicksand_regular);

        holder.taskName.setText(currentTask.getTaskName());
        holder.taskDate.setText(currentTask.getTaskDate());
        holder.taskUser.setText(currentTask.getTaskUser());

        final String whichFormat = currentTask.getTaskFormat();
        switch (whichFormat) {
            case "PDF":
                imageName = "icon_pdf";
            case "Image":
                imageName = "ic_photo_library_black_24dp";
        }

        int resourceId = context.getResources().getIdentifier(imageName, "drawable" , context.getPackageName());
        holder.taskIcon.setImageDrawable(ContextCompat.getDrawable(context, resourceId));



        //WEBVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //pass subfolder
                String fileName = currentTask.getTaskName();

                //set subFolder so DownloadFiles gets the right subFolder we came from
                try {
                    switch (subFolder) {
                        case "Tasks":
                            url = "http://hellownero.de/SocialStudy/Tasks/" + fileName;
                        case "Answers":
                            url = "http://hellownero.de/SocialStudy/Answers/" + fileName;
                        case "Skripte":
                            url = "http://hellownero.de/SocialStudy/Skripte/" + fileName;
                    }


                    File my_clicked_file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Android/data/" + context.getPackageName() + "/files/" + subFolder + "/" + fileName);

                    if (my_clicked_file.exists()) {
                        OpenFileClass openFileClass = new OpenFileClass(context, whichFormat, my_clicked_file, fileName, subFolder);
                        openFileClass.openFile();
                    }
                    else {
                        FileDownloader fileDownloader = new FileDownloader(context, fileName, subFolder, whichFormat, my_clicked_file);
                        fileDownloader.execute(url);
                    }

                } catch (Exception e){
                    Toast.makeText(context, "ERROR: " + fileName + "Ein Fehler beim Klicken ist aufgetreten (FileDownloader)!", Toast.LENGTH_LONG).show();
                }



            }
        });
    }

    //  counts the items listed
    @Override
    public int getItemCount() {
        return taskObjects.size();
    }


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
