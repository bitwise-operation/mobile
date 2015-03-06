package com.bignerdranch.android.pinkpongkiosk;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Adds convenience methods for enabling/disabling foreground dispatch.
 * Based on http://code.tutsplus.com/tutorials/reading-nfc-tags-with-android--mobile-17278.
 */
public abstract class NfcSinglFragmentActivity extends SingleFragmentActivity {

    private NfcAdapter mNfcAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /**
         * It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.
         */
        setupForegroundDispatch();
    }

    @Override
    protected void onPause() {
        /**
         * Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
         */
        stopForegroundDispatch();

        super.onPause();
    }


    protected void setupForegroundDispatch() {
        final Intent intent = new Intent(getApplicationContext(), getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        filters[0].addDataScheme("app");
        filters[0].addDataAuthority("com.bignerdranch.android.pinkpongkiosk", null);

        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, techList);
    }

    protected void stopForegroundDispatch() {
        mNfcAdapter.disableForegroundDispatch(this);
    }

}
