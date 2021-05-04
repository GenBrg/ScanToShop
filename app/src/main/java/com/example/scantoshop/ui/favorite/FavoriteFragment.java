package com.example.scantoshop.ui.favorite;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scantoshop.DAO.ItemDAO;
import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.Entity.Profile;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.shoplist.CurrentShoppingListAdapter;
import com.example.scantoshop.util.AppDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.LinearLayout.VERTICAL;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel favoriteViewModel;
    private RecyclerView itemView;
    private Item[] favoriteItems;
    private static final int SPAN_COUNT = 3;
    private int uid = 1; // default user


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "MyDatabase")
                .createFromAsset("database/scan2shopDB.db")
                .allowMainThreadQueries().build();
        ItemDAO itemDAO = db.itemDAO();
        this.favoriteItems = itemDAO.loadItemByUID(1);
        Log.i("ITEMS", ""+this.favoriteItems.length);

        List<Item> favoriteItemsFull = new ArrayList<>();
        for (Item item: this.favoriteItems) {
            String upc = item.upc;
            Item fullItem = itemDAO.loadItemByUPC(upc)[0];
            favoriteItemsFull.add(fullItem);
        }

        String categories = "";
        Map<String, List<Item>> categorizedItems = new HashMap<>();
        List<Item> fullList = new ArrayList<>();
        for (Item item: favoriteItemsFull) {
            Log.i("CATEGORY", ""+item.category);
            categories += item.category + "\n";
            if (!categorizedItems.containsKey(item.category)) {
                categorizedItems.put(item.category, new ArrayList<>());
            }
            categorizedItems.get(item.category).add(item);
        }
        Log.i("myTag", categories);
        TextView textView = (TextView)root.findViewById(R.id.textView8);
        textView.setText(categories);

        for (Map.Entry<String, List<Item>> entry: categorizedItems.entrySet()) {
            fullList.addAll(entry.getValue());
        }

        final FavoriteListAdapter currentfavListAdapter = new FavoriteListAdapter(fullList);

        itemView = root.findViewById(R.id.fav_recycle);
        itemView.setAdapter(currentfavListAdapter);
        itemView.setLayoutManager(new GridLayoutManager(requireContext(), SPAN_COUNT));

        //submitButton = root.findViewById(R.id.submit_shoppinglist_button);

        //submitButton.setOnClickListener(v->{
        //    currentShoppingListAdapter.commitShoppingList();
        //});

        return root;
    }
}