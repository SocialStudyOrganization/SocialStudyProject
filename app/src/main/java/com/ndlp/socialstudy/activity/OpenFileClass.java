package com.ndlp.socialstudy.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.BuildConfig;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.ndlp.socialstudy.NavigationDrawer_BottomNavigation.MainActivity;

import java.io.File;



public class OpenFileClass {

    Context context;
    String format;
    File my_clicked_file;
    String fileName;
    String subFolder;

    public OpenFileClass (Context context, String format, File my_clicked_file, String fileName, String subFolder){
        this.context = context;
        this.format = format;
        this.my_clicked_file = my_clicked_file;
        this.fileName = fileName;
        this.subFolder = subFolder;
    }




    public void openFile(){



        try {

            switch (format) {
                            case "PDF":
                                Intent pdfIntent = new Intent();
                                pdfIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                pdfIntent.setAction(android.content.Intent.ACTION_VIEW);
                                Uri contentPDFUri = FileProvider.getUriForFile(context,
                                        "com.ndlp.socialstudy.provider",
                                        my_clicked_file);
                                pdfIntent.setDataAndType(contentPDFUri,"application/pdf");
                                Intent intentpdfChooser = Intent.createChooser(pdfIntent, "Open With");
                                try {
                                    context.startActivity(intentpdfChooser);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(context, "Please install a PDF app to view your file!", Toast.LENGTH_LONG).show();
                                    // Instruct the user to install a PDF reader here, or something
                                }
                            case "Word":

                            case "Image":
                                Intent imageIntent = new Intent();
                                imageIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                imageIntent.setAction(android.content.Intent.ACTION_VIEW);
                                //Uri uri = Uri.parse("file://" + my_clicked_file.getAbsolutePath());
                                Uri contentimageUri = FileProvider.getUriForFile(context,
                                        "com.ndlp.socialstudy.provider",
                                        my_clicked_file);
                                imageIntent.setDataAndType(contentimageUri,"image/*");
                                //imageIntent.setDataAndType(Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + subFolder + "/" + fileName), "image/*");

                                context.startActivity(imageIntent);
            }


        }catch (Exception e){
            Toast.makeText(context, "File could not be opened" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
