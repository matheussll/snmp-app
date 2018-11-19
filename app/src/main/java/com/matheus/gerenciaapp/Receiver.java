package com.matheus.gerenciaapp;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Receiver extends BroadcastReceiver {
    private Activity mActivity;
    public Receiver(Activity activity) {
        mActivity = activity;
    }

    public Receiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("gerencia-app", "intent: " + intent);

        if (mActivity != null) {
            Log.i("gerencia-app", "activity: " + mActivity);

        }
    }
}