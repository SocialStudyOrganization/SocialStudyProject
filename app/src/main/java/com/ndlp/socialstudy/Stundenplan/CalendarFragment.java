package com.ndlp.socialstudy.Stundenplan;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.TImeDateRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


/**
 * Fragment to display a calendar
 */

public class CalendarFragment extends Fragment {
    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        String finalURL;
        String currentURL ="";

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("MST"));
        calendar.set(Calendar.DAY_OF_WEEK, 2);

        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH)+1;
        int day1 = calendar.get(Calendar.DAY_OF_MONTH);

        String firstdayofweek = day1 + "-" + month1+ "-" + year1;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date firstdayofweekdate = format.parse(firstdayofweek);
            calendar.setTime(firstdayofweekdate);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            int yeartoinsert = calendar.get(Calendar.YEAR);
            int monthtoinsert = calendar.get(Calendar.MONTH)+1;
            int daytoinsert = calendar.get(Calendar.DAY_OF_MONTH);

            currentURL = "https://rapla.dhbw-stuttgart.de/rapla?key=txB1FOi5xd1wUJBWuX8lJuJKbCD_reecDR2TGAHW6TjNsRiZcb6ATl4_UtECoofV%3B&day="
                    +daytoinsert+"&month="
                    +monthtoinsert+"&year="
                    +yeartoinsert+"&next=%3E%3E";


        } catch (ParseException e) {
            e.printStackTrace();
        }


        String postURL = "https://rapla.dhbw-stuttgart.de/rapla?key=txB1FOi5xd1wUJBWuX8lJuJKbCD_reecDR2TGAHW6TjNsRiZcb6ATl4_UtECoofV%3B&day=&month=2&year=2018&next=%3E%3E";
        WebView webView = (WebView) rootView.findViewById(R.id.webView);

        if (currentURL.equals(""))
            finalURL = postURL;
        else
            finalURL = currentURL;


        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);

        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getContext(), "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(finalURL);





        return rootView;
    }



}
