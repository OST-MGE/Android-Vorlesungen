package ch.ost.rj.mge.v06.examples.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyBoundService extends Service {
    private static final String LOG_TAG = "MGE.V06.DEBUG";

    public class MyBinder extends Binder {
        public MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    private final IBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        logMessage("onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        logMessage("onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        logMessage("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        logMessage("onDestroy");
        super.onDestroy();
    }

    public void showToast(String message) {
        logMessage("showToast");
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private static void logMessage(String message) {
        Log.d(LOG_TAG, "Bound Service | " + message);
    }
}
