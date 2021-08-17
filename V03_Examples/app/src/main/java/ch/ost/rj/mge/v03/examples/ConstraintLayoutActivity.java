package ch.ost.rj.mge.v03.examples;

import android.os.Bundle;
import android.transition.TransitionManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class ConstraintLayoutActivity extends AppCompatActivity {
    private boolean displaysFirstLayout = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);
        setupForAnimation();
    }

    private void setupForAnimation() {
        TextView textViewA = findViewById(R.id.txtA);
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout);

        textViewA.setOnClickListener(v -> {
            int targetLayout = displaysFirstLayout ?
                    R.layout.activity_constraint_2 :
                    R.layout.activity_constraint;

            TransitionManager.beginDelayedTransition(constraintLayout);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.load(this, targetLayout);
            constraintSet.applyTo(constraintLayout);

            displaysFirstLayout = !displaysFirstLayout;
        });
    }
}
