package com.ndlp.socialstudy.GeneralFileFolder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;


class UploadTask {/*extends AsyncTask<Void, Integer, Void> {
    ProgressDialog pDialog;
    Boolean uploadStat;
    UploadToFTP utp = new UploadToFTP();

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

    private static final String SERVER_IP = "w0175925.kasserver.com";
    private static final String USERNAME = "f00dd887";
    private static final String PASSWORT = "Nadipat2";

    public UploadTask (Context context, Uri contentUri, String fileName, String format, String category,
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
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Uploading...");
        pDialog.setCancelable(false);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.show();

        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        System.out.println("source url -> " + contentUri);
        System.out.println("filename -> " + fileName);
        System.out.println("desDirectory -> " + "/SocialStudy/" + subFolder);
        uploadStat = new UploadToFTP().ftpUpload1(contentUri, fileName,
                subFolder, SERVER_IP, USERNAME,
                PASSWORT, pDialog, context);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (uploadStat) {
                    if (pDialog != null && pDialog.isShowing()) {
                        pDialog.dismiss();
                    }
                    reviewImageView.setImageBitmap(null);
                    mCurrentPhotoPath = "";
                    photo = null;
                    uploadMessage.setVisibility(View.VISIBLE);
                    UploadSuccess.setVisibility(View.VISIBLE);
                } else {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            context);

                    // Setting Dialog Message
                    alertDialog.setTitle("Error Uploading File");
                    alertDialog
                            .setMessage("Connection lost during upload, please try again!");
                    alertDialog.setCancelable(false);
                    // Setting Icon to Dialog

                    // Setting OK Button
                    alertDialog.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
                    alertDialog.show();
                }
            }
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
        System.out.println("Result-->" + result);
        super.onPostExecute(result);
    }
*/
}
