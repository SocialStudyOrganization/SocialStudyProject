package com.ndlp.socialstudy.Umfragen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ndlp.socialstudy.R;


public class UmfragenFragment extends Fragment {
    public static UmfragenFragment newInstance() {
        UmfragenFragment umfragenFragment = new UmfragenFragment();
        return umfragenFragment;
    }

    //--------------------Variablendeklaration-----------------------------------------

    private FloatingActionButton floatingaddUmfrage;


    RecyclerView mRecyclerViewUmfragen;
    SwipeRefreshLayout swipeRefreshLayout;

    //---------------------------------ONCREATE-------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_umfragen, container, false);

        mRecyclerViewUmfragen = (RecyclerView) rootView.findViewById(R.id.rv_umfragen);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutUmfragen);

        floatingaddUmfrage = (FloatingActionButton) rootView.findViewById(R.id.floatingaddUmfrage);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO insert refresher
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        floatingaddUmfrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewUmfrageActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }


}
