package ch.ost.rj.mge.v06.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ch.ost.rj.mge.v06.examples.backgrounding.SleepingAsyncTask;
import ch.ost.rj.mge.v06.examples.broadcasts.MyBroadcastReceiver;
import ch.ost.rj.mge.v06.examples.observer.presentation.ObserverActivity;
import ch.ost.rj.mge.v06.examples.services.MyBoundService;
import ch.ost.rj.mge.v06.examples.services.MyStartedService;

public class MainActivity extends AppCompatActivity implements ServiceConnection {
    private TextView asyncOutputTextView;
    private Button runAsyncTaskButton;
    private Button runWithExecutorButton;
    private Button connectToBoundServiceButton;
    private Button interactWithBoundServiceButton;
    private Button disconnectFromBoundServiceButton;

    private BroadcastReceiver receiver;
    private MyBoundService boundService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Observer Demo
        findViewById(R.id.button_start_observer_demo).setOnClickListener(v -> {
            Intent intent = new Intent(this, ObserverActivity.class);
            startActivity(intent);
        });

        // Broadcasts
        Button registerReceiverButton = findViewById(R.id.button_register_receiver);
        Button unregisterReceiverButton = findViewById(R.id.button_unregister_receiver);
        Button sendImplicitBroadcastButton = findViewById(R.id.button_send_broadcast_implicit);
        Button sendExplicitBroadcastButton = findViewById(R.id.button_send_broadcast_explicit);

        registerReceiverButton.setOnClickListener(v -> {
            registerBroadcastReceiver();
            registerReceiverButton.setEnabled(false);
            unregisterReceiverButton.setEnabled(true);
        });

        unregisterReceiverButton.setOnClickListener(v -> {
            unregisterBroadcastReceiver();
            registerReceiverButton.setEnabled(true);
            unregisterReceiverButton.setEnabled(false);
        });

        sendImplicitBroadcastButton.setOnClickListener(v -> sendImplicitBroadcast());
        sendExplicitBroadcastButton.setOnClickListener(v -> sendExplicitBroadcast());

        // Services
        Button runStartedServiceButton = findViewById(R.id.button_run_started_service);
        Button stopStartedServiceButton = findViewById(R.id.button_stop_started_service);
        connectToBoundServiceButton = findViewById(R.id.button_connect_to_bound_service);
        interactWithBoundServiceButton = findViewById(R.id.button_interact_with_bound_service);
        disconnectFromBoundServiceButton = findViewById(R.id.button_disconnect_from_bound_service);

        runStartedServiceButton.setOnClickListener(v -> {
            runStartedService();
            stopStartedServiceButton.setEnabled(true);
        });

        stopStartedServiceButton.setOnClickListener(v -> {
            stopStartedService();
            stopStartedServiceButton.setEnabled(false);
        });

        connectToBoundServiceButton.setOnClickListener(v -> connectToBoundService());
        interactWithBoundServiceButton.setOnClickListener(v -> interactWithBoundService());
        disconnectFromBoundServiceButton.setOnClickListener(v -> disconnectFromBoundService());

        // Backgrounding
        asyncOutputTextView = findViewById(R.id.textview_async_output);
        runAsyncTaskButton = findViewById(R.id.button_run_async_task);
        runWithExecutorButton = findViewById(R.id.button_run_executor);

        runAsyncTaskButton.setOnClickListener(v -> runAsyncTask());
        runWithExecutorButton.setOnClickListener(v -> runExecutorTask());
    }

    // region Broadcasts

    private void registerBroadcastReceiver() {
        receiver = new MyBroadcastReceiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(MyBroadcastReceiver.BROADCAST_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(receiver, filter, RECEIVER_EXPORTED);
        }
        else
        {
            registerReceiver(receiver, filter);
        }
    }

    private void unregisterBroadcastReceiver() {
        unregisterReceiver(receiver);
        receiver = null;
    }

    private void sendImplicitBroadcast() {
        // Can be simulated using:
        // adb shell am broadcast -a ch.ost.rj.mge.v06.myapplication.MY_INTENT --es data "Payload\ of\ Implicit\ Broadcast"
        Intent intent = new Intent();
        intent.setAction(MyBroadcastReceiver.BROADCAST_ACTION);
        intent.putExtra(MyBroadcastReceiver.BROADCAST_EXTRA,"Payload of Implicit Broadcast");
        sendBroadcast(intent);
    }

    private void sendExplicitBroadcast() {
        // Can be simulated using:
        // adb shell am broadcast -a ch.ost.rj.mge.v06.myapplication.MY_INTENT --es data "Payload\ of\ Explicit\ Broadcast" -n ch.ost.rj.mge.v06.examples/.broadcasts.MyBroadcastReceiver
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        intent.setAction(MyBroadcastReceiver.BROADCAST_ACTION);
        intent.putExtra(MyBroadcastReceiver.BROADCAST_EXTRA,"Payload of Explicit Broadcast");
        sendBroadcast(intent);
    }

    // endregion

    // region Services

    private void runStartedService() {
        Intent broadcastIntent = new Intent(this, MyBroadcastReceiver.class);
        broadcastIntent.setAction(MyBroadcastReceiver.BROADCAST_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_MUTABLE);

        Intent intent = new Intent(this, MyStartedService.class);
        intent.putExtra(MyStartedService.SERVICE_PI_KEY, pendingIntent);
        startService(intent);
    }

    private void stopStartedService() {
        Intent intent = new Intent(this, MyStartedService.class);
        stopService(intent);
    }

    private void connectToBoundService() {
        Intent intent = new Intent(this, MyBoundService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    private void interactWithBoundService() {
        boundService.showToast("Hallo aus der MainActivity");
    }

    private void disconnectFromBoundService() {
        unbindService(this);
        onServiceDisconnected(null);
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        connectToBoundServiceButton.setEnabled(false);
        interactWithBoundServiceButton.setEnabled(true);
        disconnectFromBoundServiceButton.setEnabled(true);

        MyBoundService.MyBinder binder = (MyBoundService.MyBinder) iBinder;
        boundService = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        // only called on problems, not normal unbind!
        connectToBoundServiceButton.setEnabled(true);
        interactWithBoundServiceButton.setEnabled(false);
        disconnectFromBoundServiceButton.setEnabled(false);

        boundService = null;
    }

    // endregion

    // region Backgrounding

    private void runAsyncTask() {
        new SleepingAsyncTask(runAsyncTaskButton, asyncOutputTextView).execute(5);
    }

    @SuppressLint("SetTextI18n")
    private void runExecutorTask() {
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            handler.post(() -> runWithExecutorButton.setEnabled(false));

            for (int i = 1; i <= 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}

                final String progress = "Durchgang " + i;
                handler.post(() -> asyncOutputTextView.setText(progress));
            }

            handler.post(() -> {
                asyncOutputTextView.setText("Berechnung komplett");
                runWithExecutorButton.setEnabled(true);
            });
        });
    }

    // endregion
}