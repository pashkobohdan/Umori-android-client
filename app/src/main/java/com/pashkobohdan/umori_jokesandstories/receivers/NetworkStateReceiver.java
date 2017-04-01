package com.pashkobohdan.umori_jokesandstories.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.pashkobohdan.umori_jokesandstories.Application;

/**
 * Created by bohdan on 01.04.17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if(intent.getExtras()!=null) {
            NetworkInfo ni=(NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
            if(ni!=null && ni.getState()==NetworkInfo.State.CONNECTED && Application.hasActiveInternetConnection(context)) {
                Toast.makeText(context, "Network connected. You can refresh posts", Toast.LENGTH_SHORT).show();
            }
        }
        if(intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY,Boolean.FALSE)) {
            Toast.makeText(context, "There's no network connectivity", Toast.LENGTH_SHORT).show();
        }
    }
}