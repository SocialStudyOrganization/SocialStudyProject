package com.ndlp.socialstudy.ShowKursmitglieder;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;

public class KursmitgliederActivity extends Activity {

    public String urlAddress = "http://h2774251.stratoserver.net/PHP-Dateien/Kursmitglieder/getKursmitglieder.php";
    RecyclerView mRecyclerView;
    String kursid;
    Integer kursidint;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kursmitglieder);


        ImageView imageView = (ImageView) findViewById(R.id.iv_quitkursmitglieder);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_kursmitglieder);
        Drawable dividerDrawable = ContextCompat.getDrawable(KursmitgliederActivity.this, R.drawable.line_divider);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        SharedPreferences sharedPrefLoginData = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        kursid = sharedPrefLoginData.getString("kursid", "");



        new RefreshKursmitglieder(KursmitgliederActivity.this, urlAddress, kursid, mRecyclerView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
