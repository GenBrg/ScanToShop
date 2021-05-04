package com.example.scantoshop.ui.favorite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.ShoppingItem;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.ViewHolder> {
    private List<Item> favList = new ArrayList<>();
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private Button deleteButton;
        private TextView category;
        // TODO Load image
//        private ImageView itemImage;
        private TextView itemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            deleteButton = itemView.findViewById(R.id.fav_del_button);
            category = itemView.findViewById(R.id.fav_category);
            itemName = itemView.findViewById(R.id.fav_name);
        }
    }

    public FavoriteListAdapter(List<Item> itemList) {
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

        holder.deleteButton.setOnClickListener(v->{
            deleteItem(position);
        });

        holder.category.setText(String.valueOf(item.category));
        holder.itemName.setText(item.iname);
    }

    @Override
    public int getItemCount() {
        return favList.size();
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
        favList.remove(position);
        notifyDataSetChanged();
    }

    private void AddItem(Item item) {
        favList.add(item);
        // TODO Commit to Database
        notifyDataSetChanged();
    }

    void commitShoppingList() {
        // TODO Commit to Database

        Toast.makeText(context, "Successfully commit fav list!", Toast.LENGTH_SHORT).show();
        favList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
