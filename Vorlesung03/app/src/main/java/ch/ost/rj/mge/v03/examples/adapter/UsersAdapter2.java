package ch.ost.rj.mge.v03.examples.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ch.ost.rj.mge.v03.examples.model.User;

public class UsersAdapter2 extends RecyclerView.Adapter<UsersAdapter2.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text1;
        public TextView text2;

        public ViewHolder(View parent, TextView t1, TextView t2) {
            super(parent);
            text1 = t1;
            text2 = t2;
        }
    }

    private ArrayList<User> users;

    public UsersAdapter2(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(
                android.R.layout.simple_list_item_2,
                parent,
                false);

        TextView text1 = view.findViewById(android.R.id.text1);
        TextView text2 = view.findViewById(android.R.id.text2);

        return new ViewHolder(view, text1, text2);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = this.users.get(position);
        holder.text1.setText(user.name);
        holder.text2.setText(user.age + " Jahre");
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }
}
