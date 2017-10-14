package com.ndlp.socialstudy.Skripte;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ndlp.socialstudy.R;

import java.util.ArrayList;
import java.util.List;

/**
 *Activity to allow navigation between different classes
 */

public class ClassesActivity extends AppCompatActivity {

    private RecyclerView rv_recyclerviewclasses;
    private ClassRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        rv_recyclerviewclasses = (RecyclerView) findViewById(R.id.rv_recyclerviewclasses);

        //  calls method getData() and transfers titles to ClassRecyclerAdapter
        adapter = new ClassRecyclerAdapter(this, getData());
        rv_recyclerviewclasses.setAdapter(adapter);
        rv_recyclerviewclasses.setLayoutManager(new LinearLayoutManager(this));

        //  get a line divider between each class title, calls class DividerItemDecoration
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.line_divider);
        rv_recyclerviewclasses.addItemDecoration(new DividerItemDecoration(dividerDrawable));

    }

    //  gets the data displayed in the RecyclerView in form of titles
    public static ArrayList<ClassObject> getData(){
        ArrayList<ClassObject> data = new ArrayList<>();
        String[] titles = {"Informatik", "Konstruktionslehre", "Technische Physik", "Projektmanagement"};

        //  For each String in String[] titles create a classObject -> class ClassObject
        for (String title : titles) {

            //  add each title to data
            ClassObject current = new ClassObject(title);
            data.add(current);

        }
        return data;
    }
}
