package com.example.scantoshop.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.Entity.ItemHistoryCrossRef;
import com.example.scantoshop.Entity.PurchaseHistory;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryShoppingListAdapter extends RecyclerView.Adapter<HistoryShoppingListAdapter.ViewHolder> {
    private List<PurchaseHistory> shoppingHistory = new ArrayList<>();
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public HistoryShoppingListAdapter() {}

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
        PurchaseHistory history = shoppingHistory.get(position);
        List<ItemHistoryCrossRef> items = AppDatabase.getInstance(context).historyDAO().loadItemHistoryCrossRefPID(history.pid);

        holder.historyTitle.setText(dateFormat.format(new Date(history.purchase_date)) + " Total: " + items.size());
        ((HistoryShoppingListDetailAdapter)holder.historyDetailView.getAdapter()).setShoppingItems(items);
    }

    @Override
    public int getItemCount() {
        return shoppingHistory.size();
    }

    public void setShoppingHistory(List<PurchaseHistory> shoppingHistory) {
        this.shoppingHistory = shoppingHistory;
        notifyDataSetChanged();
    }
}
