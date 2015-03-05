package com.bignerdranch.android.pinkpongkiosk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class ScoreFragment extends Fragment implements KioskActivity.PlayerTapListener {

    private static final String TAG = "ScoreFragment";
    //model vars; maybe make a player that has an id and a score
    private int mScoreA; //score can be incremented, decremented, and reset
    private int mScoreB;
    private String mPlayerAId; //player has a score; has an id
    private String mPlayerBId;

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
        mScoreA = 0;
        mScoreB = 0;

        mPlayerAId = "1";
        mPlayerBId = "2";


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((KioskActivity)activity).setPlayerTapListener(this);
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

    @Override
    public void onDetach() {
        super.onDetach();
        ((KioskActivity)getActivity()).setPlayerTapListener(null);
    }

    @Override
    public void onPlayerTapped(String playerId) {
        //increment score for correct player
        Log.d(TAG, "onPlayerTapped " + playerId);
        if (mPlayerAId.equals(playerId)) {
            mScoreA++;
        } else { //player B gets a point if someone other than them scores...oh well
            mScoreB++;
        }

        updateUI();
    }

    private void updateUI(){
        mScoreATextView.setText(Integer.toString(mScoreA));
        mScoreBTextView.setText(Integer.toString(mScoreB));
    }


}
