package com.ndlp.socialstudy.Umfragen;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;
import com.ndlp.socialstudy.activity.TinyDB;

import java.util.ArrayList;

public class WortumfragenActivity extends AppCompatActivity {

    ImageView iv_goBackOptionPoll;
    EditText et_headingOptionPoll;
    EditText et_optionPollQuestion;
    EditText et_iteminput;
    Button b_addItem;
    RecyclerView rv_myOptionPollRecyclerView;
    TextView tv_newPollEinreichen;

    String itemTitle;
    String wortfrage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wortumfrage);

        iv_goBackOptionPoll = (ImageView) findViewById(R.id.iv_goBacktoNeueUmfrage);
        et_optionPollQuestion = (EditText) findViewById(R.id.et_wortumfrage);
        et_iteminput = (EditText) findViewById(R.id.et_iteminput);
        b_addItem = (Button) findViewById(R.id.b_addItem);
        rv_myOptionPollRecyclerView = (RecyclerView) findViewById(R.id.rv_myOptionPollRecyclerView);
        tv_newPollEinreichen = (TextView) findViewById(R.id.tv_newPollEinreichen);

        //intern
        final WortumfrageOptionenRecyclerAdapter adapter;
        final ArrayList<WortumfragelistenObject> arrayListWortUmfrage = new ArrayList<>();

        adapter = new WortumfrageOptionenRecyclerAdapter(this, arrayListWortUmfrage);
        rv_myOptionPollRecyclerView.setAdapter(adapter);
        rv_myOptionPollRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //extern
        final NewUmfrageRecyclerAdapter newUmfrageRecyclerAdapter;
        final ArrayList<Wortumfragenobject> wortumfragenobjects = new ArrayList<>();

        newUmfrageRecyclerAdapter = new NewUmfrageRecyclerAdapter(this, wortumfragenobjects);



        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv_myOptionPollRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        b_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemTitle = et_iteminput.getText().toString();

                if (itemTitle.matches("")) {
                    Toast.makeText(WortumfragenActivity.this, "Please type in something before you confirm.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    WortumfragelistenObject current = new WortumfragelistenObject(itemTitle);
                    arrayListWortUmfrage.add(current);
                    adapter.notifyDataSetChanged();
                    et_iteminput.setText("");
                }
            }
        });

        tv_newPollEinreichen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                wortfrage = et_optionPollQuestion.getText().toString();

                if (wortfrage.matches("") || arrayListWortUmfrage.isEmpty()) {
                    Toast.makeText(WortumfragenActivity.this, "You cannot check out if you didn't enter something",
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    ArrayList<String> strings = new ArrayList<>();

                    for (WortumfragelistenObject wortumfragelistenObject : arrayListWortUmfrage) {
                        strings.add(wortumfragelistenObject.getItemTitle());
                    }

                    TinyDB tinyDB = new TinyDB(WortumfragenActivity.this);
                    ArrayList<Integer> anzahleinzelnerUmfragenarray = new ArrayList<>();

                    if (tinyDB.getListInt("AnzahlEinzelnerUmfragen").isEmpty()){
                        anzahleinzelnerUmfragenarray.add(1);
                        tinyDB.putListInt("AnzahlEinzelnerUmfragen", anzahleinzelnerUmfragenarray);

                    } else {

                        anzahleinzelnerUmfragenarray = tinyDB.getListInt("AnzahlEinzelnerUmfragen");

                        int neuerEintrag;
                        neuerEintrag = anzahleinzelnerUmfragenarray.size() + 1;

                        anzahleinzelnerUmfragenarray.add(neuerEintrag);
                        tinyDB.putListInt("AnzahlEinzelnerUmfragen", anzahleinzelnerUmfragenarray);

                        double wortfragezahl;
                        double optionenzahl;

                        wortfragezahl = neuerEintrag + 0.1;
                        optionenzahl = neuerEintrag + 0.2;

                        tinyDB.putString("" + wortfragezahl, wortfrage);
                        tinyDB.putListString("" + optionenzahl, strings);




                    }



                    tinyDB.putString("1.1" , wortfrage);
                    tinyDB.putListString("1.2" , strings);




                    Intent intent = new Intent(WortumfragenActivity.this, NewUmfrageActivity.class);
                    intent.putExtra("wortfrage", wortfrage);
                    intent.putExtra("wortumfrageoptionen", strings);
                    startActivity(intent);
                    finish();

                }


            }
        });

        iv_goBackOptionPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentback = new Intent(WortumfragenActivity.this, NewUmfrageActivity.class);
                startActivity(intentback);

            }
        });



    }

}
