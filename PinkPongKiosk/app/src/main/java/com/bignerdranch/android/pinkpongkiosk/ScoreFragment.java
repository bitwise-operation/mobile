package com.bignerdranch.android.pinkpongkiosk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.pinkpongkiosk.model.ActiveMatch;
import com.bignerdranch.android.pinkpongkiosk.model.Match;
import com.bignerdranch.android.pinkpongkiosk.model.MockData;

import java.util.HashMap;
import java.util.Map;

public class ScoreFragment extends Fragment implements KioskActivity.PlayerTapListener {

    private static final String TAG = "ScoreFragment";
    private static final String ARG_ACTIVE_MATCH = "com.bnr.scorefragment.activeMatch";

    //model items
    private ActiveMatch mActiveMatch;

    //view items
    private TextView mScoreATextView;
    private TextView mScoreBTextView;

    public static ScoreFragment newInstance(ActiveMatch activeMatch) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACTIVE_MATCH, activeMatch);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActiveMatch = (ActiveMatch) getArguments().getSerializable(ARG_ACTIVE_MATCH);
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
        if (mActiveMatch.getPlayer1Paddle().equals(playerId)) {
            mActiveMatch.incrementPlayer1Score();
        } else { //player 2 gets a point if someone other than them scores...oh well
            mActiveMatch.incrementPlayer2Score();
        }

        updateUI(); //consider updating only the affected score text view is lag is a problem
    }

    private void updateUI(){
        mScoreATextView.setText(Integer.toString(mActiveMatch.getPlayer1Score()));
        mScoreBTextView.setText(Integer.toString(mActiveMatch.getPlayer2Score()));
    }


}
