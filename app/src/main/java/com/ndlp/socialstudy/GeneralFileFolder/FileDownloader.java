package com.ndlp.socialstudy.GeneralFileFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import android.util.Log;
import android.widget.Toast;


import com.ndlp.socialstudy.GeneralFileFolder.OpenFileClass;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

//url, int for progress and return download complete string
public class FileDownloader extends AsyncTask<String, Integer, String> {

    private String fileName;
    private Context context;
    ProgressDialog progressDialog;
    File transferring_file;
    String subFolder;
    String format;
    File my_clicked_file;

    //  Serverdata
    private static final String SERVER_IP = "w0175925.kasserver.com";
    private static final String USERNAME = "f00dd887";
    private static final String PASSWORT = "Nadipat2";

    //final private int REQUEST_CODE_ASK_FOR_PERMISSIONS = 123;


    public FileDownloader (Context context,String fileName, String subFolder, String format, File my_clicked_file){
        this.context = context;
        this.fileName = fileName;
        this.subFolder = subFolder;
        this.format = format;
        this.my_clicked_file = my_clicked_file;
    }

    FTPClient ftpClient = new FTPClient();

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Download in Progress...");
        progressDialog.setMessage("Bei einem Skript dauert der Download etwas länger :)");

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
//
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {


        String path = params[0];

        Log.i("Info:", path);

        try {

            ftpClient.connect(SERVER_IP);
            int reply = ftpClient.getReplyCode();               //Returns the integer value of the reply code of the last FTP reply. You will usually only use this method after you connect to the
                                                                // FTP server to check that the connection was successful since connect is of type void

            if(!FTPReply.isPositiveCompletion(reply)){
                ftpClient.disconnect();
                return "ERROR: FTP connection failed!";
            }

            ftpClient.login(USERNAME, PASSWORT);


            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


            //  passes Firewall
            ftpClient.enterLocalPassiveMode();                  //telling the server to open a data port to which the client will connect to conduct data transfers.

            ftpClient.changeWorkingDirectory("/SocialStudy/" + subFolder);



            /**
             * Create new Folder
             */

            File new_Folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/Android/data/" + context.getPackageName() + "/files/" + subFolder);
            if (!new_Folder.exists()) {

                if (!new_Folder.mkdirs()) {
                    return "ERROR: mkdirs() failed for directory" + new_Folder.getAbsolutePath();
                }

            } else {
                Log.i("Info:", "Folder already exists");
            }

            /**
             * Create an output file to store the file for download
             */

            transferring_file = new File(new_Folder, fileName);




            OutputStream outputStream = new FileOutputStream(transferring_file);

            if (!ftpClient.retrieveFile(fileName, outputStream)) {                                         //Retrieves a named file from the server and writes it to the given OutputStream.
                                                                                                            //true if successful false if not
                outputStream.close();
                return "ERROR: Failed to retrieve file!";
            }

            outputStream.close();


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


    }
}
