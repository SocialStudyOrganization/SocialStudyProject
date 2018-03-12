package com.ndlp.socialstudy.Vorlesungen;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;

import java.util.ArrayList;


public class VorlesungenFragment extends Fragment {
    public static VorlesungenFragment newInstance() {
        VorlesungenFragment fragment = new VorlesungenFragment();
        return fragment;
    }

    public String subFolder;
    private String kursid;
    private String urlAddress = "http://hellownero.de/SocialStudy/PHP-Dateien/Skripteverwaltung/select_vorlesungen.php";

    private RecyclerView recyclerView;
    private ArrayList<VorlesungenObject> vorlesungenObjects = new ArrayList<>();
    private VorlesungenRecyclerAdapter vorlesungenRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vorlesungen_darstellen, container, false);

        subFolder = getArguments().getString("subFolder");


        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_vorlesungen_darstellen);
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_divider);
        recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));

        vorlesungenRecyclerAdapter = new VorlesungenRecyclerAdapter(getContext(), vorlesungenObjects, subFolder);
        recyclerView.setAdapter(vorlesungenRecyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //  gets the kursid out of sharedPrefs LoginData
        SharedPreferences sharedPrefLoginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        kursid = sharedPrefLoginData.getString("kursid", "");

        if (kursid.equals(""))
            Toast.makeText(getContext(), "Something went wrong try to Login again", Toast.LENGTH_LONG).show();
        else
            new RefreshVorlesungen(getContext(), kursid, recyclerView, vorlesungenRecyclerAdapter, urlAddress, subFolder);




        return rootView;
    }
}
