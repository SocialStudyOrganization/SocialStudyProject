package com.ndlp.socialstudy.Umfragen;

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

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;

import java.util.ArrayList;

public class OptionPollActivity extends AppCompatActivity {

    ImageView iv_goBackOptionPoll;
    EditText et_headingOptionPoll;
    EditText et_optionPollQuestion;
    EditText et_iteminput;
    Button b_addItem;
    RecyclerView rv_myOptionPollRecyclerView;
    TextView tv_newPollEinreichen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_poll);

        iv_goBackOptionPoll = (ImageView) findViewById(R.id.iv_goBackOptionPoll);
        et_headingOptionPoll = (EditText) findViewById(R.id.et_headingOptionPoll);
        et_optionPollQuestion = (EditText) findViewById(R.id.et_optionPollQuestion);
        et_iteminput = (EditText) findViewById(R.id.et_iteminput);
        b_addItem = (Button) findViewById(R.id.b_addItem);
        rv_myOptionPollRecyclerView = (RecyclerView) findViewById(R.id.rv_myOptionPollRecyclerView);
        tv_newPollEinreichen = (TextView) findViewById(R.id.tv_newPollEinreichen);

        final OptionPollItemsRecyclerAdapter adapter;
        final ArrayList<OptionPollItemOpject> data = new ArrayList<>();

        adapter = new OptionPollItemsRecyclerAdapter(this, data);
        rv_myOptionPollRecyclerView.setAdapter(adapter);
        rv_myOptionPollRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv_myOptionPollRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        b_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemTitle = et_iteminput.getText().toString();

                OptionPollItemOpject current = new OptionPollItemOpject(itemTitle);
                data.add(current);
                adapter.notifyDataSetChanged();

            }
        });



    }
}
