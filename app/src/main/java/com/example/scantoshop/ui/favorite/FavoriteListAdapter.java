package com.example.scantoshop.ui.favorite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.DAO.EntryDAO;
import com.example.scantoshop.Entity.CurrentShoppingListEntry;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.ItemDescriptionActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {
    private List<Item> favList = new ArrayList<>();
    private Context context;
    private EntryDAO entryDAO;
    private AlertDialog.Builder adbuilder;
    private String uid = "1";

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button deleteButton;
        private Button insertButton;
        private ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            insertButton = itemView.findViewById(R.id.fav_insert_button);
            itemImage = itemView.findViewById(R.id.fav_imageView);
        }
    }

    public FavoriteListAdapter(List<Item> itemList, EntryDAO entryDAO) {
        this.entryDAO = entryDAO;
        // TODO Load initial shopping list from database
        for (Item item: itemList) {
            favList.add(item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.favorite_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item item = favList.get(position);

        holder.insertButton.setOnClickListener(v -> {
            insertToShoppingList(position);
        });
        Picasso.with(context).load(item.image_path).placeholder(R.drawable.no_image_available).into(holder.itemImage);
        holder.itemImage.setOnClickListener(v->{
            Intent intent = new Intent(context, ItemDescriptionActivity.class);
            Bundle b = new Bundle();
            b.putString("upc", item.upc);
            intent.putExtras(b);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public void insertToShoppingList(int position) {
        Item selectedItem = favList.get(position);

        // test add new CurrentShoppingListEntry
        CurrentShoppingListEntry[] currentEntries = entryDAO.loadEntryByUID(uid);
        boolean found = false;
        CurrentShoppingListEntry matched = null;
        int count = 1;
        for (CurrentShoppingListEntry et: currentEntries) {
            if (et.upc.equals(selectedItem.upc)) {
                found = true;
                matched = et;
                matched.quantity += 1;
                count = matched.quantity;
                entryDAO.updateEntry(matched);
                break;
            }
        }

        if (!found) {
            CurrentShoppingListEntry newEntry = new CurrentShoppingListEntry(uid, selectedItem.upc, 1);
            entryDAO.insertEntry(newEntry);
        }

        adbuilder = new AlertDialog.Builder(context);
        adbuilder.setMessage("Item added to shopping list, now " + count + " " + selectedItem.iname)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = adbuilder.create();
        //Setting the title manually
        alert.setTitle("Success");
        alert.show();
        Log.i("DATABASE", "insert entry in adapter");
    }
}
