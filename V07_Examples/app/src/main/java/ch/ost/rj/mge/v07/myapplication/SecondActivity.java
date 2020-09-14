package ch.ost.rj.mge.v07.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ch.ost.rj.mge.v07.myapplication.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        binding.setHandler(DataBindingActivity.handler);

        setContentView(binding.getRoot());
    }
}