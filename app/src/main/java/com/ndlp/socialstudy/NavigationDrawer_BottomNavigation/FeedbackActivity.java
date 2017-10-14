package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ndlp.socialstudy.R;

/**
 * Activity to give feedback to the producers in form of an email
 */

public class FeedbackActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    Button b_feedbackgeben;
    String email = "patrick-nadler@gmx.de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //  set toolbar and adjust title
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("FeedbackActivity geben");


        b_feedbackgeben = (Button) findViewById(R.id.b_feedbackgeben);

        //  start email chooser to give feedback
        b_feedbackgeben.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    final Intent emailIntent = new Intent(
                            Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");

                    //  transfer the emailaddress to the provider
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ email });

                    //  create Chooser to choose between different providers
                    startActivity(Intent.createChooser(emailIntent,
                            "Sending email..."));

                //  on error show text
                } catch (Throwable t) {
                    Toast.makeText(FeedbackActivity.this,
                            "Request failed try again: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
