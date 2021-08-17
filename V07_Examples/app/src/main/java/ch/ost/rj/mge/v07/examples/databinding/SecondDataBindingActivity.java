package ch.ost.rj.mge.v07.examples.databinding;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SecondDataBindingActivity extends AppCompatActivity {
    public static EventHandlerBase eventHandler;
    private ActivityDataBindingSecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDataBindingSecondBinding.inflate(getLayoutInflater());
        binding.setHandler(eventHandler);

        setContentView(binding.getRoot());
    }
}