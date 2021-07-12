package ch.ost.rj.mge.v03.examples;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ch.ost.rj.mge.v03.examples.adapter.UsersAdapter2;
import ch.ost.rj.mge.v03.examples.model.User;
import ch.ost.rj.mge.v03.examples.model.UserManager;

public class ListRecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<User> data = UserManager.getUsers();
        UsersAdapter2 adapter = new UsersAdapter2(data);
        recyclerView.setAdapter(adapter);
    }
}
