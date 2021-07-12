package ch.ost.rj.mge.v03.examples;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ch.ost.rj.mge.v03.examples.adapter.UsersAdapter;
import ch.ost.rj.mge.v03.examples.model.User;
import ch.ost.rj.mge.v03.examples.model.UserManager;

public class ListCustomAdapterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_listview);

        ArrayList<User> data = UserManager.getUsers();
        UsersAdapter adapter = new UsersAdapter(this, data);

        ListView listView = findViewById(R.id.list_example);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, "Item " + i, Toast.LENGTH_SHORT).show();
        });
    }
}
