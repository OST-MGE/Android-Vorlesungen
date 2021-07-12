package ch.ost.rj.mge.v03.examples;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.button_padding_and_margin).setOnClickListener(v -> startActivity(PaddingAndMarginActivity.class));
        findViewById(R.id.button_linear_layout_vertical).setOnClickListener(v -> startActivity(LinearLayoutVerticalActivity.class));
        findViewById(R.id.button_linear_layout_horizontal).setOnClickListener(v -> startActivity(LinearLayoutHorizontalActivity.class));
        findViewById(R.id.button_frame_layout).setOnClickListener(v -> startActivity(FrameLayoutActivity.class));
        findViewById(R.id.button_relative_layout).setOnClickListener(v -> startActivity(RelativeLayoutActivity.class));
        findViewById(R.id.button_constraint_layout).setOnClickListener(v -> startActivity(ConstraintLayoutActivity.class));

        findViewById(R.id.button_widgets_normal).setOnClickListener(v -> startActivity(WidgetsNormalActivity.class));
        findViewById(R.id.button_widgets_api).setOnClickListener(v -> startActivity(WidgetsApiActivity.class));
        findViewById(R.id.button_widgets_menu).setOnClickListener(v -> startActivity(WidgetsMenuActivity.class));

        findViewById(R.id.button_list_array_adapter).setOnClickListener(v -> startActivity(ListArrayAdapterActivity.class));
        findViewById(R.id.button_list_custom_adapter).setOnClickListener(v -> startActivity(ListCustomAdapterActivity.class));
        findViewById(R.id.button_list_recycler_view).setOnClickListener(v -> startActivity(ListRecyclerViewActivity.class));
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}