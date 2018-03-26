package com.ndlp.socialstudy.GeneralFileFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import static android.R.id.progress;

//url, int for progress and return download complete string
public class FileDownloader extends AsyncTask<String, Integer, String> {

    private String fileName;
    private Context context;
    ProgressDialog progressDialog;
    File transferring_file;
    String subFolder;
    String format;
    File my_clicked_file;
    InputStream inputStream;

    //  Serverdata
    private static final String SERVER_IP = "h2774251.stratoserver.net";
    private static final String USERNAME = "studywire";
    private static final String PASSWORT = "Nadipat1";

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
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {

        FTPClient ftpClient = new FTPClient();

        String path = params[0];

        Log.i("Info:", path);

        OutputStream outputStream = null;

        try {

            ftpClient.connect(SERVER_IP);
            int reply = ftpClient.getReplyCode();               //Returns the integer value of the reply code of the last FTP reply. You will usually only use this method after you connect to the
                                                                // FTP server to check that the connection was successful since connect is of type void

            if(!FTPReply.isPositiveCompletion(reply)){
                ftpClient.disconnect();
                return "ERROR: FTP connection failed!";
            }

            if (!ftpClient.login(USERNAME, PASSWORT)){
                return "Error";
            }


            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);


            //  passes Firewall
            ftpClient.enterLocalPassiveMode();                  //telling the server to open a data port to which the client will connect to conduct data transfers.

            String directory = null;

            if (subFolder.equals("Lösungen"))
                directory = "Loesungen";

            if (subFolder.equals("Aufgaben"))
                directory = "Aufgaben";

            if (subFolder.equals("Skripte"))
                directory = "Skripte";

            ftpClient.changeWorkingDirectory("/httpdocs/Vorlesungsdokumente/" + directory);

            long fileLength = ftpClient.mlistFile(fileName).getSize();

            inputStream = ftpClient.retrieveFileStream(fileName);

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

            outputStream = new FileOutputStream(transferring_file);

            byte[] buffer = new byte[4096];
            long total = 0;
            int count;
            while ((count = inputStream.read(buffer)) != -1) {

                if (isCancelled())
                    return null;

                total += count;

                if (fileLength > 0)
                    publishProgress((int) (total * 100L / fileLength));
                outputStream.write(buffer, 0, count);
            }

            return "Download Complete...";

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "ERROR MalformedURLException" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR IOException" + e.getMessage();
        } finally {

            try {

                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();

                ftpClient.logout();
                ftpClient.disconnect();

            } catch (IOException ignored) {}

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

    @Override
    protected void onPostExecute(String result) {

        progressDialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        OpenFileClass openFileClass = new OpenFileClass(context, format, my_clicked_file, fileName, subFolder);
        openFileClass.openFile();

        if (result.toString().startsWith("ERROR")) {
            return;
        }


    }
}
