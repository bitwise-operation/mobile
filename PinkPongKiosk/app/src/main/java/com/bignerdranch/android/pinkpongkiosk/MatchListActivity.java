package com.bignerdranch.android.pinkpongkiosk;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MatchListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment getFragment() {
        return MatchListFragment.newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Scheduled Matches");
    }
}
