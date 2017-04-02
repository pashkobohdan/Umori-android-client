package com.pashkobohdan.umori_jokesandstories.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.pashkobohdan.umori_jokesandstories.Application;
import com.pashkobohdan.umori_jokesandstories.R;

/**
 * Created by bohdan on 01.04.17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                Toast.makeText(context, R.string.network_connected, Toast.LENGTH_SHORT).show();
            }
        }
        if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
            Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show();
        }
    }
}