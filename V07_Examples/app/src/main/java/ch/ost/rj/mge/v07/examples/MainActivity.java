package ch.ost.rj.mge.v07.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import ch.ost.rj.mge.v07.examples.viewbinding.ViewBindingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.button_view_binding).setOnClickListener(v -> startActivity(ViewBindingActivity.class));

        findViewById(R.id.button_data_binding_pojo).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.databinding.pojo.DataBindingActivity.class));
        findViewById(R.id.button_data_binding_observable_fields).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.databinding.observablefields.DataBindingActivity.class));
        findViewById(R.id.button_data_binding_observable_class).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.databinding.observableclass.DataBindingActivity.class));
        findViewById(R.id.button_data_binding_live_data).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.databinding.livedata.DataBindingActivity.class));

        findViewById(R.id.button_view_model_observable_fields).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.viewmodel.observablefields.ViewModelActivity.class));
        findViewById(R.id.button_view_model_live_data).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.viewmodel.livedata.ViewModelActivity.class));
        findViewById(R.id.button_view_model_base_class).setOnClickListener(v -> startActivity(ch.ost.rj.mge.v07.examples.viewmodel.baseclass.ViewModelActivity.class));
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}