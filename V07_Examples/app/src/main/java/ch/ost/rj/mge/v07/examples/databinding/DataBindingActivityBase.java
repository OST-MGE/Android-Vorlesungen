package ch.ost.rj.mge.v07.examples.databinding;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public abstract class DataBindingActivityBase extends AppCompatActivity {
    private static final String LOG_TAG = "MGE.V07.DEBUG";

    protected static String Firstname = "Thomas";
    protected static String Lastname = "Kälin";
    protected static int Age = 37;

    protected void Log(String method, String message) {
        Log.d(LOG_TAG, "DataBindingActivity." + method + ": " + message);
    }
}