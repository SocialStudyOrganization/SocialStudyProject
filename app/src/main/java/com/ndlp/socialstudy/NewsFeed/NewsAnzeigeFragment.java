package com.ndlp.socialstudy.NewsFeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
        Button button = (Button) rootView.findViewById(R.id.b_newsanzeigenzurück);

        textView.setText(message);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, newsFeedFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }




}
