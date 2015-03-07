package com.bignerdranch.android.pinkpongkiosk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bignerdranch.android.pinkpongkiosk.model.ActiveMatch;

public class KioskActivity extends NfcSinglFragmentActivity {

    public static final String EXTRA_ACTIVE_MATCH = "com.bnr.android.kioskactivity.activematch";

    private static final String TAG = "KioskActivity";
    private PlayerTapListener mPlayerTapListener;

    public static Intent newIntent(Context context) {
        return new Intent(context, KioskActivity.class);
    }

    public interface PlayerTapListener {
        void onPlayerTapped(String playerId);
    }

    public void setPlayerTapListener(PlayerTapListener playerTapListener) {
        mPlayerTapListener = playerTapListener;
    }

    @Override
    protected Fragment getFragment() {
        Intent origIntent = getIntent();
        ActiveMatch activeMatch = (ActiveMatch) origIntent.getSerializableExtra(EXTRA_ACTIVE_MATCH);
        return ScoreFragment.newInstance(activeMatch);
     }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //good android developers account for rotation and override onSavedInstanceState...right now I only have time to be bad...
        getSupportActionBar().setTitle("In Play");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            String tappedPlayerId = parsePlayerIdFromNdefDiscoveredIntent(intent);
            mPlayerTapListener.onPlayerTapped(tappedPlayerId);
        }

    }


    /**
     * Assumes intent action is ACTION_NDEF_DISCOVERED; doesn't check as of yet
     *
     * @param intent
     * @return
     */
    private String parsePlayerIdFromNdefDiscoveredIntent(Intent intent) {
        String id = null;
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        NdefRecord record = msg.getRecords()[0];
        Uri uri = record.toUri();
        id = uri.getQueryParameter("id");
        return id;
    }


}
