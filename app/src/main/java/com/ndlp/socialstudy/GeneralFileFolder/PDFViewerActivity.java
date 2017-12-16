package com.ndlp.socialstudy.GeneralFileFolder;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.ndlp.socialstudy.R;

public class PDFViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        Uri contentPDFUri;

        String pathtoPDF = getIntent().getExtras().getString("pathtoPDF");
        contentPDFUri = Uri.parse(pathtoPDF);

        pdfView.fromUri(contentPDFUri).load();

    }
}
