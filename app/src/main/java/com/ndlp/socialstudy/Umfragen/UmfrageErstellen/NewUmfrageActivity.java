package com.ndlp.socialstudy.Umfragen.UmfrageErstellen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.TImeDateRequest;
import com.ndlp.socialstudy.activity.TinyDB;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.widget.Toast.LENGTH_LONG;

public class NewUmfrageActivity extends AppCompatActivity {

    EditText et_umfrageueberschrift;
    EditText et_headingNewUmfrage;
    EditText et_umfragedatum;
    EditText et_umfragetime;
    TextView tv_einreichen;
    TextView tv_umfrageheaderbar;
    TextView tv_umfragecontainerhead;

    ImageView iv_newumfrageback;
    String wortfrage;

    ArrayList<String> wortumfrageoptionen;

    ArrayList<String> datatoserverarray = new ArrayList<String>();

    String umfragethema;
    String enddate;
    String endtime;


    RecyclerView rv_newUmfrage;
    FloatingActionButton fabToOptionPoll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_umfrage);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-LightItalic.otf");

        et_umfrageueberschrift = (EditText) findViewById(R.id.et_headingNewUmfrage);
        et_umfragedatum = (EditText) findViewById(R.id.et_newUmfrageDate);
        et_umfragetime = (EditText) findViewById(R.id.et_newUmfrageTime);
        tv_einreichen = (TextView) findViewById(R.id.tv_newPollEinreichen);
        rv_newUmfrage = (RecyclerView) findViewById(R.id.rv_newUmfrage);
        fabToOptionPoll = (FloatingActionButton) findViewById(R.id.floating_pollAsOption);
        iv_newumfrageback = (ImageView) findViewById(R.id.iv_newUmfrageback);
        tv_umfrageheaderbar = (TextView) findViewById(R.id.tv_umfrageheaderbar);
        tv_umfragecontainerhead = (TextView) findViewById(R.id.tv_umfragecontainerhead);

        //assigning typefaces
        tv_umfrageheaderbar.setTypeface(quicksand_bold);
        tv_umfragecontainerhead.setTypeface(quicksand_regular);
        et_umfrageueberschrift.setTypeface(quicksand_regular);
        et_umfragedatum.setTypeface(quicksand_regular);
        et_umfragetime.setTypeface(quicksand_regular);
        tv_einreichen.setTypeface(quicksand_regular);

        final NewUmfrageRecyclerAdapter newUmfrageRecyclerAdapter;
        final ArrayList<Wortumfragenobject> wortumfragenobjects = new ArrayList<>();

        datatoserverarray.clear();

        final TinyDB tinyDB = new TinyDB(NewUmfrageActivity.this);


        newUmfrageRecyclerAdapter = new NewUmfrageRecyclerAdapter(this, wortumfragenobjects);
        rv_newUmfrage.setAdapter(newUmfrageRecyclerAdapter);
        rv_newUmfrage.setLayoutManager(new LinearLayoutManager(this));

        if (!tinyDB.getString("ueberschrift").isEmpty()){
            et_umfrageueberschrift.setText(tinyDB.getString("ueberschrift"));
        }
        if (!tinyDB.getString("datum").isEmpty()){
            et_umfragedatum.setText(tinyDB.getString("datum"));
        }
        if (!tinyDB.getString("uhrzeit").isEmpty()){
            et_umfragetime.setText(tinyDB.getString("uhrzeit"));
        }



        //----------------------Handle back button pressed ----------------------------------------

        iv_newumfrageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //-----------------------Load Umfragenobjecte----------------------------------------


        ArrayList<Integer> anzahleinzelnerUmfragenarray;

        if (!tinyDB.getListInt("AnzahlEinzelnerUmfragen").isEmpty())     {

            anzahleinzelnerUmfragenarray = tinyDB.getListInt("AnzahlEinzelnerUmfragen");


            for (int eintrag = anzahleinzelnerUmfragenarray.size(); eintrag>0; eintrag--) {

                double wortfragezahl;
                double optionenzahl;

                wortfragezahl = eintrag + 0.1;
                optionenzahl = eintrag + 0.2;

                try {

                    wortfrage = tinyDB.getString("" + wortfragezahl);
                    wortumfrageoptionen = tinyDB.getListString("" + optionenzahl);

                    Wortumfragenobject wortumfragenobject = new Wortumfragenobject(wortfrage, wortumfrageoptionen);
                    wortumfragenobjects.add(wortumfragenobject);
                    newUmfrageRecyclerAdapter.notifyDataSetChanged();

                } catch (Exception e){
                    Toast.makeText(NewUmfrageActivity.this, e.getMessage(), LENGTH_LONG ).show();
                }


            }
        }

        //-------------------- Create new Wortumfrage ---------------------------------------------


        fabToOptionPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!et_umfrageueberschrift.equals("") ){
                    tinyDB.putString("ueberschrift", et_umfrageueberschrift.getText().toString());
                }

                if (!et_umfragedatum.equals("") ){
                    tinyDB.putString("datum", et_umfragedatum.getText().toString());
                }

                if (!et_umfragetime.equals("") ){
                    tinyDB.putString("uhrzeit", et_umfragetime.getText().toString());
                }

                Intent intent = new Intent(NewUmfrageActivity.this, WortumfragenActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //-------------------- Einreichen ---------------------------------------------------------


        tv_einreichen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){



                if (et_umfrageueberschrift.equals("") || et_umfragedatum.equals("") || et_umfragetime.equals("")
                         || wortumfragenobjects.isEmpty()){

                    Toast.makeText(NewUmfrageActivity.this, "You are missing some data", LENGTH_LONG).show();

                }else {

                    //gets the userinputs out of the edit texts
                    umfragethema = et_umfrageueberschrift.getText().toString().trim();
                    enddate = et_umfragedatum.getText().toString().trim();
                    endtime = et_umfragetime.getText().toString().trim();



                    //checks the format of the date
                    if (!enddate.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")
                            || !endtime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")){

                        Toast.makeText(NewUmfrageActivity.this, "Check the format of your inputs pls", LENGTH_LONG).show();

                    } else {

                        //gets the date

                        String erstelltamDate;
                        TImeDateRequest tImeDateRequest = new TImeDateRequest();
                        erstelltamDate = tImeDateRequest.getDate();

                        //  gets the username out of sharedPrefs LoginData

                        Integer userint;
                        SharedPreferences sharedPrefLoginData = NewUmfrageActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        userint = sharedPrefLoginData.getInt("matrikelnummer", 1);
                        String user = userint.toString();



                        final TinyDB tinyDB = new TinyDB(NewUmfrageActivity.this);
                        ArrayList<Integer> anzahleinzelnerUmfragenarray;
                        anzahleinzelnerUmfragenarray = tinyDB.getListInt("AnzahlEinzelnerUmfragen");

                        Integer anzahleinzelnerumfrageninteger = anzahleinzelnerUmfragenarray.size();
                        String anzahleinzelnerumfragen = "" + anzahleinzelnerumfrageninteger;



                        for (int eintrag = anzahleinzelnerUmfragenarray.size(); eintrag>0; eintrag--) {

                            double wortfragezahl;
                            double optionenzahl;

                            wortfragezahl = eintrag + 0.1;
                            optionenzahl = eintrag + 0.2;

                                wortfrage = tinyDB.getString("" + wortfragezahl);
                                datatoserverarray.add(wortfrage);

                                wortumfrageoptionen = tinyDB.getListString("" + optionenzahl);


                                for (int optionenzahlimarray = wortumfrageoptionen.size(); optionenzahlimarray>0; optionenzahlimarray--) {

                                    String option;

                                    option = wortumfrageoptionen.get(optionenzahlimarray-1);

                                    datatoserverarray.add(option);
                                }

                                datatoserverarray.add("%");
                        }

                        String arraytostring = datatoserverarray.toString();

                        arraytostring = arraytostring.replace("[", "");
                        arraytostring = arraytostring.replace("]", "");
                        Log.i("Umfragethema:", umfragethema);
                        Log.i("erstelltamDate:", erstelltamDate);
                        Log.i("enddate:", enddate);
                        Log.i("endtime:", endtime);
                        Log.i("user:", user);
                        Log.i("arraytostring:", arraytostring);



                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                //  gets called if a response is transmitted
                                try {
                                    Log.i("tagconvertstr", response);
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");


                                    if (success) {
                                        Toast.makeText(NewUmfrageActivity.this, jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                                        tinyDB.remove("ueberschrift");
                                        tinyDB.remove("datum");
                                        tinyDB.remove("uhrzeit");

                                        finish();

                                    } else {

                                        Toast.makeText(NewUmfrageActivity.this, jsonResponse.getString("error_msg"), LENGTH_LONG).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        };


                        UmfragenDataIntoDatabase umfragendataintodatabase = new UmfragenDataIntoDatabase(umfragethema, erstelltamDate
                            , enddate, endtime, user, arraytostring, anzahleinzelnerumfragen, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(NewUmfrageActivity.this);
                        queue.add(umfragendataintodatabase);

                    }

                }

            }
        });


    }

    //------------------------------ Handle back pressed ------------------------------------------

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                            case BUTTON_POSITIVE:

                                TinyDB tinyDB = new TinyDB(NewUmfrageActivity.this);

                                tinyDB.remove("ueberschrift");
                                tinyDB.remove("datum");
                                tinyDB.remove("uhrzeit");

                                finish();
                                dialog.dismiss();
                                break;
                        }

                    }
                }).create().show();
    }
}
