package com.ndlp.socialstudy.ShowKursmitglieder;

import android.app.Activity;
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

    public String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/getKursmitglieder.php";
    RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kursmitglieder);


        ImageView imageView = (ImageView) findViewById(R.id.iv_quitkursmitglieder);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_kursmitglieder);
        Drawable dividerDrawable = ContextCompat.getDrawable(KursmitgliederActivity.this, R.drawable.line_divider);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        new RefreshKursmitglieder(KursmitgliederActivity.this, urlAddress, mRecyclerView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
