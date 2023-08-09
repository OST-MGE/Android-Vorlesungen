package ch.ost.rj.mge.v06.examples.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import ch.ost.rj.mge.v06.examples.services.MyStartedService;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String BROADCAST_ACTION = "ch.ost.rj.mge.v06.myapplication.MY_INTENT";
    public static final String BROADCAST_EXTRA = "data";
    private static final String LOG_TAG = "MGE.V06.DEBUG";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "Broadcast | Action: " + intent.getAction());

        if (intent.getExtras() != null && intent.getExtras().containsKey(BROADCAST_EXTRA)) {
            Log.d(LOG_TAG, "Broadcast | Extra: " + intent.getStringExtra(BROADCAST_EXTRA));
        }

        if (intent.getExtras() != null && intent.getExtras().containsKey(MyStartedService.SERVICE_RESULT_KEY)) {
            Log.d(LOG_TAG, "Broadcast | Service Result: " + intent.getIntExtra(MyStartedService.SERVICE_RESULT_KEY, 0));
        }
    }
}
