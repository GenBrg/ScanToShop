package com.example.scantoshop.barcodescanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scantoshop.DAO.EntryDAO;
import com.example.scantoshop.DAO.ItemDAO;
import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.CurrentShoppingListEntry;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.Entity.Profile;
import com.example.scantoshop.Entity.ProfileWithCurrentShoppingListEntry;
import com.example.scantoshop.MainActivity;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ItemListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ItemDialogFragment extends BottomSheetDialogFragment {
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_layout,
                container, false);

        TextView rawResponseTextView = (TextView) v.findViewById(R.id.rawResponse);
        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String barcodeValue = getArguments().getString("barcodeRawValue");
        String url = "https://api.edamam.com/api/food-database/v2/parser?upc=" + barcodeValue
                + "&app_id=d9198ac0&app_key=58c31511882a25d2a5bd1398e1c0b117";
        Button cancelButton = v.findViewById(R.id.cancelScanButton);
        cancelButton.setOnClickListener(
                view -> {
                    dismiss();
                });

        AppDatabase db = AppDatabase.getInstance(getContext());
        ProfileDAO profileDao = db.profileDAO();
        Profile user = profileDao.loadAllProfiles()[0];
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject parser = new JSONObject(response);
                            JSONObject foodHint = parser.getJSONArray("hints").getJSONObject(0).getJSONObject("food");
                            String itemName = foodHint.getString("label");
                            ((TextView) v.findViewById(R.id.scannedItemName)).setText(itemName);
                            String categoryString = foodHint.getString("category");
                            ((TextView) v.findViewById(R.id.scannedItemCategory)).setText(Html.fromHtml("<b>Category: </b>" + categoryString));
                            String contentsString = "<b>Contents: </b>" + foodHint.getString("foodContentsLabel").toLowerCase();
                            ((TextView) v.findViewById(R.id.scannedItemFoddContentsLabel)).setText(Html.fromHtml(contentsString));
                            String warningString = "";
                            StringBuilder allergens = new StringBuilder("");
                            if (user.allergy_1) {
                                if (contentsString.contains("milk")) {
                                    allergens.append("milk, ");
                                }
                            }
                            if (user.allergy_2) {
                                if (contentsString.contains("egg")) {
                                    allergens.append("egg, ");
                                }
                            }
                            String allergenString = "";
                            if (allergens.length() != 0) {
                                allergenString = "Allergens detected: " + allergens.toString().substring(0, allergens.length() - 2) + "<br>";
                            }
                            StringBuilder nutrientsWarning = new StringBuilder("");
                            StringBuilder nutrients = new StringBuilder("<b>Nutrients: </b>");
                            Iterator<String> it = foodHint.getJSONObject("nutrients").keys();
                            while (it.hasNext()) {
                                String key = it.next();
                                double value = foodHint.getJSONObject("nutrients").getDouble(key);
                                if (key.toLowerCase().equals("fat") && value > user.nutrient_1) {
                                    nutrientsWarning.append("fat: " + String.format("%.2f", value) + ", ");
                                }
                                if (key.toLowerCase().equals("sugar") && value > user.nutrient_2) {
                                    nutrientsWarning.append("sugar: " + String.format("%.2f", value) + ", ");
                                }
                                nutrients.append(key.substring(0, 1)).append(key.substring(1).toLowerCase())
                                        .append(": ")
                                        .append(String.format("%.2f", value)).append("; ");
                            }

                            ((TextView) v.findViewById(R.id.scannedItemNutrients)).setText(Html.fromHtml(nutrients.toString()));
                            String nutrientsWarningString = "";
                            if (nutrientsWarning.length() != 0) {
                                nutrientsWarningString = "Nutrients exceeds threshold: " + nutrientsWarning.toString().substring(0, nutrientsWarning.length() - 2);
                            }
                            if (nutrientsWarningString.length() + allergenString.length() > 0) {
                                warningString = "<b>WARNING:</b><br>" + allergenString + nutrientsWarningString;
                            }
                            ((TextView) v.findViewById(R.id.scannedItemWarnings)).setText(Html.fromHtml(warningString));
                            ((TextView) v.findViewById(R.id.scannedItemWarnings)).setTextColor(Color.RED);
                            String imageURL = "https://www.goodinfonet.com/images/no-pimage.jpg";
                            if(foodHint.has("image")){
                                imageURL = foodHint.getString("image");
                            }
                            Picasso.with(v.getContext()).load(imageURL).into((ImageView) v.findViewById(R.id.scannedItemImage));
                            Button addButton = v.findViewById(R.id.addToShopListButton);
                            String finalImageURL = imageURL;
                            addButton.setOnClickListener(
                                    v -> {
                                        ItemDAO itemDAO = db.itemDAO();
                                        Item item;
                                        boolean existsItem = false;
                                        for(Item i:itemDAO.loadAllItems()){
                                            if(i.upc.equals(barcodeValue)){
                                                item = i;
                                                existsItem = true;
                                                break;
                                            }
                                        }
                                        if(!existsItem){
                                            item = new Item();
                                            item.upc = barcodeValue;
                                            item.iname = itemName;
                                            item.image_path = finalImageURL;
                                            item.nutrient = nutrients.toString();
                                            item.health_label = contentsString;
                                            item.category = categoryString;
                                            itemDAO.insertItems(item);
                                        }
                                        boolean itemInList = false;
                                        EntryDAO entryDAO = db.entryDAO();

                                        for(CurrentShoppingListEntry entry: profileDao.getShoppingList().get(0).currentShoppingList){
                                            if(entry.upc.equals(barcodeValue)){
                                                entry.quantity++;
                                                entryDAO.insertEntry(entry);
                                                itemInList = true;
                                                break;
                                            }
                                        }
                                        if (!itemInList){
                                            CurrentShoppingListEntry entry = new CurrentShoppingListEntry("1", barcodeValue, 1);
                                            entryDAO.insertEntry(entry);
                                        }
                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        startActivity(intent);
                                    });
                            addButton.setVisibility(View.VISIBLE);
                            rawResponseTextView.setText("");

                        } catch (JSONException e) {
                            rawResponseTextView.setText("Unable to interpret API response.");
                            e.printStackTrace();
                        }
//                        rawResponseTextView.setText("Response is: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rawResponseTextView.setText("Sorry, no product information is available for this item.");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return v;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((LivePreviewActivity) getActivity()).restartCamera();
        ((LivePreviewActivity) getActivity()).resetDetectedBarcodeStatus();
    }
}