package com.ndlp.socialstudy.GeneralFileFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ndlp.socialstudy.Notifications.DateienAsyncTask;
import com.ndlp.socialstudy.Skripte.AnswersDataIntoDatabase;
import com.ndlp.socialstudy.Skripte.SkripteDataIntoDatabase;
import com.ndlp.socialstudy.Skripte.TasksDataIntoDatabase;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class FileUploader extends AsyncTask<String, Integer, Boolean> {

    //  Variables
    private Context context;
    private Uri contentUri;
    private String fileName;
    private String format;
    private String category;
    private String date;
    private String time;
    private String user;
    private String subFolder;
    private String urlAddress;
    private RecyclerView mRecyclerView;

    ProgressDialog progressDialog;
    private PowerManager.WakeLock mWakeLock;

    private static final String SERVER_IP = "w0175925.kasserver.com";
    private static final String USERNAME = "f00dd887";
    private static final String PASSWORT = "Nadipat2";

    //  Constructor
    public FileUploader (Context context, Uri contentUri, String fileName, String format, String category,
                      String date, String time, String user, String subFolder, String urlAddress, RecyclerView mRecyclerView) {
        this.context = context;
        this.contentUri = contentUri;
        this.fileName = fileName;
        this.format = format;
        this.category = category;
        this.date = date;
        this.time = time;
        this.user = user;
        this.subFolder = subFolder;
        this.urlAddress = urlAddress;
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    protected void onPreExecute() {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        mWakeLock.acquire();

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Upload in progress...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    //  Methoden to uploadTask
    protected Boolean doInBackground(String... params) {


        FTPClient ftpClient = new FTPClient();
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //  connected zum Server + alles
        try {
            //  resolver Brechtigungen und liest die Datei ein

            ftpClient.connect(SERVER_IP);
            int reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                Log.e("UploadTask", "FTPServer refuses connection");
            }

            ftpClient.login(USERNAME, PASSWORT);

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            //  passes Firewall
            ftpClient.enterLocalPassiveMode();

            //  navigates to the folder on te server
            ftpClient.changeWorkingDirectory("/SocialStudy/" + subFolder);

            inputStream = context.getContentResolver().openInputStream(contentUri);

            outputStream = ftpClient.storeFileStream(fileName);

            if (outputStream == null)
                return false;

            long fileLength = inputStream.available();
            int total = 0;
            int count;
            byte[] buffer = new byte[4096];
            while ((count = inputStream.read(buffer)) != -1) {

                if (isCancelled())
                    return false;

                total += count;

                if (fileLength > 0)
                    publishProgress((int) (total * 100L / fileLength));
                outputStream.write(buffer, 0, count);
            }

            inputStream.close();
            outputStream.close();

            return ftpClient.completePendingCommand();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            try {

                //  control if inputstream has data, logouts from ftp and disconnects
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();

                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException ignored) {

            }
        }

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        //this will update the progress bar
        super.onProgressUpdate(progress);

        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(progress[0]);
    }


    //
    @Override
    protected void onPostExecute(Boolean result) {
        mWakeLock.release();
        //  if true make toast that file is uploaded
        if (result) {
            Toast.makeText(context, "Datei hochgeladen", Toast.LENGTH_LONG).show();

        progressDialog.hide();

            //  calls method putIntoTable()
            putIntoTable();

        }

    }


    //  method to get the result on the server from uploading skript detailled data
    //gets called in postexecute only if file is successfully uploaded
    public void putIntoTable() {

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            //when the request is executed and volley gives a reponse it will
            // check success from php
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success) {
                        Toast.makeText(context, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();

                        new DateienAsyncTask(fileName, category, subFolder).execute();

                        //notify recycler adapter that dataset changed
                        new RefreshfromDatabase(context, urlAddress, mRecyclerView, category, subFolder);

                    } else {
                        Toast.makeText(context, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };


        if (subFolder == "Skripte"){
            //  starts the request to upload skriptname category, date, time, user to server
            SkripteDataIntoDatabase skripteDataIntoDatabase = new SkripteDataIntoDatabase(fileName, format, category, date, time, user, responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(skripteDataIntoDatabase);
        }

        if (subFolder == "Tasks"){
            //  starts the request to upload skriptname category, date, time, user to server
            TasksDataIntoDatabase tasksDataIntoDatabase = new TasksDataIntoDatabase(fileName, format, category, date, time, user, responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(tasksDataIntoDatabase);
        }

        if (subFolder == "Answers"){
            //  starts the request to upload skriptname category, date, time, user to server
            AnswersDataIntoDatabase answersDataIntoDatabase = new AnswersDataIntoDatabase(fileName, format, category, date, time, user, responseListener);
            RequestQueue queue = Volley.newRequestQueue(context);
            queue.add(answersDataIntoDatabase);
        }


    }



}
