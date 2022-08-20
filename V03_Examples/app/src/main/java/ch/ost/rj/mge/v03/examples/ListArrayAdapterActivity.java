package ch.ost.rj.mge.v03.examples;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ListArrayAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview);

        final int ITEMS = 100;
        String[] data = new String[ITEMS];
        for (int i = 1; i <= ITEMS; i++)
            data[i-1] = "Item " + i;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data);

        ListView listView = findViewById(R.id.list_example);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, "Item " + (i + 1), Toast.LENGTH_SHORT).show();
        });
    }
}
