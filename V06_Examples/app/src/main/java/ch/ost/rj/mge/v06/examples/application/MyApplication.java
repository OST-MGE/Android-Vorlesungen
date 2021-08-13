package ch.ost.rj.mge.v06.examples.application;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String LOG_TAG = "MGE.V06.DEBUG";

    private static void log(String message) {
        Log.d(LOG_TAG, message);
    }

    //region Overrides

    @Override
    public void onCreate() {
        super.onCreate();
        log("MyApplication.onCreate");
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onTerminate() {
        log("MyApplication.onTerminate");
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        log("MyApplication.onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        log("MyApplication.onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        log("MyApplication.onTrimMemory: " + level);
    }

    //endregion

    //region ActivityLifecycleCallbacks

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        log("MyApplication.onActivityCreated: " + activity.getLocalClassName());

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        log("MyApplication.onActivityStarted: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        log("MyApplication.onActivityResumed: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        log("MyApplication.onActivityPaused: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        log("MyApplication.onActivityStopped: " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
        log("MyApplication.onActivitySaveInstanceState: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        log("MyApplication.onActivityDestroyed: " + activity.getLocalClassName());
    }

    //endregion
}
