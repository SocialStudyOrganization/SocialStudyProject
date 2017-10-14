package com.ndlp.socialstudy.Skripte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.GetScriptData.Downloader;
import com.ndlp.socialstudy.R;

/**
 *Activity to handle the listing of different informatic files (pdf Skripte)
 */

public class InformatikSkripteActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    private FloatingActionButton floatingasPDF;
    private FloatingActionButton floatingasWord;


    private static final int READ_REQUEST_CODE = 1;
    Uri PDFUri;
    public String PDFName;


    //  Serverdata
    private static final String SERVER_IP = "w0175925.kasserver.com";
    private static final String USERNAME = "f00dd887";
    private static final String PASSWORT = "Nadipat2";

    //  location of the php script on server
    final static String urlAddress = "http://hellownero.de/SkripteSkript/select_skripte.php";

    public String skriptname;
    public String category = "informatik";
    public String date;
    public String time;
    public String user;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informatik_skripte);

        //  set toolbar and adjust title
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Informatik Skripte");

        //  initialize the recyclerView of the data files
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_skripteInformatik);

        floatingasPDF = (FloatingActionButton) findViewById(R.id.floating_asPDFFile);

        PDFUri = null;
        PDFName = null;

        //  gets the username out of sharedPrefs LoginData
        SharedPreferences sharedPrefLoginData = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user = sharedPrefLoginData.getString("username", "");

        //  calls DownloaderClass and puts urlAddress as parameter
        new Downloader(InformatikSkripteActivity.this, urlAddress, mRecyclerView).execute();

        //  set onClickListener on the floating item as PDF
        floatingasPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  get all files on the device with type pdf -> highlighted and clickable
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                //  get the data for the fields in the recycler view according to time and date
                Calendar calander = Calendar.getInstance();
                int cDay = calander.get(Calendar.DAY_OF_MONTH);
                int cMonth = calander.get(Calendar.MONTH) + 1;
                int cYear = calander.get(Calendar.YEAR);
                date = String.valueOf(cDay)+ "." + String.valueOf(cMonth)+ "." + String.valueOf(cYear);
                int cHour = calander.get(Calendar.HOUR_OF_DAY);
                int cMinute = calander.get(Calendar.MINUTE);
                int cSecond = calander.get(Calendar.SECOND);
                time = String.valueOf(cHour)+ ":" +String.valueOf(cMinute)+ ":" + String.valueOf(cSecond);

                //  calls startAcivityForResult method
                startActivityForResult(intent, READ_REQUEST_CODE);

            }
        });

    }


    //  if we receive a result from volley this gets called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //  if result code == ok and If the request code seen here doesn't match, it's the
        //  response to some other intent, and the code below shouldn't run at all.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            //  check that intent != 0 therefore consists of data
            //  getData() gets the data out of the Intent and saves it as PFDUri variable
            if (data != null){
                PDFUri = data.getData();
                Cursor resultCursor = getContentResolver().query(PDFUri, null, null, null, null);

                //  move to first row
                resultCursor.moveToFirst();

                //  get PDF name out of the file and set it as PDFName
                PDFName = resultCursor.getString(resultCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));


                //  sets skriptname equals to PDFName
                skriptname = PDFName;

                //  calls method putIntoTable()
                putIntoTable();

                //  starts upload task to the server
                UploadTask uploadTask = new UploadTask(InformatikSkripteActivity.this, PDFUri, PDFName);
                uploadTask.execute(SERVER_IP, USERNAME, PASSWORT);



            }
        }
    }

    //  method to get the result on the server from uploading skript detailled data
    public void putIntoTable(){

        Response.Listener<String> responseListener = new Response.Listener<String>() {

            //when the request is executed and volley gives a reponse it will
            // check success from php
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");


                    if (success){
                        Toast.makeText(InformatikSkripteActivity.this, jsonResponse.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                    else {

                        Log.e("Skriptladen", jsonResponse.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //  starts the request to upload skriptname category, date, time, user to server
        SkripteRequest skripteRequest = new SkripteRequest(skriptname, category, date, time, user, responseListener);
        RequestQueue queue = Volley.newRequestQueue(InformatikSkripteActivity.this);
        queue.add(skripteRequest);
    }

    //  with using asyncTask the download is handled in the background
    //  therefore user can use app nevertheless
    private class UploadTask extends AsyncTask<String, Integer, Boolean> {

        //  Variables
        private Context context;
        private Uri contentUri;
        private String fileName;

        private PowerManager.WakeLock mWakeLock;

        //  Constructor
        public UploadTask(Context context, Uri contentUri, String fileName){
            this.context = context;
            this.contentUri = contentUri;
            this.fileName = fileName;
        }

        //  Methoden to uploadTask
        protected Boolean doInBackground(String... params) {

            FTPClient ftpClient = new FTPClient();
            InputStream inputStream = null;

            //  connected zum Server + alles
            try {
                //  resolver Brechtigungen und liest die Datei ein
                inputStream = getContentResolver().openInputStream(contentUri);

                ftpClient.connect(params[0]);
                int reply = ftpClient.getReplyCode();

                if(!FTPReply.isPositiveCompletion(reply)){
                    ftpClient.disconnect();
                    Log.e("UploadTask", "FTPServer refuses connection");
                }

                ftpClient.login(params[1], params[2]);

                //  passes Firewall
                ftpClient.enterLocalPassiveMode();

                //  navigates to the folder on te server
                ftpClient.changeWorkingDirectory("/pdf");
                return ftpClient.storeFile(fileName, inputStream);

            } catch(Exception e){
                e.printStackTrace();
                return false;
            } finally {

                try {

                    //  control if inputstream has data, logouts from ftp and disconnects
                    if (inputStream != null)
                        inputStream.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch(IOException ignored) {

                }
            }
        }

        @Override
        protected void onPreExecute() {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            mWakeLock.acquire();
        }

        //
        @Override
        protected void onPostExecute(Boolean result) {
            mWakeLock.release();
            //  if true make toast that file is uploaded
            if (result) {
                Toast.makeText(context, "Datei hochgeladen", Toast.LENGTH_LONG).show();
                new Downloader(InformatikSkripteActivity.this, urlAddress, mRecyclerView).execute();
            }

        }
    }


}
