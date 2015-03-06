package com.bignerdranch.android.pinkpongkiosk.model;

import java.io.Serializable;

public class Player implements Serializable{

    private int mId;
    private String mName;
    private String mAvatarUrl;
    private int mWins;
    private int mLosses;

    public Player(int id, String name, String avatarUrl, int wins, int losses) {
        mId = id;
        mName = name;
        mAvatarUrl = avatarUrl;
        mWins = wins;
        mLosses = losses;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int wins) {
        mWins = wins;
    }

    public int getLosses() {
        return mLosses;
    }

    public void setLosses(int losses) {
        mLosses = losses;
    }

    public String getWinLossString() {
        return String.format("%d-%d", mWins, mLosses);
    }

    @Override
    public String toString() {
        return "Player{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mAvatarUrl='" + mAvatarUrl + '\'' +
                ", mWins=" + mWins +
                ", mLosses=" + mLosses +
                '}';
    }

}
