package com.example.scantoshop.ui.shoplist;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.R;

import java.util.ArrayList;
import java.util.List;

public class CurrentShoppingListAdapter extends RecyclerView.Adapter<CurrentShoppingListAdapter.ViewHolder> {
    private List<ShoppingItem> shoppingList = new ArrayList<>();
    private Context context;
    private View itemView;

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageButton upButton, downButton, deleteButton;
        private TextView quantity;
        // TODO Load image
//        private ImageView itemImage;
        private TextView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            upButton = itemView.findViewById(R.id.add_item_button);
            downButton = itemView.findViewById(R.id.remove_item_button);
            deleteButton = itemView.findViewById(R.id.item_delete);
            quantity = itemView.findViewById(R.id.item_quantity);
            // TODO Load image
//            itemImage.findViewById(R.id.item_img);
            itemImage = itemView.findViewById(R.id.favorite_img);
        }
    }

    public CurrentShoppingListAdapter() {
        // TODO Load initial shopping list from database
        shoppingList.add(new ShoppingItem("Apple", 3));
        shoppingList.add(new ShoppingItem("Banana", 1));
        shoppingList.add(new ShoppingItem("Strawberry", 4));
        shoppingList.add(new ShoppingItem("Watermelon", 5));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        itemView = LayoutInflater.from(context).inflate(R.layout.shopping_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ShoppingItem item = shoppingList.get(position);

        holder.upButton.setOnClickListener(v->{
            setQuantity(item, item.getQuantity() + 1);
        });
        holder.downButton.setOnClickListener(v->{
            setQuantity(item, item.getQuantity() - 1);
        });
        holder.deleteButton.setOnClickListener(v->{
            deleteItem(position);
        });

        holder.quantity.setText(String.valueOf(item.getQuantity()));
        holder.itemImage.setText(item.getName());

        holder.itemImage.setOnClickListener(v->{
            ItemDescriptionDialog.showItemDialog(context, item);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
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
        shoppingList.remove(position);
        notifyDataSetChanged();
    }

    private void AddItem(ShoppingItem item) {
        shoppingList.add(item);
        // TODO Commit to Database
        notifyDataSetChanged();
    }

    void commitShoppingList() {
        // TODO Commit to Database

        Toast.makeText(context, "Successfully commit shopping list!", Toast.LENGTH_SHORT).show();
        shoppingList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
