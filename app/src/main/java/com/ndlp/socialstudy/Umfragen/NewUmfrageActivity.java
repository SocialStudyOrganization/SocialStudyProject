package com.ndlp.socialstudy.Umfragen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.R;

public class NewUmfrageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_umfrage);

        FloatingActionButton fabToOptionPoll = (FloatingActionButton) findViewById(R.id.floating_pollAsOption);

        fabToOptionPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewUmfrageActivity.this, OptionPollActivity.class);
                startActivity(intent);
            }
        });

    }
}
