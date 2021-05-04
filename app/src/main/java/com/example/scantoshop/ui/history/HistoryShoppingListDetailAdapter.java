package com.example.scantoshop.ui.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.Entity.ItemHistoryCrossRef;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.ItemDescriptionActivity;
import com.example.scantoshop.ui.shoplist.ShoppingItem;
import com.example.scantoshop.util.AppDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HistoryShoppingListDetailAdapter extends RecyclerView.Adapter<HistoryShoppingListDetailAdapter.ViewHolder> {
    private List<ItemHistoryCrossRef> shoppingItems = new ArrayList<>();
    private Context context;
    private View itemView;

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
        context = parent.getContext();
        itemView = LayoutInflater.from(context).inflate(R.layout.shopping_history_item_detail, parent, false);
        return new HistoryShoppingListDetailAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemHistoryCrossRef itemHistoryCrossRef = shoppingItems.get(position);
        Item item = AppDatabase.getInstance(context).itemDAO().loadItemByUPC(itemHistoryCrossRef.upc)[0];
        holder.quantity.setText(String.valueOf(itemHistoryCrossRef.quantity));
        Picasso.with(context).load(item.image_path).placeholder(R.drawable.no_image_available).into(holder.itemImage);

        holder.itemImage.setOnClickListener(v->{
            Intent intent = new Intent(context, ItemDescriptionActivity.class);
            Bundle b = new Bundle();
            b.putString("upc", itemHistoryCrossRef.upc);
            intent.putExtras(b);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public void setShoppingItems(List<ItemHistoryCrossRef> items) {
        this.shoppingItems = items;
        notifyDataSetChanged();
    }
}
