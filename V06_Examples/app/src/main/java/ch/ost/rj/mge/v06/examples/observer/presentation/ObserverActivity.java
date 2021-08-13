package ch.ost.rj.mge.v06.examples.observer.presentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ch.ost.rj.mge.v06.examples.R;
import ch.ost.rj.mge.v06.examples.observer.domain.Stock;

public class ObserverActivity extends AppCompatActivity implements Observer {

    private StocksAdapter adapter;
    private TextView lastTradeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        RecyclerView recyclerView = findViewById(R.id.observer_recycler_view);
        lastTradeTextView = findViewById(R.id.observer_textview_last_trade);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("Apple", 121.0));
        stocks.add(new Stock("Amazon", 530.0));
        stocks.add(new Stock("Yahoo", 37.0));
        stocks.add(new Stock("Microsoft", 46.0));
        stocks.add(new Stock("Oracle", 39.0));
        stocks.add(new Stock("Red Hat", 79.0));

        adapter = new StocksAdapter(stocks);
        recyclerView.setAdapter(adapter);

        for (Stock stock : stocks) {
            stock.addObserver(this);
        }

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.refreshAll();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void update(Observable observable, Object data) {
        final Stock stock = (Stock) observable;
        runOnUiThread(() -> {
            String date = new SimpleDateFormat("HH:mm:ss").format(stock.getDate());
            lastTradeTextView.setText("Last Trade: " + date);
        });
    }
}