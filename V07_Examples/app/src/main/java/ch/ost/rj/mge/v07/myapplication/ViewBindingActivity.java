package ch.ost.rj.mge.v07.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import ch.ost.rj.mge.v07.myapplication.databinding.ActivityViewBindingBinding;

public class ViewBindingActivity extends AppCompatActivity {

    private ActivityViewBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = getLayoutInflater();
        binding = ActivityViewBindingBinding.inflate(inflater);
        setContentView(binding.getRoot());

        binding.vbButton.setOnClickListener(v -> {
            Toast.makeText(this, "Button pressed", Toast.LENGTH_SHORT).show();
        });
    }
}