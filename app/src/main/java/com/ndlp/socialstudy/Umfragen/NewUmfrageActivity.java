package com.ndlp.socialstudy.Umfragen;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.TImeDateRequest;
import com.ndlp.socialstudy.activity.TinyDB;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.widget.Toast.LENGTH_LONG;

public class NewUmfrageActivity extends AppCompatActivity {

    EditText et_umfrageueberschrift;
    EditText et_umfragedatum;
    EditText et_umfragetime;
    TextView tv_einreichen;
    ImageView iv_newumfrageback;
    String wortfrage;
    ArrayList<String> wortumfrageoptionen;

    String umfragethema;
    String enddate;
    String endtime;


    RecyclerView rv_newUmfrage;
    FloatingActionButton fabToOptionPoll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_umfrage);

        et_umfrageueberschrift = (EditText) findViewById(R.id.et_headingNewUmfrage);
        et_umfragedatum = (EditText) findViewById(R.id.et_newUmfrageDate);
        et_umfragetime = (EditText) findViewById(R.id.et_newUmfrageTime);
        tv_einreichen = (TextView) findViewById(R.id.tv_newPollEinreichen);
        rv_newUmfrage = (RecyclerView) findViewById(R.id.rv_newUmfrage);
        fabToOptionPoll = (FloatingActionButton) findViewById(R.id.floating_pollAsOption);
        iv_newumfrageback = (ImageView) findViewById(R.id.iv_newUmfrageback);

        final NewUmfrageRecyclerAdapter newUmfrageRecyclerAdapter;
        final ArrayList<Wortumfragenobject> wortumfragenobjects = new ArrayList<>();

        newUmfrageRecyclerAdapter = new NewUmfrageRecyclerAdapter(this, wortumfragenobjects);
        rv_newUmfrage.setAdapter(newUmfrageRecyclerAdapter);
        rv_newUmfrage.setLayoutManager(new LinearLayoutManager(this));

        //----------------------Handle back button pressed ----------------------------------------

        iv_newumfrageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //-----------------------Load Umfragenobjecte----------------------------------------

        TinyDB tinyDB = new TinyDB(NewUmfrageActivity.this);
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
                    umfragethema = et_umfrageueberschrift.toString();
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

                        String user;
                        SharedPreferences sharedPrefLoginData = NewUmfrageActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                        user = sharedPrefLoginData.getString("username", "");


                        TinyDB tinyDB = new TinyDB(NewUmfrageActivity.this);
                        ArrayList<Integer> anzahleinzelnerUmfragenarray;

                        anzahleinzelnerUmfragenarray = tinyDB.getListInt("AnzahlEinzelnerUmfragen");


                        for (int eintrag = anzahleinzelnerUmfragenarray.size(); eintrag>0; eintrag--) {

                            double wortfragezahl;
                            double optionenzahl;

                            wortfragezahl = eintrag + 0.1;
                            optionenzahl = eintrag + 0.2;

                            try {

                                wortfrage = tinyDB.getString("" + wortfragezahl);
                                wortumfrageoptionen = tinyDB.getListString("" + optionenzahl);



                            } catch (Exception e){
                                Toast.makeText(NewUmfrageActivity.this, e.getMessage(), LENGTH_LONG ).show();
                            }


                        }


 /*
                        UmfragenDataIntoDatabase umfragendataintodatabase = new UmfragenDataIntoDataBase(umfragethema, erstelltamDate
                            , enddate, endtime, user, INSERT, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(NewUmfrageActivity.this);
                        queue.add(umfragendataintodatabase);

*/
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
                                finish();
                                dialog.dismiss();
                                break;
                        }

                    }
                }).create().show();
    }
}
