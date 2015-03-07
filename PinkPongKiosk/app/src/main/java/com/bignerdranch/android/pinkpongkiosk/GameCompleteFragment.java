package com.bignerdranch.android.pinkpongkiosk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.pinkpongkiosk.model.ActiveMatch;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameCompleteFragment extends Fragment {

    private static final String ARG_MATCH = "com.bnr.android.match";
    private ActiveMatch mActiveMatch;
    private TextView mScoreTextView;
    private TextView mStatusTextView;
    private CircleImageView mWinnerAvatar;

    public static GameCompleteFragment newInstance(ActiveMatch activeMatch) {
        GameCompleteFragment fragment = new GameCompleteFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MATCH, activeMatch);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActiveMatch = (ActiveMatch) getArguments().getSerializable(ARG_MATCH);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_game_complete, container, false);

        if (mActiveMatch.isDraw()) {
            //set up draw screen
            return setupUiDraw(v);
        } else if (mActiveMatch.isTie()) {
            //set up tie screen
            return setupUiTie(v);
        }else {
            return setupUiWinner(v);
        }

    }

    //beware...redundant methods on the horizon...

    private View setupUiTie(View v) {
        mScoreTextView = (TextView) v.findViewById(R.id.fragment_game_complete_score_text_view);
        mStatusTextView = (TextView) v.findViewById(R.id.fragment_game_complete_win_text_view);

        mWinnerAvatar = (CircleImageView) v.findViewById(R.id.fragment_game_complete_avatar1);

        mScoreTextView.setVisibility(View.GONE);

        String status = "It's a tie!";
        mStatusTextView.setText(status);

        mWinnerAvatar.setVisibility(View.GONE);

        ImageView gameComplete = (ImageView) v.findViewById(R.id.fragment_game_complete_paddle);
        gameComplete.setVisibility(View.VISIBLE);

        TextView finalScoreLabel = (TextView) v.findViewById(R.id.fragment_game_complete_final_score_label);
        finalScoreLabel.setText("Play again soon...");
        return v;
    }

    private View setupUiWinner(View v) {

        mScoreTextView = (TextView) v.findViewById(R.id.fragment_game_complete_score_text_view);
        mStatusTextView = (TextView) v.findViewById(R.id.fragment_game_complete_win_text_view);

        mWinnerAvatar = (CircleImageView) v.findViewById(R.id.fragment_game_complete_avatar1);

        mScoreTextView.setText(mActiveMatch.getScoreString());

        String status = "Congratulations, " + mActiveMatch.getWinner().getName() + "!";
        mStatusTextView.setText(status);

        Picasso.with(getActivity())
                .load(mActiveMatch.getWinner().getAvatarUrl())
                .into(mWinnerAvatar);
        return v;

    }

    private View setupUiDraw(View v) {

        mScoreTextView = (TextView) v.findViewById(R.id.fragment_game_complete_score_text_view);
        mStatusTextView = (TextView) v.findViewById(R.id.fragment_game_complete_win_text_view);

        mWinnerAvatar = (CircleImageView) v.findViewById(R.id.fragment_game_complete_avatar1);

        mScoreTextView.setVisibility(View.GONE);

        String status = "It's a draw!";
        mStatusTextView.setText(status);

        mWinnerAvatar.setVisibility(View.GONE);

        ImageView gameComplete = (ImageView) v.findViewById(R.id.fragment_game_complete_paddle);
        gameComplete.setVisibility(View.VISIBLE);

        TextView finalScoreLabel = (TextView) v.findViewById(R.id.fragment_game_complete_final_score_label);
        finalScoreLabel.setText("Play again soon...");

        return v;

    }
}
