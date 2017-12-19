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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

        final WortumfrageRecyclerAdapter adapter;
        final ArrayList<WortumfrageObject> arrayListWortUmfrage = new ArrayList<>();

        adapter = new WortumfrageRecyclerAdapter(this, arrayListWortUmfrage);
        rv_myOptionPollRecyclerView.setAdapter(adapter);
        rv_myOptionPollRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                    WortumfrageObject current = new WortumfrageObject(itemTitle);
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
                    ArrayList<String> strings = new ArrayList<String>();
                    for (WortumfrageObject wortumfrageObject : arrayListWortUmfrage) {
                        strings.add(wortumfrageObject.getItemTitle());
                    }

                    TinyDB tinyDB = new TinyDB(WortumfragenActivity.this);
                    tinyDB.putListString("Wortumfrage1", strings);
                    tinyDB.putString("Wortumfragethema1", wortfrage);

                    //ArrayList datastring = tinyDB.getListString("Wortumfrage1");
                    //Toast.makeText(WortumfragenActivity.this, ""+datastring, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(WortumfragenActivity.this, NewUmfrageActivity.class);
                    startActivity(intent);
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
