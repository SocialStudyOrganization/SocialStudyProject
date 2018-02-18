package com.ndlp.socialstudy.Umfragen.UmfrageErstellen;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;
import com.ndlp.socialstudy.activity.TinyDB;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class WortumfragenActivity extends AppCompatActivity {

    ImageView iv_goBackOptionPoll;
    EditText et_headingOptionPoll;
    EditText et_optionPollQuestion;
    EditText et_iteminput;
    Button b_addItem;
    RecyclerView rv_myOptionPollRecyclerView;
    TextView tv_newPollEinreichen;
    TextView tv_wortumfragetopbar;
    TextView tv_wortabfragecontainerhead;
    EditText et_wortumfrage;

    String itemTitle;
    String wortfrage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wortumfrage);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");

        iv_goBackOptionPoll = (ImageView) findViewById(R.id.iv_goBacktoNeueUmfrage);
        et_optionPollQuestion = (EditText) findViewById(R.id.et_wortumfrage);
        et_iteminput = (EditText) findViewById(R.id.et_iteminput);
        b_addItem = (Button) findViewById(R.id.b_addItem);
        rv_myOptionPollRecyclerView = (RecyclerView) findViewById(R.id.rv_myOptionPollRecyclerView);
        tv_newPollEinreichen = (TextView) findViewById(R.id.tv_newPollEinreichen);
        tv_wortumfragetopbar=(TextView) findViewById(R.id.tv_wortumfragetopbar);
        tv_wortabfragecontainerhead=(TextView) findViewById(R.id.tv_wortabfragecontainerhead);
        et_wortumfrage=(EditText) findViewById(R.id.et_wortumfrage);



        //assigning typefaces
        tv_wortumfragetopbar.setTypeface(quicksand_bold);
        tv_newPollEinreichen.setTypeface(quicksand_regular);
        tv_wortabfragecontainerhead.setTypeface(quicksand_regular);
        et_iteminput.setTypeface(quicksand_regular);
        b_addItem.setTypeface(quicksand_regular);
        et_wortumfrage.setTypeface(quicksand_regular);

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
                String lastcharacter = wortfrage.substring(wortfrage.length() - 1);
                Integer anzahlfragezeichen = wortfrage.replaceAll("[^,?]+", "").length();

                if (wortfrage.matches("") || arrayListWortUmfrage.isEmpty() || anzahlfragezeichen > 1) {
                    Toast.makeText(WortumfragenActivity.this, "Fill everything in and check your inputs!",
                            Toast.LENGTH_SHORT).show();

                }
                else {

                    if (arrayListWortUmfrage.size() == 1){
                        Toast.makeText(WortumfragenActivity.this, "Please give more than one option to choose from.",
                                Toast.LENGTH_SHORT).show();

                    }else {
                        if (!lastcharacter.equals("?")){
                            wortfrage = wortfrage + "?";
                        }

                        ArrayList<String> strings = new ArrayList<>();

                        for (WortumfragelistenObject wortumfragelistenObject : arrayListWortUmfrage) {
                            strings.add(wortumfragelistenObject.getItemTitle());
                        }

                        TinyDB tinyDB = new TinyDB(WortumfragenActivity.this);
                        ArrayList<Integer> anzahleinzelnerUmfragenarray = new ArrayList<>();

                        if (tinyDB.getListInt("AnzahlEinzelnerUmfragen").isEmpty()) {
                            anzahleinzelnerUmfragenarray.add(1);
                            tinyDB.putListInt("AnzahlEinzelnerUmfragen", anzahleinzelnerUmfragenarray);

                            int neuerEintrag;

                            neuerEintrag = 1;

                            double wortfragezahl;
                            double optionenzahl;

                            wortfragezahl = neuerEintrag + 0.1;
                            optionenzahl = neuerEintrag + 0.2;

                            tinyDB.putString("" + wortfragezahl, wortfrage);
                            tinyDB.putListString("" + optionenzahl, strings);



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

                        Intent intent = new Intent(WortumfragenActivity.this, NewUmfrageActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    }




            }
        });

        iv_goBackOptionPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentback = new Intent(WortumfragenActivity.this, NewUmfrageActivity.class);
                startActivity(intentback);
                finish();

            }
        });



    }
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
                                Intent intentback = new Intent(WortumfragenActivity.this, NewUmfrageActivity.class);
                                startActivity(intentback);
                                finish();
                                dialog.dismiss();
                                break;
                        }

                    }
                }).create().show();
    }

}
