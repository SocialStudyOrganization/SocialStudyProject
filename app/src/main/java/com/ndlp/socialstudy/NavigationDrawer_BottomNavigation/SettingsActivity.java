package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ndlp.socialstudy.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //  set toolbar and adjust title
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("");

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getAssets(),  "fonts/Quicksand-Bold.otf");

        TextView tv_toolbar_title_workaround_settings = (TextView) findViewById(R.id.tv_toolbar_title_workaround_settings);
        TextView tv_notif = (TextView) findViewById(R.id.textView3);
        TextView tv_wlan = (TextView) findViewById(R.id.textView4);

        tv_toolbar_title_workaround_settings.setTypeface(quicksand_bold);
        tv_notif.setTypeface(quicksand_regular);
        tv_wlan.setTypeface(quicksand_regular);


    }
}
