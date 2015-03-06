package com.bignerdranch.android.pinkpongkiosk.model;

import java.io.Serializable;

public class ActiveMatch implements Serializable {
    //represents a match either in play or in paddle assignment mode
    //maybe make this as singleton?
    private Match mMatch;

    //yeah...there is def a better way to map these...
    private String mPlayer1Paddle;
    private String mPlayer2Paddle;
    private int mPlayer1Score;
    private int mPlayer2Score;

    public ActiveMatch(Match match) {
        mMatch = match;
        mPlayer1Score = 0;
        mPlayer2Score = 0;
    }

    public void setPlayer1Paddle(String paddleId) {
        mPlayer1Paddle = paddleId;
    }

    public void setPlayerPaddle2(String paddleId) {
        mPlayer2Paddle = paddleId;
    }

    public Match getMatch() {
        return mMatch;
    }

    public void setMatch(Match match) {
        mMatch = match;
    }

    public String getPlayer1Paddle() {
        return mPlayer1Paddle;
    }

    public String getPlayer2Paddle() {
        return mPlayer2Paddle;
    }

    public void setPlayer2Paddle(String player2Paddle) {
        this.mPlayer2Paddle = player2Paddle;
    }

    public int getPlayer1Score() {
        return mPlayer1Score;
    }

    public void setPlayer1Score(int player1Score) {
        this.mPlayer1Score = player1Score;
    }

    public int getPlayer2Score() {
        return mPlayer2Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.mPlayer2Score = player2Score;
    }

    public void incrementPlayer1Score() {
        mPlayer1Score++;
    }

    public void incrementPlayer2Score() {
        mPlayer2Score++;
    }
}
