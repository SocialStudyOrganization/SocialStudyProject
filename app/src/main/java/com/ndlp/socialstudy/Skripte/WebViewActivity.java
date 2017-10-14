package com.ndlp.socialstudy.Skripte;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.ndlp.socialstudy.R;

/**
 * Activity to start the webView of a pdf
 */

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String pdf_name = getIntent().getExtras().getString("pdf_name");

        //  connection to internet and locate recommeded pdf file
        WebView webview = (WebView) findViewById(R.id.skripte_webView);
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf = "http://www.hellownero.de/pdf/" + pdf_name;
        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
        finish();
    }
}
