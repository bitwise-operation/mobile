package com.bignerdranch.android.pinkpongkiosk.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable{

    @SerializedName("id")
    private int mId;

    @SerializedName("creator")
    private Player mPlayer1; //creator

    @SerializedName("opponent")
    private Player mPlayer2; //opponent

    @SerializedName("scheduled_at")
    private String mScheduledAt; //todo: make this a real date at some point

    @SerializedName("state")
    private String mState; //this should probably be mapped to some sort of enum...

    public Match(int matchid, Player player1, Player player2, String scheduledAt) {
        setId(matchid);
        setPlayer1(player1);
        setPlayer2(player2);
        setScheduledAt(scheduledAt);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Player getPlayer1() {
        return mPlayer1;
    }

    public void setPlayer1(Player player1) {
        mPlayer1 = player1;
    }

    public Player getPlayer2() {
        return mPlayer2;
    }

    public void setPlayer2(Player player2) {
        mPlayer2 = player2;
    }

    public String getScheduledAt() {
        return mScheduledAt;
    }

    public void setScheduledAt(String scheduledAt) {
        mScheduledAt = scheduledAt;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }
}
