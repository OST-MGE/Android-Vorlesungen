package ch.ost.rj.mge.v04.examples;

import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DynamicIntegrationActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_integration);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentA fragment = FragmentA.create("Dynamic Integration without Animation");
        fragmentTransaction.add(R.id.container_for_fragment, fragment);
        fragmentTransaction.commit();

        Button button = findViewById(R.id.button_to_change_text);
        button.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String now = formatter.format(new Date());
            fragment.updateText(now);
        });
    }
}