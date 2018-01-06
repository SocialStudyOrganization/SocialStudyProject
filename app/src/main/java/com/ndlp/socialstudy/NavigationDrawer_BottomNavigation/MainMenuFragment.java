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

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.SkripteVorlesungenFragment;
import com.ndlp.socialstudy.Tasks.TasksVorlesungenFragment;
import com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen.BasicUmfragenFragment;

/**
 * Fragment to navigate between tasks, exercises, surveys, solutions
 * show classmembers with click on button
 */

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
        Button b_toSkripte = (Button) rootView.findViewById(R.id.b_toSkripte);
        Button b_toTasks = (Button) rootView.findViewById(R.id.b_toTasks);
        Button b_toAnswers = (Button) rootView.findViewById(R.id.b_toAnswers);
        Button b_tomfragen = (Button) rootView.findViewById(R.id.b_toUmfragen);
        Button b_logout = (Button) rootView.findViewById(R.id.b_logout);

        //declaring typefaces
        Typeface quicksand_regular = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Regular.otf");
        Typeface quicksand_bold = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Bold.otf");
        Typeface quicksand_bolditalic = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-BoldItalic.otf");
        Typeface quicksand_italic = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Italic.otf");
        Typeface quicksand_light = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-Light.otf");
        Typeface quicksand_lightitalic = Typeface.createFromAsset(b_toSkripte.getContext().getAssets(),  "fonts/Quicksand-LightItalic.otf");

        //assigning typefaces
        b_toSkripte.setTypeface(quicksand_regular);
        b_toTasks.setTypeface(quicksand_regular);
        b_toAnswers.setTypeface(quicksand_regular);
        b_tomfragen.setTypeface(quicksand_regular);
        b_logout.setTypeface(quicksand_regular);

        b_toSkripte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start fragment
                SkripteVorlesungenFragment classesFragment = new SkripteVorlesungenFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, classesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        b_toTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TasksVorlesungenFragment tasksVorlesungenFragment = new TasksVorlesungenFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tasksVorlesungenFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

/*        b_toAnswers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerClassesFragment answerClassesFragment = new AnswerClassesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, answerClassesFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

*/

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






        //  logout the user -> call method clearPrefs() -> call LoginActivity
        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearPrefs();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);


            }
        });

        return rootView;
    }

    //  method to clear the rememberMe sharedPrefs
    public void clearPrefs() {

        SharedPreferences preferences = getActivity().getSharedPreferences("rememberMe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}