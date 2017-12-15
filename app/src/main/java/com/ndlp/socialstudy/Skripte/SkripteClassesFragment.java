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

import java.util.ArrayList;


public class SkripteClassesFragment extends Fragment {
    public static SkripteClassesFragment newInstance() {
        SkripteClassesFragment fragment = new SkripteClassesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_script_classes, container, false);

        RecyclerView rv_recyclerviewclasses;
        SkripteClassRecyclerAdapter adapter;


        rv_recyclerviewclasses = (RecyclerView) rootView.findViewById(R.id.rv_recyclerviewclasses);


        ArrayList<ClassObject> data = new ArrayList<>();
        String[] titles = {"Elektrotechnik", "Marketing"};

        //  For each String in String[] titles create a classObject -> class ClassObject
        for (String title : titles) {
            //  add each title to data
            ClassObject current = new ClassObject(title);
            data.add(current);

        }


        //  calls method getData() and transfers titles to SkripteClassRecyclerAdapter
        adapter = new SkripteClassRecyclerAdapter(getContext(), data);
        rv_recyclerviewclasses.setAdapter(adapter);
        rv_recyclerviewclasses.setLayoutManager(new LinearLayoutManager(getContext()));

        //  get a line divider between each class title, calls class DividerItemDecoration
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_divider);
        rv_recyclerviewclasses.addItemDecoration(new DividerItemDecoration(dividerDrawable));




        return rootView;
    }
}
