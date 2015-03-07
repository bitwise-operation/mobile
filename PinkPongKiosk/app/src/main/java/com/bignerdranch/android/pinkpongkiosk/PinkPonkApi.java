package com.bignerdranch.android.pinkpongkiosk;

import com.bignerdranch.android.pinkpongkiosk.model.Match;
import com.bignerdranch.android.pinkpongkiosk.model.MatchResponse;
import com.squareup.okhttp.Response;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface PinkPonkApi {

    @GET("/matches/confirmed")
    void getConfirmedMatches(Callback<MatchResponse> callback);

    @POST("/matches/{match_id}/scores/{user_id}")
    void incrementScore(@Path("match_id") int matchId, @Path("user_id") int userId, Callback<Response> callback);

    @POST("/matches/{match_id}/draw")
    void drawMatch(@Path("match_id") int matchId, Callback<Response> callback);

    @POST("/matches/{match_id}/complete")
    void completeMatch(@Path("match_id") int matchId, Callback<Response> callback);

}
