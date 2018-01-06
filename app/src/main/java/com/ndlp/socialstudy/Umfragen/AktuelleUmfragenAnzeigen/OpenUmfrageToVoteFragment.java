package com.ndlp.socialstudy.Umfragen.AktuelleUmfragenAnzeigen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndlp.socialstudy.R;


public class OpenUmfrageToVoteFragment extends Fragment {
    public static OpenUmfrageToVoteFragment newInstance() {
        OpenUmfrageToVoteFragment openUmfrageToVoteFragment = new OpenUmfrageToVoteFragment();
        return openUmfrageToVoteFragment;
    }

    //--------------------Variablendeklaration-----------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_open_umfrage_to_vote, container, false);






        return rootView;
    }
}
