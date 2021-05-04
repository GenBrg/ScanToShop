package com.example.scantoshop.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.ShoppingItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryShoppingListAdapter extends RecyclerView.Adapter<HistoryShoppingListAdapter.ViewHolder> {
    private List<ShoppingHistory> shoppingHistory = new ArrayList<>();
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HistoryShoppingListAdapter() {
        List<ShoppingItem> history1, history2;
        history1 = new ArrayList<>();
        history2 = new ArrayList<>();

        history1.add(new ShoppingItem("Apple", 3));
        history1.add(new ShoppingItem("Orange", 3));
        history1.add(new ShoppingItem("Banana", 4));

        history2.add(new ShoppingItem("bbb", 5));

        shoppingHistory.add(new ShoppingHistory(history1, new Date()));
        shoppingHistory.add(new ShoppingHistory(history2, new Date()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView historyTitle;
        private RecyclerView historyDetailView;
//        private Button historyDetailButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            historyTitle = itemView.findViewById(R.id.history_title);
            historyDetailView = itemView.findViewById(R.id.history_detail_view);
//            historyDetailButton = itemView.findViewById(R.id.history_detail_button);
            historyDetailView.setAdapter(new HistoryShoppingListDetailAdapter());
            historyDetailView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_history_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingHistory history = shoppingHistory.get(position);

        holder.historyTitle.setText(dateFormat.format(history.getDate()) + " Total: " + history.size());
        ((HistoryShoppingListDetailAdapter)holder.historyDetailView.getAdapter()).setShoppingItems(history.getShoppingItems());
    }

    @Override
    public int getItemCount() {
        return shoppingHistory.size();
    }
}
