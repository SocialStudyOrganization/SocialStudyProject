package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteVorlesungenFragment;
import com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen.BasicUmfragenFragment;



public class MainMenuFragment extends Fragment {
    public static MainMenuFragment newInstance() {
        MainMenuFragment fragment = new MainMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main_menu, container, false);

        //  navigate to Classes in order to show Skripte
        LinearLayout b_toSkripte = (LinearLayout) rootView.findViewById(R.id.b_toSkripte);
        LinearLayout b_toTasks = (LinearLayout) rootView.findViewById(R.id.b_toTasks);
        LinearLayout b_toAnswers = (LinearLayout) rootView.findViewById(R.id.b_toAnswers);
        LinearLayout b_tomfragen = (LinearLayout) rootView.findViewById(R.id.b_toUmfragen);
        Button b_logout = (Button) rootView.findViewById(R.id.b_logout);
        TextView tv_skripte = (TextView) rootView.findViewById(R.id.tv_skripte);
        TextView tv_answers = (TextView) rootView.findViewById(R.id.tv_answers);
        TextView tv_tasks =(TextView) rootView.findViewById(R.id.tv_tasks);
        TextView tv_umfragen=(TextView) rootView.findViewById(R.id.tv_umfragen);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-LightItalic.otf");

        //assigning typefaces
        tv_skripte.setTypeface(quicksand_regular);
        tv_answers.setTypeface(quicksand_regular);
        tv_tasks.setTypeface(quicksand_regular);
        tv_umfragen.setTypeface(quicksand_regular);
        b_logout.setTypeface(quicksand_regular);



        b_toSkripte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.clear();
                b.putString("subFolder", "Skripte");
                //Start fragment
                SkripteVorlesungenFragment classesFragment = new SkripteVorlesungenFragment();
                classesFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, classesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        b_toTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.clear();
                b.putString("subFolder", "Tasks");

                SkripteVorlesungenFragment classesFragment = new SkripteVorlesungenFragment();
                classesFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, classesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

       b_toAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.clear();
                b.putString("subFolder", "Answers");

                SkripteVorlesungenFragment classesFragment = new SkripteVorlesungenFragment();
                classesFragment.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, classesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        b_tomfragen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BasicUmfragenFragment basicUmfragenFragment = new BasicUmfragenFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, basicUmfragenFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        return rootView;
    }


}