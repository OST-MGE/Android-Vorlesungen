package ch.ost.rj.mge.v04.examples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StylingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_Customized);
        setContentView(R.layout.activity_styling);
    }
}