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
 * Activity to report bugs and errors to the producers
 */

public class FehlerMeldenAcivity extends AppCompatActivity {

    Toolbar mActionBarToolbar;
    Button b_fehlermelden;
    String email = "patrick-nadler@gmx.de";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fehler_melden);

        //  set toolbar and adjust title
        mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Fehler melden");


        b_fehlermelden = (Button) findViewById(R.id.b_fehlermelden);

        //  start email chooser to give feedback
        b_fehlermelden.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    final Intent emailIntent = new Intent(
                            Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");

                    //  transfer the emailaddress to the provider
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});

                    //  create Chooser to choose between different providers
                    startActivity(Intent.createChooser(emailIntent,
                            "Sending email..."));

                // on error show text
                } catch (Throwable t) {
                    Toast.makeText(FehlerMeldenAcivity.this,
                            "Request failed try again: " + t.toString(),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
