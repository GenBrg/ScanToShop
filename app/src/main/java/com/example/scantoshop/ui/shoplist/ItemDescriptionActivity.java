package com.example.scantoshop.ui.shoplist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scantoshop.DAO.ItemDAO;
import com.example.scantoshop.DAO.ItemProfileCrossRefDAO;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.Entity.ItemProfileCrossRef;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemDescriptionActivity extends AppCompatActivity {
    private Button favButton;
    private TextView itemName, itemDescription;
    private ImageView itemImage;
    private boolean isFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_description);

        Bundle b = getIntent().getExtras();
        final String upc = b.getString("upc");

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        ItemDAO itemDAO = db.itemDAO();
        ItemProfileCrossRefDAO itemProfileCrossRefDAO =db.itemProfileCrossRefDAO();

        List<Item> favoriteItems = Arrays.asList(itemDAO.loadItemByUID(1));
        Item item = itemDAO.loadItemByUPC(upc)[0];
        Log.e("123", favoriteItems.toString());
        isFav = favoriteItems.contains(item);

        favButton = findViewById(R.id.fav_button);
        itemName = findViewById(R.id.item_name);
        itemDescription = findViewById(R.id.item_description);
        itemImage = findViewById(R.id.item_description_image);

        if (isFav) {
            favButton.setText("Unfavorite");
        } else {
            favButton.setText("Favorite");
        }

        Picasso.with(getApplicationContext()).load(item.image_path).placeholder(R.drawable.no_image_available).into(itemImage);
        itemName.setText(item.iname);

        favButton.setOnClickListener(v -> {
            if (isFav) {
                itemProfileCrossRefDAO.deleteItemProfileCrossRef(new ItemProfileCrossRef(upc, "1"));
                favButton.setText("Favorite");
            } else {
                itemProfileCrossRefDAO.insertItemProfileCrossRef(new ItemProfileCrossRef(upc, "1"));
                favButton.setText("Unfavorite");
            }
            isFav = !isFav;
        });
    }
}