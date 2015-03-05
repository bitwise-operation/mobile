package com.bignerdranch.android.pinkpongkiosk;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;


public abstract class SingleFragmentActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.activity_single_fragment_fragment_container);

        if (fragment == null) {
            fragment = getFragment();
            if (fragment != null) {
                fragmentManager.beginTransaction()
                        .add(R.id.activity_single_fragment_fragment_container, fragment)
                        .commit();
            }
        }
    }

    protected abstract Fragment getFragment();

}