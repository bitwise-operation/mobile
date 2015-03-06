package com.bignerdranch.android.pinkpongkiosk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.android.pinkpongkiosk.model.Match;
import com.bignerdranch.android.pinkpongkiosk.model.MockData;


public class PaddleRegistrationActivity extends NfcSinglFragmentActivity implements PaddleRegFragmentListener{

    private static final String TAG = "PaddleRegistration";


    private static enum RegistrationState {
        PLAYER1_PROMPT, PLAYER1_UPDATING, PLAYER2_PROMPT, PLAYER2_UPDATING
    }

    //later we should be using the match the user clicked on;  either pass it along or create a singleton to represent the state
    private static Match mCurrentMatch = MockData.generateMatchList().get(0);

    private RegistrationState mRegistrationState;

    private PlayerTapListener mPlayerTapListener; //callback for hosted fragment

    private BroadcastReceiver mBroadcastReciever;

    public static Intent newIntent(Context context) {
        return new Intent(context, PaddleRegistrationActivity.class);
    }

    public interface PlayerTapListener {
        void onPlayerTapped(String playerId);
    }

    public void setPlayerTapListener(PlayerTapListener playerTapListener) {
        mPlayerTapListener = playerTapListener;
    }

    @Override
    protected Fragment getFragment() { //called during onCreate -- always add frag for player 1
        return PaddleRegistrationFragment.newInstance(mCurrentMatch.getPlayer1());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegistrationState = RegistrationState.PLAYER1_PROMPT;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mBroadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "received broadcast!");
                onUiUpdateComplete();
            }
        };

        registerReceiver(mBroadcastReciever, new IntentFilter(PaddleRegistrationFragment.ACTION_PADDLE_REG_FRAG_UPDATE_UI_COMPLETE));*/

    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(mBroadcastReciever);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent");
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            switch (mRegistrationState) {
                case PLAYER1_PROMPT:
                    mRegistrationState = RegistrationState.PLAYER1_UPDATING;
                    mPlayerTapListener.onPlayerTapped(parsePlayerIdFromNdefDiscoveredIntent(intent));
                    //todo: update paddle-player1 mapping
                    //wait for frag to call back...
                    break;
                case PLAYER1_UPDATING:
                    break;
                case PLAYER2_PROMPT:
                    mRegistrationState = RegistrationState.PLAYER2_UPDATING;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Fragment fragment = fragmentManager.findFragmentById(R.id.activity_single_fragment_fragment_container);
                    setPlayerTapListener((PlayerTapListener)fragment); //HACK!!!  onAttach/onDetach setting of listener was causing null to clobber new frag as listener
                    mPlayerTapListener.onPlayerTapped(parsePlayerIdFromNdefDiscoveredIntent(intent));
                    //todo: update paddle-player2 mapping
                    //wait for frag to call back...
                    break;
                case PLAYER2_UPDATING:
                    break;
            }
        }

    }

    //call this when frag notifies you it is done updating itself...should probably set up this dependency a different way!
    @Override
    public void onUiUpdateComplete() {
        switch (mRegistrationState) {
            case PLAYER1_PROMPT:
                //do nothing - invalid case
                break;
            case PLAYER1_UPDATING:

                Fragment newFragment = PaddleRegistrationFragment.newInstance(mCurrentMatch.getPlayer2());
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit)
                        .replace(R.id.activity_single_fragment_fragment_container, newFragment)
                        .commit();
                fragmentManager.executePendingTransactions();

                mRegistrationState = RegistrationState.PLAYER2_PROMPT;
                break;
            case PLAYER2_PROMPT:
                break;
            case PLAYER2_UPDATING:
                //launch Kiosk Activity!
                startActivity(KioskActivity.newIntent(this));
                break;
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
