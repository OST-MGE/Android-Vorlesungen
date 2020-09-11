package ch.ost.rj.mge.v06.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "MGE.V06.DEBUG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast | Action: " + intent.getAction());

        if (intent.getExtras().containsKey(MyStartedService.SERVICE_RESULT_KEY)) {
            Log.d(LOG_TAG, "Broadcast | Service Result: " + intent.getIntExtra(MyStartedService.SERVICE_RESULT_KEY, 0));
        }
    }
}
