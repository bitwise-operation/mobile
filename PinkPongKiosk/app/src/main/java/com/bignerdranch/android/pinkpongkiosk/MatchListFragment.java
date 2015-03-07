package com.bignerdranch.android.pinkpongkiosk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bignerdranch.android.pinkpongkiosk.model.Match;
import com.bignerdranch.android.pinkpongkiosk.model.MatchResponse;
import com.bignerdranch.android.pinkpongkiosk.model.MockData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

public class MatchListFragment extends Fragment {

    private static final String TAG = "MatchListFragment";

    //http://pink-ponk.herokuapp.com/

    private RecyclerView mRecyclerView;
    private PinkPonkApi mPinkPonkApi;
    private List<Match> mMatches;

    public static MatchListFragment newInstance() {
        return new MatchListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Gson gson = new GsonBuilder()
        //        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://pink-ponk.herokuapp.com/")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new AndroidLog(TAG))
                .setClient(new OkClient(new OkHttpClient()))
                //.setConverter(new GsonConverter(gson))
                .build();


        mMatches = new ArrayList<>();
        mPinkPonkApi = restAdapter.create(PinkPonkApi.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_match_list, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.fragment_match_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new MatchAdapter(mMatches));

        setupAdapter();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPinkPonkApi.getConfirmedMatches(new Callback<MatchResponse>() {
            @Override
            public void success(MatchResponse matchResponse, Response response) {
                Log.d(TAG, "matches request success!");
                mMatches = matchResponse.getMatches();
                setupAdapter();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "matches request failed " + error.toString());
            }
        });
    }

    private void setupAdapter() {
        if (isAdded()) {
            mRecyclerView.setAdapter(new MatchAdapter(mMatches));
        }
    }

    private class MatchAdapter extends RecyclerView.Adapter<MatchHolder> {
        private List<Match> mMatches;

        public MatchAdapter(List<Match> matches) {
            mMatches = matches;
        }

        @Override
        public MatchHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.match_list_item, viewGroup, false);
            return new MatchHolder(view);
        }

        @Override
        public void onBindViewHolder(MatchHolder matchHolder, int i) {
            matchHolder.bindMatch(mMatches.get(i));
        }

        @Override
        public int getItemCount() {
            return mMatches.size();
        }
    }

    private class MatchHolder extends RecyclerView.ViewHolder {
        private Match mMatch;

        private TextView mTimeTextView;

        //player 1 - this should prob be a custom view that makes life easier
        private CircleImageView mAvatar1;
        private TextView mName1;
        private TextView mWins1;

        //player 2
        private CircleImageView mAvatar2;
        private TextView mName2;
        private TextView mWins2;

        private Button mStartMatchButton;

        public MatchHolder(View itemView) {
            super(itemView);

            mTimeTextView = (TextView) itemView.findViewById(R.id.match_list_item_time);

            mAvatar1 = (CircleImageView) itemView.findViewById(R.id.match_list_item_avatar1);
            mName1 = (TextView) itemView.findViewById(R.id.match_list_item_name1);
            mWins1 = (TextView) itemView.findViewById(R.id.match_list_item_wins1);

            mAvatar2 = (CircleImageView) itemView.findViewById(R.id.match_list_item_avatar2);
            mName2 = (TextView) itemView.findViewById(R.id.match_list_item_name2);
            mWins2 = (TextView) itemView.findViewById(R.id.match_list_item_wins2);

            mStartMatchButton = (Button) itemView.findViewById(R.id.match_list_item_button);
        }


        public void bindMatch(final Match match) {

            mTimeTextView.setText(match.getMatchTime());

            Picasso.with(getActivity())
                    .load(match.getPlayer1().getAvatarUrl())
                    .into(mAvatar1);
            //mAvatar1.setImageDrawable(); //load image using URL from dataset and Picasso or maybe glide
            mName1.setText(match.getPlayer1().getName());
            mWins1.setText(match.getPlayer1().getWinLossString());


            Picasso.with(getActivity())
                    .load(match.getPlayer2().getAvatarUrl())
                    .into(mAvatar2);
            mName2.setText(match.getPlayer2().getName());
            mWins2.setText(match.getPlayer2().getWinLossString());

            mStartMatchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent paddleRegIntent = PaddleRegistrationActivity.newIntent(getActivity());
                    paddleRegIntent.putExtra(PaddleRegistrationActivity.EXTRA_MATCH, match);
                    startActivity(paddleRegIntent);
                }
            });
        }

    }



}
