package ch.ost.rj.mge.v05.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class PermissionActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "MGE.V05";

    private static final int PHONE_PERMISSION_CODE = 1;

    Button openDialerButton;
    Button startCallButton;
    Button requestPermissionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_permission);

        openDialerButton = findViewById(R.id.button_open_dialer);
        openDialerButton.setOnClickListener(v -> openDialerApp());

        startCallButton = findViewById(R.id.button_start_call);
        startCallButton.setOnClickListener(v -> startPhoneCall());

        requestPermissionButton = findViewById(R.id.button_request_permission);
        requestPermissionButton.setOnClickListener(v -> requestPhoneCallPermission());
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean granted = checkPermission(Manifest.permission.CALL_PHONE);
        updateButtonAvailability(granted);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PHONE_PERMISSION_CODE) {
            boolean granted = checkPermission(grantResults);
            updateButtonAvailability(granted);
        }
    }

    private void openDialerApp() {
        // Keine Berechtigungen nötig
        Intent intent = createPhoneIntent(Intent.ACTION_DIAL);
        startActivity(intent);
    }

    private void startPhoneCall() {
        // Berechtigungen nötig
        Intent intent = createPhoneIntent(Intent.ACTION_CALL);
        startActivity(intent);
    }

    private void requestPhoneCallPermission() {
        requestPermission(Manifest.permission.CALL_PHONE, PHONE_PERMISSION_CODE);
    }

    private Intent createPhoneIntent(String action) {
        return new Intent(action, Uri.parse("tel:0041790123"));
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkPermission(int[] grantResults) {
        if (grantResults.length == 0) {
            return false;
        } else {
            return grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    @SuppressLint("NewApi")
    private void requestPermission(String permission, int requestCode) {
        if (checkPermission(permission))
            return;

        if (shouldShowRequestPermissionRationale(permission)) {
            Log.d(DEBUG_TAG, "Erklärung anzeigen für: " + permission);
        }

        requestPermissions(new String[]{ permission }, requestCode);
    }

    private void updateButtonAvailability(boolean permissionGranted) {
        startCallButton.setEnabled(permissionGranted);
        requestPermissionButton.setEnabled(!permissionGranted);
    }
}