package com.ndlp.socialstudy.NewsFeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        Bundle  bundle = getArguments();
        String message = bundle.getString("message");

        TextView textView = (TextView) rootView.findViewById(R.id.tv_newsanzeigen);

        textView.setText(message);
        textView.setMovementMethod(new ScrollingMovementMethod());


        return rootView;
    }




}
