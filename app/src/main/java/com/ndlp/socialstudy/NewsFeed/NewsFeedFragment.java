package com.ndlp.socialstudy.NewsFeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndlp.socialstudy.R;

import java.util.ArrayList;

/**
 * Fragment to show the newsfeed on surveys and events
 */

public class NewsFeedFragment extends Fragment {
    public static NewsFeedFragment newInstance() {
        NewsFeedFragment fragment = new NewsFeedFragment();
        return fragment;
    }

    RecyclerView mRecyclerViewNewsFeed;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsFeedRecyclerAdapter newsFeedRecyclerAdapter;
    ArrayList<NewsFeedObject> newsFeedObjectsArrayList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        newsFeedObjectsArrayList = new ArrayList<>();


        mRecyclerViewNewsFeed = (RecyclerView) rootView.findViewById(R.id.rv_newsfeed);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutNewsFeed);

        newsFeedRecyclerAdapter = new NewsFeedRecyclerAdapter(getContext(), newsFeedObjectsArrayList);
        mRecyclerViewNewsFeed.setAdapter(newsFeedRecyclerAdapter);
        mRecyclerViewNewsFeed.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RefreshNewsFeedFromDatabase(getActivity(), mRecyclerViewNewsFeed);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        new RefreshNewsFeedFromDatabase(getActivity(), mRecyclerViewNewsFeed);

        return rootView;
    }
}