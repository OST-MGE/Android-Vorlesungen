package ch.ost.rj.mge.v04.examples;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public abstract class ActivityBase extends AppCompatActivity implements FragmentCallback {
    @Override
    public void showMessage(String text) {
        Toast.makeText(this, "Fragment Callback: " + text, Toast.LENGTH_SHORT).show();
    }
}
