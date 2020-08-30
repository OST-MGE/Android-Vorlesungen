package ch.ost.rj.mge.v02.examples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        int parameter = getIntent().getIntExtra("myKey", 0);

        TextView parameterOutputView = findViewById(R.id.textParameterOutput);
        parameterOutputView.setText("Parameter: " + parameter);
    }
}