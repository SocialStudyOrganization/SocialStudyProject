package com.ndlp.socialstudy.NewsFeed;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen.BasicUmfragenFragment;


public class NewsAnzeigeFragment extends Fragment {
    public static NewsAnzeigeFragment newInstance(){
        NewsAnzeigeFragment newsAnzeigeFragment = new NewsAnzeigeFragment();
        return newsAnzeigeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_anzeige, container, false);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(getContext().getAssets(),  "fonts/Quicksand-Bold.otf");

        Bundle  bundle = getArguments();
        String message = bundle.getString("message");
        String topic = bundle.getString("topic");
        String header = bundle.getString("header");

        TextView tv_newsanzeigen = (TextView) rootView.findViewById(R.id.tv_newsanzeigen);
        TextView tv_newscontainerhead = (TextView) rootView.findViewById(R.id.tv_newscontainerhead);
        TextView tv_newsThema = (TextView) rootView.findViewById(R.id.tv_newsThema);

        tv_newsanzeigen.setTypeface(quicksand_regular);
        tv_newscontainerhead.setTypeface(quicksand_regular);
        tv_newsThema.setTypeface(quicksand_regular);

        tv_newsanzeigen.setMovementMethod(new ScrollingMovementMethod());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tv_newsanzeigen.setText(Html.fromHtml(message,Html.FROM_HTML_MODE_LEGACY));
        } else {
            tv_newsanzeigen.setText(Html.fromHtml(message));
        }

        tv_newsanzeigen.setMovementMethod(LinkMovementMethod.getInstance());

        tv_newscontainerhead.setText(header);
        tv_newsThema.setText(topic);

        return rootView;
    }




}
