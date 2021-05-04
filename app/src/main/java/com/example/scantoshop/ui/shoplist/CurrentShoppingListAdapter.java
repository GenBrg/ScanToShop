package com.example.scantoshop.ui.shoplist;

import android.app.Dialog;
import android.content.Context;
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
import com.example.scantoshop.DAO.ItemDAO;
import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.CurrentShoppingListEntry;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CurrentShoppingListAdapter extends RecyclerView.Adapter<CurrentShoppingListAdapter.ViewHolder> {
    private List<CurrentShoppingListEntry> shoppingList = new ArrayList<>();
    private Context context;
    private View itemView;
    private AppDatabase db;
    private ProfileDAO profileDao;
    private ItemDAO itemDAO;
    private EntryDAO entryDAO;

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
            itemImage.findViewById(R.id.item_img);
        }
    }

    public CurrentShoppingListAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        itemView = LayoutInflater.from(context).inflate(R.layout.shopping_item, parent, false);
        db = Room.databaseBuilder(context,
                AppDatabase.class, "MyDatabase")
                .createFromAsset("database/scan2shopDB.db")
                .allowMainThreadQueries().build();
        profileDao = db.profileDAO();
        itemDAO = db.itemDAO();

        shoppingList = profileDao.getShoppingList().get(0).currentShoppingList;

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CurrentShoppingListEntry entry = shoppingList.get(position);
        Item item = itemDAO.loadItemByUPC(entry.upc)[0];

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
        Picasso.with(context).load(item.image_path).into(holder.itemImage);
        holder.itemImage.setOnClickListener(v->{
//            ItemDescriptionDialog.showItemDialog(context, item);
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    private void setQuantity(CurrentShoppingListEntry entry, int quantity) {
        if (quantity <= 0) {
            return;
        }
        entry.quantity = quantity;
        entryDAO.updateEntry(entry);

        notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        CurrentShoppingListEntry entry = shoppingList.get(position);
        entryDAO.deleteEntries(entry);

        shoppingList.remove(position);
        notifyDataSetChanged();
    }

    void commitShoppingList() {
        // TODO Commit to Database

        Toast.makeText(context, "Successfully commit shopping list!", Toast.LENGTH_SHORT).show();
        shoppingList = new ArrayList<>();
        notifyDataSetChanged();
    }
}
