package com.ndlp.socialstudy.NewsFeed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ndlp.socialstudy.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    public static NotificationFragment newInstance(){
        NotificationFragment notificationFragment = new NotificationFragment();
        return notificationFragment;
    }

    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    NewsFeedRecyclerAdapter NewsFeedRecyclerAdapter;
    ArrayList<NewsFeedObject> ObjectsArrayList;
    String source = "Notification";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        ObjectsArrayList = new ArrayList<>();


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_notification);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutNotification);

        NewsFeedRecyclerAdapter = new NewsFeedRecyclerAdapter(getContext(), ObjectsArrayList);
        mRecyclerView.setAdapter(NewsFeedRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RefreshNewsFeedFromDatabase(getActivity(), mRecyclerView, source);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        new RefreshNewsFeedFromDatabase(getActivity(), mRecyclerView, source);




        return rootView;
    }
}
