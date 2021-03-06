package com.ndlp.socialstudy.GeneralFileFolder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteFragment;
import com.ndlp.socialstudy.activity.TImeDateRequest;
import com.ndlp.socialstudy.activity.TinyDB;


import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;


import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static android.os.Build.HOST;
import static android.provider.Telephony.Carriers.PASSWORD;


public class ImageUpload extends AppCompatActivity implements View.OnClickListener {

    private Button buttonChoose;
    private RelativeLayout buttonUpload;
    private ImageView imageView;
    private EditText editText;
    private TextView buttonlabel;
    private TextView headerbar;
    private TextView containerhead;

    private static final int STORAGE_PERMISSION_CODE = 1;
    private static final int PICK_IMAGE_REQUEST = 2;

    private static final String SERVER_IP = "w0175925.kasserver.com";
    private static final String USERNAME = "f00dd887";
    private static final String PASSWORT = "Nadipat2";

    private Uri filePath;
    private String skriptbezeichnung;
    private String extension;
    private Bitmap bitmap;

    private String category;
    private String format;
    private String user;
    private String date;
    private String time;

    private static final String UPLOAD_URL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");

        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        format = intent.getStringExtra("format");

        requestStoragePermission();

        buttonChoose = (Button) findViewById(R.id.b_chooseImage);
        buttonUpload = (RelativeLayout) findViewById(R.id.b_uploadImage);
        buttonlabel = (TextView) findViewById(R.id.tv_imageUploadeinreichen);
        containerhead = (TextView) findViewById(R.id.tv_imageUploadcontainerhead);
        headerbar = (TextView) findViewById(R.id.tv_imageUploadheaderbar);
        imageView = (ImageView) findViewById(R.id.iv_imagePreview);
        editText = (EditText) findViewById(R.id.et_imageName);

        buttonChoose.setTypeface(quicksand_regular);
        buttonlabel.setTypeface(quicksand_regular);
        headerbar.setTypeface(quicksand_bold);
        editText.setTypeface(quicksand_regular);
        containerhead.setTypeface(quicksand_regular);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    private void requestStoragePermission(){
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select the image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();

            Cursor resultCursor = this.getContentResolver().query(filePath, null, null, null, null);

            //  move to first row
            resultCursor.moveToFirst();

            //  get PDF name out of the file and set it as PDFName
            skriptbezeichnung = resultCursor.getString(resultCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            String filenameArray[] = skriptbezeichnung.split("\\.");
            extension = "." + filenameArray[filenameArray.length-1];

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){

            }
        }
    }

    //returns absolut path of image
    private String getPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);

        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null


        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    private void uploadImage(){
        String name  = editText.getText().toString().trim() + extension;

        //real path
        //String path  = getPath(filePath);
        ///storage/emulated/0/DCIM/Camera/IMG_20180125_225916.jpg


        SharedPreferences sharedPrefLoginData = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user = sharedPrefLoginData.getString("firstname", "");

        TImeDateRequest tImeDateRequest = new TImeDateRequest();
        date = tImeDateRequest.getDate();
        time = tImeDateRequest.getTime();

        TinyDB tinyDB = new TinyDB(this);
        tinyDB.putString("fileUri", filePath + "");
        tinyDB.putString("format", format);
        tinyDB.putString("filename", name);

        //pull down keyboarrd
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


        finish();





    }



    @Override
    public void onClick(View view) {
        if (view == buttonChoose){
            showFileChooser();
        }
        if (view == buttonUpload){
            uploadImage();
        }

    }
}
