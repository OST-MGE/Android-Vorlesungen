package ch.ost.rj.mge.v05.examples;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.ost.rj.mge.v05.examples.hardware.HardwareActivity;
import ch.ost.rj.mge.v05.examples.permission.PermissionActivity;
import ch.ost.rj.mge.v05.examples.persistence.PersistenceActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.button_permissions).setOnClickListener(v -> startActivity(PermissionActivity.class));
        findViewById(R.id.button_persistence).setOnClickListener(v -> startActivity(PersistenceActivity.class));
        findViewById(R.id.button_hardware).setOnClickListener(v -> startActivity(HardwareActivity.class));
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}