package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ndlp.socialstudy.R;

import org.w3c.dom.Text;

/**
 * Activity to give feedback to the producers in form of an email
 */

public class FeedbackActivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    Button b_feedbackgeben;
    String email = "StudyWire@gmx.de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-LightItalic.otf");

        //  set toolbar and adjust title
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("");

        //find title workaround & textbox & button
        TextView tv_toolbar_title_workaround_feedback = (TextView) findViewById(R.id.tv_toolbar_title_workaround_feedback);
        TextView tv_feedback_msg = (TextView) findViewById(R.id.tv_feedback_msg);
        b_feedbackgeben = (Button) findViewById(R.id.b_feedbackgeben);

        //adjust typefaces
        tv_toolbar_title_workaround_feedback.setTypeface(quicksand_bold);
        tv_feedback_msg.setTypeface(quicksand_regular);
        b_feedbackgeben.setTypeface(quicksand_bold);




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
