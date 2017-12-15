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

            if (format.equals("PDF")) {

                Uri contentPDFUri = FileProvider.getUriForFile(context,
                        "com.ndlp.socialstudy.provider",
                        my_clicked_file);
                String stringUri = contentPDFUri.toString();
                Intent intent = new Intent(context, PDFViewerActivity.class);
                intent.putExtra("pathtoPDF", stringUri);
                context.startActivity(intent);
            }
            else if (format.equals("Image")) {

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
            else if (format.equals("Word")) {

            }


        }catch (Exception e){
            Toast.makeText(context, "File could not be opened" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


}
