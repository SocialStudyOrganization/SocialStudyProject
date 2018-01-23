package com.ndlp.socialstudy.Skripte;


import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.activity.DividerItemDecoration;
import com.ndlp.socialstudy.GeneralFileFolder.VorlesungenObject;

import java.util.ArrayList;


public class SkripteVorlesungenFragment extends Fragment {
    public static SkripteVorlesungenFragment newInstance() {
        SkripteVorlesungenFragment fragment = new SkripteVorlesungenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vorlesungen_darstellen, container, false);

        RecyclerView rv_recyclerviewclasses;
        SkripteVorlesungenRecyclerAdapter adapter;


        rv_recyclerviewclasses = (RecyclerView) rootView.findViewById(R.id.rv_vorlesungen_darstellen);


        ArrayList<VorlesungenObject> data = new ArrayList<>();
        String[] titles = {"Konstruktionslehre", "Außenwirtschaft", "Informatik", "Elektrotechnik", "Marketing", "Rechnungswesen"};

        //  For each String in String[] titles create a classObject -> class VorlesungenObject
        for (String title : titles) {
            //  add each title to data
            VorlesungenObject current = new VorlesungenObject(title);
            data.add(current);

        }


        //  calls method getData() and transfers titles to AnswersVorlesungenRecyclerAdapter
        adapter = new SkripteVorlesungenRecyclerAdapter(getContext(), data);
        rv_recyclerviewclasses.setAdapter(adapter);
        rv_recyclerviewclasses.setLayoutManager(new LinearLayoutManager(getContext()));

        //  get a line divider between each class title, calls class DividerItemDecoration
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_divider);
        rv_recyclerviewclasses.addItemDecoration(new DividerItemDecoration(dividerDrawable));




        return rootView;
    }
}
