package com.example.scantoshop.ui.shoplist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scantoshop.DAO.EntryDAO;
import com.example.scantoshop.DAO.HistoryDAO;
import com.example.scantoshop.DAO.ItemDAO;
import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.CurrentShoppingListEntry;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.Entity.ItemHistoryCrossRef;
import com.example.scantoshop.Entity.PurchaseHistory;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CurrentShoppingListAdapter extends RecyclerView.Adapter<CurrentShoppingListAdapter.ViewHolder> {
    private List<CurrentShoppingListEntry> shoppingList = new ArrayList<>();
    private Context context;
    private View itemView;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageButton upButton, downButton, deleteButton;
        private TextView quantity;
        private ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            upButton = itemView.findViewById(R.id.add_item_button);
            downButton = itemView.findViewById(R.id.remove_item_button);
            deleteButton = itemView.findViewById(R.id.item_delete);
            quantity = itemView.findViewById(R.id.item_quantity);
            itemImage = itemView.findViewById(R.id.item_img);
        }
    }

    public CurrentShoppingListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemView = LayoutInflater.from(context).inflate(R.layout.shopping_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CurrentShoppingListEntry entry = shoppingList.get(position);
        Item item = AppDatabase.getInstance(context).itemDAO().loadItemByUPC(entry.upc)[0];

        holder.upButton.setOnClickListener(v->{
            setQuantity(entry, entry.quantity + 1);
        });
        holder.downButton.setOnClickListener(v->{
            setQuantity(entry, entry.quantity - 1);
        });
        holder.deleteButton.setOnClickListener(v->{
            deleteItem(position);
        });

        holder.quantity.setText(String.valueOf(entry.quantity));

        Picasso.with(context).load(item.image_path).placeholder(R.drawable.no_image_available).into(holder.itemImage);

        holder.itemImage.setOnClickListener(v->{
            Intent intent = new Intent(context, ItemDescriptionActivity.class);
            Bundle b = new Bundle();
            b.putString("upc", entry.upc);
            intent.putExtras(b);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    public void setShoppingList(List<CurrentShoppingListEntry> shoppingList) {
        this.shoppingList = shoppingList;
        notifyDataSetChanged();
    }

    private void setQuantity(CurrentShoppingListEntry entry, int quantity) {
        if (quantity <= 0) {
            return;
        }
        entry.quantity = quantity;
        AppDatabase.getInstance(context).entryDAO().updateEntry(entry);

        notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        CurrentShoppingListEntry entry = shoppingList.get(position);
        AppDatabase.getInstance(context).entryDAO().deleteEntries(entry);

        shoppingList.remove(position);
        notifyDataSetChanged();
    }

    void commitShoppingList() {
        if (shoppingList.size() == 0) {
            Toast.makeText(context, "Shopping list empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        AppDatabase db = AppDatabase.getInstance(context);
        EntryDAO entryDAO = db.entryDAO();
        HistoryDAO historyDAO = db.historyDAO();

        PurchaseHistory purchaseHistory = new PurchaseHistory("1", new Date().toString());
        int pid = (int)historyDAO.insertPurchaseHistory(purchaseHistory);

        for (CurrentShoppingListEntry entry : shoppingList) {
            historyDAO.insertItemHistoryCrossRef(new ItemHistoryCrossRef(entry.upc, pid, entry.quantity));
        }

        entryDAO.deleteAllEntries();
        shoppingList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
