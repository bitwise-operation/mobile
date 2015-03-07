package com.bignerdranch.android.pinkpongkiosk.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MatchResponse {

    @SerializedName("matches")
    private List<Match> mMatches;

    public List<Match> getMatches() {
        return mMatches;
    }

    public void setMatches(List<Match> matches) {
        mMatches = matches;
    }
}
