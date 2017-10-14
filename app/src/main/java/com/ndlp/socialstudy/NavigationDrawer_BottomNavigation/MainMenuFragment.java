package com.ndlp.socialstudy.NavigationDrawer_BottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ndlp.socialstudy.LoginSystem.LoginActivity;
import com.ndlp.socialstudy.R;
import com.ndlp.socialstudy.Skripte.ClassesActivity;

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
        b_toSkripte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClassesActivity.class);
                startActivity(intent);
            }
        });


        Button b_logout = (Button) rootView.findViewById(R.id.b_logout);

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