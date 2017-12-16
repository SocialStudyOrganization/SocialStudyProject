package com.ndlp.socialstudy.Tasks;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndlp.socialstudy.GeneralFileFolder.VorlesungenObject;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteVorlesungenRecyclerAdapter;
import com.ndlp.socialstudy.activity.DividerItemDecoration;

import java.util.ArrayList;


public class TasksVorlesungenFragment extends Fragment {
    public static TasksVorlesungenFragment newInstance() {
        TasksVorlesungenFragment tasksVorlesungenFragment = new TasksVorlesungenFragment();
        return tasksVorlesungenFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tasks_vorlesungen, container, false);

        RecyclerView recyclerView;
        TasksVorlesungenRecyclerAdapter adapter;

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_tasksrecyclerviewclasses);

        ArrayList<VorlesungenObject> data = new ArrayList<>();
        String[] titles = {"Außenwirtschaft"};

        //  For each String in String[] titles create a classObject -> class VorlesungenObject
        for (String title : titles) {
            //  add each title to data
            VorlesungenObject current = new VorlesungenObject(title);
            data.add(current);

        }


        //  calls method getData() and transfers titles to SkripteVorlesungenRecyclerAdapter
        adapter = new TasksVorlesungenRecyclerAdapter(getContext(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //  get a line divider between each class title, calls class DividerItemDecoration
        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), R.drawable.line_divider);
        recyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));




        return rootView;


    }
}
