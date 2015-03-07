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
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

public class ScoreFragment extends Fragment implements KioskActivity.PlayerTapListener {

    private static final String TAG = "ScoreFragment";
    private static final String ARG_ACTIVE_MATCH = "com.bnr.scorefragment.activeMatch";

    //model items
    private ActiveMatch mActiveMatch;

    //web service
    private PinkPonkApi mPinkPonkApi;

    //view items
    private TextView mScoreATextView;
    private TextView mScoreBTextView;

    private CircleImageView mAvatar1;
    private CircleImageView mAvatar2;

    private TextView mName1;
    private TextView mName2;


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

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://pink-ponk.herokuapp.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(TAG))
                .setClient(new OkClient(new OkHttpClient()))
                .build();

        mPinkPonkApi = restAdapter.create(PinkPonkApi.class);
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

        mName1 = (TextView) v.findViewById(R.id.fragment_score_a_name_text_view);
        mName2 = (TextView) v.findViewById(R.id.fragment_score_b_name_text_view);

        mAvatar1 = (CircleImageView) v.findViewById(R.id.fragment_score_avatar1);
        mAvatar2 = (CircleImageView) v.findViewById(R.id.fragment_score_avatar2);

        updateUi();

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
            mPinkPonkApi.incrementScore(mActiveMatch.getMatch().getId(),
                    mActiveMatch.getMatch().getPlayer1().getId(),
                    new Callback<Response>() {
                        @Override
                        public void success(Response response, retrofit.client.Response response2) {

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
        } else { //player 2 gets a point if someone other than them scores...oh well
            mActiveMatch.incrementPlayer2Score();
            mPinkPonkApi.incrementScore(mActiveMatch.getMatch().getId(),
                    mActiveMatch.getMatch().getPlayer2().getId(),
                    new Callback<Response>() {
                        @Override
                        public void success(Response response, retrofit.client.Response response2) {

                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
        }

        updateScoreUi(); //consider updating only the affected score text view is lag is a problem
    }

    private void updateScoreUi(){
        mScoreATextView.setText(Integer.toString(mActiveMatch.getPlayer1Score()));
        mScoreBTextView.setText(Integer.toString(mActiveMatch.getPlayer2Score()));
    }


    private void updateUi(){
        mScoreATextView.setText(Integer.toString(mActiveMatch.getPlayer1Score()));
        mScoreBTextView.setText(Integer.toString(mActiveMatch.getPlayer2Score()));

        Picasso.with(getActivity())
                .load(mActiveMatch.getMatch().getPlayer1().getAvatarUrl())
                .into(mAvatar1);

        Picasso.with(getActivity())
                .load(mActiveMatch.getMatch().getPlayer2().getAvatarUrl())
                .into(mAvatar2);

        mName1.setText(mActiveMatch.getMatch().getPlayer1().getName());
        mName2.setText(mActiveMatch.getMatch().getPlayer2().getName());
    }

}
