package com.example.scantoshop.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private List<Item> hisList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button likeButton;
        private TextView category;
        // TODO Load image
//        private ImageView itemImage;
        private TextView itemName;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            likeButton = itemView.findViewById(R.id.add_to_fav_button);
            category = itemView.findViewById(R.id.his_category);
            itemName = itemView.findViewById(R.id.his_name);
        }
    }

    public HistoryAdapter(List<Item> itemList) {
        // TODO Load initial shopping list from database
        for (Item item: itemList) {
            hisList.add(item);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.history_list_new, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item item = hisList.get(position);

        holder.likeButton.setOnClickListener(v->{
            //deleteItem(position);
            addToFavorite(position);
        });

        holder.category.setText(String.valueOf(item.category));
        holder.itemName.setText(item.iname);
    }

    @Override
    public int getItemCount() {
        return hisList.size();
    }

    private void setQuantity(ShoppingItem item, int quantity) {
        if (quantity <= 0) {
            return;
        }
        item.setQuantity(quantity);
        // TODO Commit to database
        notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        // TODO Delete in database
        //favList.remove(position);
        notifyDataSetChanged();
    }

    private void AddItem(Item item) {
        //favList.add(item);
        // TODO Commit to Database
        notifyDataSetChanged();
    }

    private void addToFavorite(int position) {
        //favList.add(item);
        // TODO Commit to Database
        notifyDataSetChanged();
    }

    void commitShoppingList() {
        // TODO Commit to Database

        Toast.makeText(context, "Successfully commit fav list!", Toast.LENGTH_SHORT).show();
        //favList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
