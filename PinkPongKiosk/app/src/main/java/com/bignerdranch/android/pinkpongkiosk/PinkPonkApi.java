package com.bignerdranch.android.pinkpongkiosk;

import com.bignerdranch.android.pinkpongkiosk.model.Match;
import com.bignerdranch.android.pinkpongkiosk.model.MatchResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PinkPonkApi {

    @GET("/matches") //@GET("/matches/confirmed")
    void getConfirmedMatches(Callback<MatchResponse> callback);

    @POST("/matches/{match_id}/users/{user_id}")
    void groupList(@Path("match_id") int matchId, @Path("user_id") int userId, Callback<Void> callback);
}
