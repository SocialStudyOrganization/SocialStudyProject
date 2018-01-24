package com.ndlp.socialstudy.Answers.Vorlesungen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.GeneralFileFolder.FileUploader;
import com.ndlp.socialstudy.GeneralFileFolder.RefreshfromDatabase;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;
import com.ndlp.socialstudy.activity.TImeDateRequest;


public class KonstruktionslehreAnswersFragment extends Fragment {
    public static KonstruktionslehreAnswersFragment newInstance() {
        KonstruktionslehreAnswersFragment konstruktionslehreAnswersFragment = new KonstruktionslehreAnswersFragment();
        return konstruktionslehreAnswersFragment;
    }

    //--------------------Variablendeklaration-----------------------------------------

    private FloatingActionButton floatingasPDF;
    private FloatingActionButton floatingGallery;

    private static final int READ_REQUEST_CODE = 1;
    private static final int SELECT_PICTURE = 1;
    Uri fileUri;
    Uri imageUri;

    //  location of the php script on server
    final static String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/select_answers.php";

    public String skriptname;
    public String format;
    public String category = "konstruktionslehre";
    public String subFolder = "Answers";
    public String date;
    public String time;
    public String user;

    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

//---------------------------------ONCREATE----------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dokumentendarstellung, container, false);


        //  initialize the recyclerView of the data files
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_dokumentendarstellung);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_divider);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        floatingasPDF = (FloatingActionButton) rootView.findViewById(R.id.floating_asPDFFile);
        floatingGallery = (FloatingActionButton) rootView.findViewById(R.id.floating_fromGallery);

        fileUri = null;
        imageUri = null;
        skriptname = null;

        //  gets the username out of sharedPrefs LoginData
        SharedPreferences sharedPrefLoginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        user = sharedPrefLoginData.getString("firstname", "");

        //  calls DownloaderClass and puts urlAddress as parameter to refresh the recyclerView
        new RefreshfromDatabase(getActivity(), urlAddress, mRecyclerView, category, subFolder);

        //sets refreshlistener on Swiperefreshlayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                new RefreshfromDatabase(getActivity(), urlAddress, mRecyclerView, category, subFolder);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //  set onClickListener on the floating item as PDF
        floatingasPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  get all files on the device with type pdf -> highlighted and clickable
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");

                format = "PDF";

                TImeDateRequest tImeDateRequest = new TImeDateRequest();
                date = tImeDateRequest.getDate();
                time = tImeDateRequest.getTime();

                //  calls startAcivityForResult method
                startActivityForResult(intent, READ_REQUEST_CODE);

            }
        });

        floatingGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                format = "Image";

                TImeDateRequest tImeDateRequest = new TImeDateRequest();
                date = tImeDateRequest.getDate();
                time = tImeDateRequest.getTime();

                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);

            }
        });

        return rootView;
    }

    //  if we receive a result from volley this gets called
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //  if result code == ok and If the request code seen here doesn't match, it's the
        //  response to some other intent, and the code below shouldn't run at all.
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            //if the intent was from the pdf fab
            if (data != null){


                fileUri = data.getData();
                Cursor resultCursor = getActivity().getContentResolver().query(fileUri, null, null, null, null);

                //  move to first row
                resultCursor.moveToFirst();

                //  get PDF name out of the file and set it as PDFName
                skriptname = resultCursor.getString(resultCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));


                //  starts upload task to the server
                FileUploader fileUploader = new FileUploader(getActivity(), fileUri, skriptname, format, category, date, time, user, subFolder, urlAddress, mRecyclerView);
                fileUploader.execute();


            }
        }


    }


}
