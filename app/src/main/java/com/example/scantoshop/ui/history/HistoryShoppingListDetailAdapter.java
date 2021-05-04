package com.example.scantoshop.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryShoppingListDetailAdapter extends RecyclerView.Adapter<HistoryShoppingListDetailAdapter.ViewHolder> {
    List<ShoppingItem> shoppingItems = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.history_detail_item_image);
            quantity = itemView.findViewById(R.id.history_detail_item_quantity);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryShoppingListDetailAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_history_item_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppingItem item = shoppingItems.get(position);

        holder.quantity.setText(String.valueOf(item.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
        notifyDataSetChanged();
    }
}
