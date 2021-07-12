package ch.ost.rj.mge.v03.examples;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.material.snackbar.Snackbar;

public class WidgetsApiActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_widgets_api);

        setupToast();
        SetupSnackbar();
        SetupDialog();
        SetupNotification();
    }

    private void setupToast() {
        Button toastButton = findViewById(R.id.button_toast);
        toastButton.setOnClickListener(v -> {
            Context context = this;
            String text = "MGE rocks!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });
    }

    private void SetupSnackbar() {
        Button snackbarButton = findViewById(R.id.button_snackbar);
        snackbarButton.setOnClickListener(b -> {
            ViewGroup parent = findViewById(R.id.layout_system_widgets);
            String text = "MGE rocks!";
            int duration = Snackbar.LENGTH_LONG;
            String action = "Schliessen";

            Snackbar snackbar = Snackbar.make(parent,text, duration);
            snackbar.setAction(action, v -> snackbar.dismiss());
            snackbar.show();
        });
    }

    private void SetupDialog() {
        Button dialogButton = findViewById(R.id.button_dialog);
        dialogButton.setOnClickListener(b -> {
            AlertDialog dialog;

            dialog = new AlertDialog.Builder(this)
                    .setTitle("Beispiel")
                    .setMessage("Gutes Beispiel?")
                    .setCancelable(false)
                    .setPositiveButton("Ja", (d, id) -> dialogButton.setText("Dialog: ja"))
                    .setNegativeButton("Nein", (d, id) -> dialogButton.setText("Dialog: nein"))
                    .create();

            /*
            dialog = new AlertDialog.Builder(this)
                    .setTitle("Auswahl")
                    .setCancelable(false)
                    .setSingleChoiceItems(new String[]{ "Option 1", "Option 2", "Option 3" }, 0, (d, id) -> {})
                    //.setMultiChoiceItems(new String[]{ "Option 1", "Option 2", "Option 3" }, new boolean[] { false, false, true }, (d, id, x) -> {})
                    .setPositiveButton("OK", (d, id) -> {})
                    .create();
             */

            dialog.show();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SetupNotification() {
        final String CHANNEL_ID = "MGE_Channel";
        final String CHANNEL_NAME = "MGE Notifications";
        final String CHANNEL_DESCRIPTION = "Ein Channel für die MGE Demo";
        final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
        final int[] notificationId = { 1 };

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            channel.setDescription(CHANNEL_DESCRIPTION);

            manager.createNotificationChannel(channel);
        }

        Button notificationButton = findViewById(R.id.button_notification);
        notificationButton.setOnClickListener(b -> {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_emoji)
                    .setContentTitle("MGE")
                    .setContentText("MGE rocks!")
                    .build();

            manager.notify(notificationId[0], notification);

            notificationId[0]++;
        });
    }
}
