package com.ndlp.socialstudy.Umfragen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.TinyDB;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewUmfrageActivity extends AppCompatActivity {

    EditText et_umfrageueberschrift;
    EditText et_umfragedatum;
    EditText et_umfragetime;
    TextView tv_einreichen;
    String wortfrage;
    ArrayList<String> wortumfrageoptionen;

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

        final NewUmfrageRecyclerAdapter newUmfrageRecyclerAdapter;
        final ArrayList<Wortumfragenobject> wortumfragenobjects = new ArrayList<>();

        newUmfrageRecyclerAdapter = new NewUmfrageRecyclerAdapter(this, wortumfragenobjects);
        rv_newUmfrage.setAdapter(newUmfrageRecyclerAdapter);
        rv_newUmfrage.setLayoutManager(new LinearLayoutManager(this));

/*        if(getIntent().hasExtra("wortfrage") && getIntent().hasExtra("wortumfrageoptionen")) {
            wortfrage = getIntent().getStringExtra("wortfrage");
            wortumfrageoptionen = getIntent().getStringArrayListExtra("wortumfrageoptionen");


*/
        TinyDB tinyDB = new TinyDB(NewUmfrageActivity.this);
        ArrayList<Integer> anzahleinzelnerUmfragenarray = new ArrayList<>();

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
                    Toast.makeText(NewUmfrageActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
                }


            }
        }

        fabToOptionPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUmfrageActivity.this, WortumfragenActivity.class);
                startActivity(intent);
            }
        });

    }
}
