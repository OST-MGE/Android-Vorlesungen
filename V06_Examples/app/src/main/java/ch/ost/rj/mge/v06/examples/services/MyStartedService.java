package ch.ost.rj.mge.v06.examples.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyStartedService extends Service {
    public static final String SERVICE_PI_KEY = "service.pi";
    public static final String SERVICE_RESULT_KEY = "service.result";

    private static final String LOG_TAG = "MGE.V06.DEBUG";

    private boolean stopRequested;

    @Override
    public void onCreate() {
        super.onCreate();
        logMessage("onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        logMessage("onStartCommand: " + startId);

        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}

                if (stopRequested)
                    break;

                logMessage("onStartCommand: ID " + startId + " | Durchgang " + i);
            }

            if (!stopRequested) {
                try {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(SERVICE_RESULT_KEY, startId);

                    PendingIntent pendingIntent = intent.getParcelableExtra(SERVICE_PI_KEY);
                    pendingIntent.send(this, 0, resultIntent);
                } catch (PendingIntent.CanceledException ignored) {}
            }

            stopSelf(startId);
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        logMessage("onDestroy");
        stopRequested = true;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Nicht nötig für Started Service
    }

    private static void logMessage(String message) {
        Log.d(LOG_TAG, "Started Service | " + message);
    }
}
