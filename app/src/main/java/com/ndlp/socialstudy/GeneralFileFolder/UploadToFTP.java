package com.ndlp.socialstudy.GeneralFileFolder;


import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.io.CopyStreamAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class UploadToFTP {
/*    public FTPClient mFTPClient = null;
    String host;
    String username;
    String password;
    CopyStreamAdapter streamListener;
    ProgressDialog pDialog;
    boolean status = false;
    Context context;

    public boolean ftpUpload1(Uri contentUri, String desFileName,

                              String subfolder, String host, String username, String password,
                              final ProgressDialog pDialog, Context context) {
        this.pDialog = pDialog;
        this.context = context;
        this.host = host;
        this.username = username;
        this.password = password;
        mFTPClient = new FTPClient();

        try {
            mFTPClient.connect(host); // connecting to the host
            mFTPClient.login(username, password); // Authenticate using username
            // and password
            mFTPClient.changeWorkingDirectory("/SocialStudy/" + subfolder); // change directory
            System.out.println("Dest Directory-->" + "/SocialStudy/" + subfolder); // to that
            // directory
            // where image
            // will be
            // uploaded
            mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);

            BufferedInputStream buffIn = null;



            final File file = new File(srcFilePath);

            System.out.println("on going file-->" + srcFilePath);
            buffIn = new BufferedInputStream(new FileInputStream(file), 8192);

            mFTPClient.enterLocalPassiveMode();
            streamListener = new CopyStreamAdapter() {

                @Override
                public void bytesTransferred(long totalBytesTransferred,
                                             int bytesTransferred, long streamSize) {
                    // this method will be called everytime some
                    // bytes are transferred
                    // System.out.println("Stream size" + file.length());
                    // System.out.println("byte transfeedd "
                    // + totalBytesTransferred);

                    int percent = (int) (totalBytesTransferred * 100 / file.length());
                    pDialog.setProgress(percent);

                    if (totalBytesTransferred == file.length()) {
                        System.out.println("100% transfered");

                        removeCopyStreamListener(streamListener);

                    }

                }

            };
            mFTPClient.setCopyStreamListener(streamListener);

            status = mFTPClient.storeFile(desFileName, buffIn);
            System.out.println("Status Value-->" + status);
            buffIn.close();
            mFTPClient.logout();
            mFTPClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
    */
}