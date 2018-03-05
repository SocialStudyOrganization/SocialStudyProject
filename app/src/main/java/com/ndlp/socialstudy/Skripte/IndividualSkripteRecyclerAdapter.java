package com.ndlp.socialstudy.Skripte;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.GeneralFileFolder.FileDownloader;
import com.ndlp.socialstudy.GeneralFileFolder.OpenFileClass;
import com.ndlp.socialstudy.Umfragen.UmfrageErstellen.NewUmfrageActivity;
import com.ndlp.socialstudy.activity.TinyDB;


import java.io.File;
import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;


public class IndividualSkripteRecyclerAdapter extends RecyclerView.Adapter<IndividualSkripteRecyclerAdapter.ScriptViewHolder> {

    private Context context;
    private ArrayList<SkripteObject> scriptObjects;
    private String imageName;
    private String subFolder;
    private String url;
    //SkripteObject currentScript;
    String whichFormat;
    String fileName;
    File my_clicked_file;

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

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(context.getAssets(),  "fonts/Quicksand-Regular.otf");

        //assigning typefaces
        holder.scriptName.setTypeface(quicksand_regular);
        holder.scriptDate.setTypeface(quicksand_regular);
        holder.scriptUser.setTypeface(quicksand_regular);

        holder.scriptName.setText(currentScript.getScriptName());
        holder.scriptDate.setText(currentScript.getScriptDate());
        holder.scriptUser.setText(currentScript.getScriptUser());

        whichFormat = currentScript.getScriptFormat();

        Log.i("current Skript load: ", holder.scriptName.getText().toString());


        switch (whichFormat) {
            case "PDF":
                imageName = "icon_pdf";
                int resourceId = context.getResources().getIdentifier(imageName, "drawable" , context.getPackageName());
                holder.scriptIcon.setImageDrawable(ContextCompat.getDrawable(context, resourceId));
                break;
            case "Image":
                imageName = "icon_img";
                int resourceId2 = context.getResources().getIdentifier(imageName, "drawable" , context.getPackageName());
                holder.scriptIcon.setImageDrawable(ContextCompat.getDrawable(context, resourceId2));
                break;
        }


        //WEBVIEW
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //pass subfolder
                fileName = currentScript.getScriptName();

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


                    my_clicked_file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/Android/data/" + context.getPackageName() + "/files/" + subFolder + "/" + fileName);

                    if (my_clicked_file.exists()) {
                        OpenFileClass openFileClass = new OpenFileClass(context, whichFormat, my_clicked_file, fileName, subFolder);
                        openFileClass.openFile();
                    }
                    else {

                        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                        if (activeNetwork != null) { // connected to the internet
                            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                                //connected to wifi
                                FileDownloader fileDownloader = new FileDownloader(context, fileName, subFolder, whichFormat, my_clicked_file);
                                fileDownloader.execute(url);

                            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                                // connected to the mobile provider's data plan


                                new AlertDialog.Builder(context)
                                        .setTitle("Datenverbrauch-Warnung")
                                        .setMessage("Du bist derzeit nicht mit dem Wlan verbunden. Willst du die Datei über dein mobiles Internet downloaden?")
                                        .setNegativeButton(android.R.string.no, null)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which) {
                                                    case BUTTON_NEGATIVE:
                                                        dialog.dismiss();
                                                        break;
                                                    case BUTTON_POSITIVE:
                                                        FileDownloader fileDownloader = new FileDownloader(context, fileName, subFolder, whichFormat, my_clicked_file);
                                                        fileDownloader.execute(url);
                                                        dialog.dismiss();
                                                        break;
                                                }

                                            }
                                        }).create().show();




                            }
                        } else {
                            Toast.makeText(context, "You have currently no connection to the internet", Toast.LENGTH_SHORT).show();
                        }



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
