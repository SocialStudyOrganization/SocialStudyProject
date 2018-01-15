package com.ndlp.socialstudy.Skripte;

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

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.GeneralFileFolder.FileDownloader;
import com.ndlp.socialstudy.GeneralFileFolder.OpenFileClass;


import java.io.File;
import java.util.ArrayList;

/**
 * Class to handle the recyclerView
 */

public class IndividualSkripteRecyclerAdapter extends RecyclerView.Adapter<IndividualSkripteRecyclerAdapter.ScriptViewHolder> {

    private Context context;
    private ArrayList<SkripteObject> scriptObjects;
    private String imageName;
    private String subFolder;
    private String url;

    public IndividualSkripteRecyclerAdapter() {
    }

    //  conastructor
    public IndividualSkripteRecyclerAdapter(Context context, ArrayList<SkripteObject> scriptObjects, String subFolder) {
        this.context = context;
        this.scriptObjects = scriptObjects;
        this.subFolder = subFolder;
    }

    //  set context constructor
    public void setContext(Context context) {
        this.context = context;
    }

    //  scriptObject Constructor
    public void setScriptList(ArrayList<SkripteObject> scriptObjects) {
        this.scriptObjects = scriptObjects;
    }
    public void setSubFolder(String subFolder){
        this.subFolder = subFolder;
    }


    //  get the predefined layout create holder for data
    @Override
    public ScriptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.script_item, null);
        return new ScriptViewHolder(itemView);
    }

    //  pass data n the recommended fields and set clickListener to open pdf
    @Override
    public void onBindViewHolder(ScriptViewHolder holder, int position) {

        final SkripteObject currentScript = scriptObjects.get(position);

        holder.scriptName.setText(currentScript.getScriptName());
        holder.scriptDate.setText(currentScript.getScriptDate());
        holder.scriptUser.setText(currentScript.getScriptUser());

        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");

        holder.scriptName.setTypeface(quicksand_regular);

        final String whichFormat = currentScript.getScriptFormat();
        switch (whichFormat) {
            case "PDF":
                imageName = "ic_picture_as_pdf_black_24dp";
            case "Image":
                imageName = "ic_photo_library_black_24dp";
        }

        int resourceId = context.getResources().getIdentifier(imageName, "drawable" , context.getPackageName());
        holder.scriptIcon.setImageDrawable(ContextCompat.getDrawable(context, resourceId));



        //WEBVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //pass subfolder
                String fileName = currentScript.getScriptName();

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
        return scriptObjects.size();
    }

    //  ScriptViewHolder to connect variables with items on xml
    public class ScriptViewHolder extends RecyclerView.ViewHolder {

        ImageView scriptIcon;
        TextView scriptName;
        TextView scriptDate;
        TextView scriptUser;


        public ScriptViewHolder(View itemView) {
            super(itemView);

            scriptIcon = (ImageView) itemView.findViewById(R.id.script_icon);
            scriptName = (TextView) itemView.findViewById(R.id.script_name);
            scriptDate = (TextView) itemView.findViewById(R.id.script_date);
            scriptUser = (TextView) itemView.findViewById(R.id.script_user);
        }
    }
}
