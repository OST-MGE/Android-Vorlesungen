package ch.ost.rj.mge.v04.examples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OutputFragmentCallback {
    private int fragmentCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpForStaticIntegration();
        //setUpForDynamicIntegration();
        //setUpForDynamicAnimations();
        //setUpForStyling();
    }

    @Override
    public void onTextTapped(String text) {
        Toast.makeText(this, "Callback - " + text, Toast.LENGTH_SHORT).show();
    }

    private void setUpForStaticIntegration() {
        setContentView(R.layout.activity_main_static);
    }

    private void setUpForDynamicIntegration() {
        setContentView(R.layout.activity_main_dynamic);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        OutputFragment fragment = OutputFragment.create("Hello from MainActivity");
        fragmentTransaction.add(R.id.main_fragment_container, fragment);
        fragmentTransaction.commit();

        Button button = findViewById(R.id.main_button);
        button.setOnClickListener(v -> {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String now = formatter.format(new Date());
            fragment.updateText(now);
        });
    }

    private void setUpForDynamicAnimations() {
        setContentView(R.layout.activity_main_dynamic);

        FragmentManager fragmentManager = getSupportFragmentManager();

        OutputFragment fragment = OutputFragment.create("Hello from MainActivity " + fragmentCounter);

        fragmentManager.beginTransaction()
                .add(R.id.main_fragment_container, fragment)
                .commit();

        Button button = findViewById(R.id.main_button);
        button.setOnClickListener(v -> {
            fragmentCounter++;
            OutputFragment newFragment = OutputFragment.create("Hello from MainActivity " + fragmentCounter);

            fragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // Enter NEW
                            R.anim.fade_out,  // Exit OLD
                            R.anim.fade_in,   // Enter OLD (Popping NEW)
                            R.anim.slide_out  // Exit NEW (Popping NEW)
                    )
                    .replace(R.id.main_fragment_container, newFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void setUpForStyling() {
        // Do not forget to change Theme in styles.xml: Theme.MaterialComponents.Light.DarkActionBar
        setTheme(R.style.AppTheme_Customized);
        setContentView(R.layout.activity_styling);
    }
}