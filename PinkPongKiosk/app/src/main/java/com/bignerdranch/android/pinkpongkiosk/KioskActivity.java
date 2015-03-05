package com.bignerdranch.android.pinkpongkiosk;

import android.support.v4.app.Fragment;

public class KioskActivity extends SingleFragmentActivity{

    @Override
    protected Fragment getFragment() {
        return ScoreFragment.newInstance();
    }
}
