package com.bignerdranch.android.pinkpongkiosk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScoreFragment extends Fragment {

    //model vars; maybe make a player that has an id and a score
    private int mScoreA;
    private int mScoreB;

    //view items
    private TextView mScoreATextView;
    private TextView mScoreBTextView;

    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //init mScoreA and mScoreB
        //not sure if we will worry about rotation or just lock to portrait mode for now
        mScoreA = 42;
        mScoreB = 15;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_score, container, false);

        mScoreATextView = (TextView) v.findViewById(R.id.fragment_score_a_text_view);
        mScoreBTextView = (TextView) v.findViewById(R.id.fragment_score_b_text_view);

        updateUI();

        return v;
    }

    private void updateUI(){
        mScoreATextView.setText(Integer.toString(mScoreA));
        mScoreBTextView.setText(Integer.toString(mScoreB));
    }
}
