package com.ndlp.socialstudy.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;

import android.util.Log;
import android.widget.Toast;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//url, int for progress and return download complete string
public class FileDownloader extends AsyncTask<String, Integer, String> {

    private String fileName;
    private Context context;
    ProgressDialog progressDialog;
    File inputoutput_file;
    String subFolder;
    String format;
    File my_clicked_file;

    //final private int REQUEST_CODE_ASK_FOR_PERMISSIONS = 123;


    public FileDownloader (Context context,String fileName, String subFolder, String format, File my_clicked_file){
        this.context = context;
        this.fileName = fileName;
        this.subFolder = subFolder;
        this.format = format;
        this.my_clicked_file = my_clicked_file;
    }



    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Download in Progress...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {


        String path = params[0];
        int file_length = 0;

        Log.i("Info:", path);

        try {
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();


            // this will be useful to display download percentage
            // might be -1: server did not report the length
            file_length = urlConnection.getContentLength();


            /**
             * Create new Folder
             */


            File new_Folder = new File("/sdcard/MY DOWNLOADED FILES/" + subFolder);
            //File new_Folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), subFolder);
            if (!new_Folder.exists()) {

                if (!new_Folder.mkdir()) {
                    return "ERROR: mkdirs() failed for directory" + new_Folder.getAbsolutePath();
                }

            } else {
                Log.i("Info:", "Folder already exists");
            }

            /**
             * Create an output file to store the file for download
             */

            inputoutput_file = new File(new_Folder, fileName);


            InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
            //this will read the information in 1 KB
            byte[] data = new byte[1024];
            int total = 0;
            int count;

            OutputStream outputStream = new FileOutputStream(inputoutput_file);

            while ((count = inputStream.read(data))!= -1){
                total+=count;

                if (file_length>0) {
                    int progress = total * 100 / file_length;
                    publishProgress(progress);
                    outputStream.write(data, 0, count);
                    Log.i("Info:", "Progress: " + Integer.toString(progress));
                }
            }

            inputStream.close();
            outputStream.close();

/*            //make the file visible
            MediaScannerConnection
                    .scanFile(context, new String[] {inputoutput_file.getAbsolutePath()},
                            new String[] {null}, null);
*/
            return "Download Complete...";

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "ERROR MalformedURLException" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR IOException" + e.getMessage();
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //this will update the progress bar
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {

        progressDialog.hide();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        OpenFileClass openFileClass = new OpenFileClass(context, format, my_clicked_file, fileName, subFolder);
        openFileClass.openFile();

        if (result.toString().startsWith("ERROR")) {
            return;
        }




        //open file to view

    }
}
