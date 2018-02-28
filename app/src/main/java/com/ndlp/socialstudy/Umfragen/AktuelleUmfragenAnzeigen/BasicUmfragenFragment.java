package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Umfragen.UmfrageErstellen.NewUmfrageActivity;
import com.ndlp.socialstudy.activity.TinyDB;

import java.util.ArrayList;


public class BasicUmfragenFragment extends Fragment {
    public static BasicUmfragenFragment newInstance() {
        BasicUmfragenFragment basicUmfragenFragment = new BasicUmfragenFragment();
        return basicUmfragenFragment;
    }

    //--------------------Variablendeklaration-----------------------------------------

    private FloatingActionButton floatingaddUmfrage;


    RecyclerView mRecyclerViewUmfragen;
    SwipeRefreshLayout swipeRefreshLayout;

    private String kursid;


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


        final BasicUmfragenRecyclerAdapter basicUmfragenRecyclerAdapter;
        final ArrayList<GeneralObject> generalObjects = new ArrayList<>();


        basicUmfragenRecyclerAdapter = new BasicUmfragenRecyclerAdapter(getContext(), generalObjects);
        mRecyclerViewUmfragen.setAdapter(basicUmfragenRecyclerAdapter);
        mRecyclerViewUmfragen.setLayoutManager(new LinearLayoutManager(getContext()));

        SharedPreferences sharedPrefLoginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        kursid = sharedPrefLoginData.getString("kursid", "");

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new RefreshUmfragenFromDatabase(getActivity(), kursid, mRecyclerViewUmfragen);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        floatingaddUmfrage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TinyDB tinyDB = new TinyDB(getContext());
                tinyDB.remove("AnzahlEinzelnerUmfragen");
                Intent intent = new Intent(getContext(), NewUmfrageActivity.class);
                startActivity(intent);
            }
        });



        new RefreshUmfragenFromDatabase(getActivity(), kursid, mRecyclerViewUmfragen);

        return rootView;
    }


}
