package com.example.scantoshop.ui.shoplist;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scantoshop.R;

public class ItemDescriptionDialog {
    public static void showItemDialog(Context context, ShoppingItem item) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_description);
        dialog.setCancelable(false);

        View view = LayoutInflater.from(context).inflate(R.layout.item_description, null);
        Button favButton = view.findViewById(R.id.fav_button);
        Button backButton = view.findViewById(R.id.back_button);
        TextView itemName = view.findViewById(R.id.item_name);
        TextView itemDescription = view.findViewById(R.id.item_description);
        ImageView itemDescriptionImage = view.findViewById(R.id.item_description_image);

        itemName.setText(item.getName());
        itemDescription.setText(item.getName());

        favButton.setOnClickListener(v -> {
            boolean isFav = favButton.getText().equals("Favorite");
            if (isFav) {
                Toast.makeText(context, "Unfavorite", Toast.LENGTH_SHORT).show();
                // TODO database
                favButton.setText("Unfavorite");
            } else {
                Toast.makeText(context, "Favorite", Toast.LENGTH_SHORT).show();
                // TODO database
                favButton.setText("Favorite");
            }
        });

        backButton.setOnClickListener(v->{
            Toast.makeText(context, "back", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
