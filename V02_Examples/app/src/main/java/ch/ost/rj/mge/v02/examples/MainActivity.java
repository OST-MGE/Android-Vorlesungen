package ch.ost.rj.mge.v02.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logStateChange("onCreate");

        final TextView textView = findViewById(R.id.textField);

        // Expliziter Intent
        Button explicitIntentButton = findViewById(R.id.buttonExplicitIntent);
        explicitIntentButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("myKey", 42);
            startActivity(intent);
        });

        // Impliziter Intent
        Button implicitIntentButton = findViewById(R.id.buttonImplicitIntent);
        implicitIntentButton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hey!");
            startActivity(intent);
        });

        // Threading: UI Blockierung
        Button uiBlockingButton = findViewById(R.id.buttonBlockUI);
        uiBlockingButton.setOnClickListener(v -> {
            sleep();
            textView.setText("Aktualisiert nach Blockierung");
        });

        // Threading: UI Blockierung (korrekt)
        Button uiNonBlockingButton = findViewById(R.id.buttonNonBlockUI);
        uiNonBlockingButton.setOnClickListener(v -> {
            final Runnable updateUI = () -> {
                textView.setText("Aktualisierung ohne Blockierung");
            };

            Runnable background = () -> {
                sleep();

                // Option 1
                this.runOnUiThread(updateUI);

                // Option 2
                textView.post(updateUI);

                // Option 3
                Looper looper = Looper.getMainLooper();
                Handler handler = new Handler(looper);
                handler.post(updateUI);
            };

            Thread thread = new Thread(background);
            thread.start();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        logStateChange("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();

        logStateChange("onResume");
    }

    private static void logStateChange(String callback) {
        Log.d("MGE.V02.DEBUG", "Method: " + callback);
    }

    private static void sleep()
    {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }
}