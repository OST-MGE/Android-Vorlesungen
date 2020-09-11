package ch.ost.rj.mge.v06.myapplication.observer.presentation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import ch.ost.rj.mge.v06.myapplication.R;
import ch.ost.rj.mge.v06.myapplication.observer.domain.Stock;

public class StocksAdapter extends RecyclerView.Adapter<StocksAdapter.ViewHolder> implements Observer {
    private ArrayList<Stock> dataset;
    private Handler handler;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView value;
        TextView date;

        ViewHolder(View parent, TextView name, TextView value, TextView date) {
            super(parent);
            this.name = name;
            this.value = value;
            this.date = date;
        }
    }

    public StocksAdapter(ArrayList<Stock> stocks) {
        dataset = stocks;

        createHandler();

        for (Stock stock : stocks) {
            stock.addObserver(this);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.list_item_stock, parent, false);
        TextView name = view.findViewById(R.id.text_view_name);
        TextView value = view.findViewById(R.id.text_view_value);
        TextView date = view.findViewById(R.id.text_view_date);

        return new ViewHolder(view, name, value, date);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Stock stock = dataset.get(position);

        holder.name.setText(stock.getName());

        String date = new SimpleDateFormat("HH:mm:ss").format(stock.getDate());
        holder.date.setText(date);

        String price = String.format("%.2f", stock.getValue());
        holder.value.setText(price);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public void update(Observable observable, Object data) {
        final int position = dataset.indexOf(observable);

        this.handler.post(() -> notifyItemChanged(position));
    }

    public void refreshAll() {
        notifyDataSetChanged();
    }


    private void createHandler() {
        this.handler = new Handler(Looper.getMainLooper());
    }
}