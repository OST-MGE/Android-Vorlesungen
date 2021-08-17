package ch.ost.rj.mge.v04.examples;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.button_fragments_static_integration).setOnClickListener(v -> startActivity(StaticIntegrationActivity.class));
        findViewById(R.id.button_fragments_dynamic_integration).setOnClickListener(v -> startActivity(DynamicIntegrationActivity.class));
        findViewById(R.id.button_fragments_dynamic_integration_with_animations).setOnClickListener(v -> startActivity(DynamicIntegrationAnimatedActivity.class));
        findViewById(R.id.button_styling_with_custom_theme).setOnClickListener(v -> startActivity(StylingActivity.class));
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}