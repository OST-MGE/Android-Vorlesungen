package ch.ost.rj.mge.v04.examples;

import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;

public class DynamicIntegrationAnimatedActivity extends ActivityBase {
    private int fragmentCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dynamic_integration);

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentA fragment = FragmentA.create("Dynamic Integration with Animation: " + fragmentCounter);

        fragmentManager.beginTransaction()
                .add(R.id.container_for_fragment, fragment)
                .commit();

        Button button = findViewById(R.id.button_to_change_text);
        button.setOnClickListener(v -> {
            fragmentCounter++;
            FragmentA newFragment = FragmentA.create("Dynamic Integration with Animation: " + fragmentCounter);

            fragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in,  // Enter NEW
                            R.anim.fade_out,  // Exit OLD
                            R.anim.fade_in,   // Enter OLD (Popping NEW)
                            R.anim.slide_out  // Exit NEW (Popping NEW)
                    )
                    .replace(R.id.container_for_fragment, newFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}