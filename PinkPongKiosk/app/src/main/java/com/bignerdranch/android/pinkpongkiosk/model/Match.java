package com.bignerdranch.android.pinkpongkiosk.model;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable{

    private int mId;
    private Player mPlayer1; //creator
    private Player mPlayer2; //opponent

    private String mScheduledAt; //todo: make this a real date at some point

    //status - needs enum: unconfirmed, confirmed, completed, drawn, in progress

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
}
