package ch.ost.rj.mge.v03.examples;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WidgetsMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widgets_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_example, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuIndex = 0;

        switch (item.getItemId()) {
            case R.id.menu_1:
                menuIndex = 1;
                break;

            case R.id.menu_2:
                menuIndex = 2;
                break;

            case R.id.menu_3:
                menuIndex = 3;
                break;

            case R.id.menu_4:
                menuIndex = 4;
                break;
        }

        TextView outputText = findViewById(R.id.text_menu_selection);
        outputText.setText("Auswahl: " + menuIndex);

        return true;
    }
}
