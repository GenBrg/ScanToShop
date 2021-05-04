package com.example.scantoshop.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.Profile;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

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

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject parser = new JSONObject(response);
                            JSONObject foodHint = parser.getJSONArray("hints").getJSONObject(0).getJSONObject("food");
                            ((TextView) v.findViewById(R.id.scannedItemName)).setText(foodHint.getString("label"));
                            ((TextView) v.findViewById(R.id.scannedItemCategory)).setText("Category: " + foodHint.getString("category"));
                            String contentsString = "Contents: " + foodHint.getString("foodContentsLabel").toLowerCase();
                            ((TextView) v.findViewById(R.id.scannedItemFoddContentsLabel)).setText(contentsString);
                            AppDatabase db = Room.databaseBuilder(getContext(),
                                    AppDatabase.class, "MyDatabase")
                                    .createFromAsset("database/scan2shopDB.db")
                                    .allowMainThreadQueries().build();
                            ProfileDAO profileDao = db.profileDAO();
                            Profile user = profileDao.loadAllProfiles()[0];
                            String warningString = "";
                            StringBuilder allergens = new StringBuilder("");
                            if(user.allergy_1){
                                if(contentsString.contains("milk")){
                                    allergens.append("milk, ");
                                }
                            }
                            if(user.allergy_2){
                                if(contentsString.contains("egg")){
                                    allergens.append("egg, ");
                                }
                            }
                            String allergenString = "";
                            if(allergens.length() != 0){
                                allergenString = "Allergens detected: " + allergens.toString().substring(0, allergens.length() - 2) + "\n";
                            }
                            StringBuilder nutrientsWarning = new StringBuilder("");
                            StringBuilder nutrients = new StringBuilder("Nutrients:\n");
                            Iterator<String> it = foodHint.getJSONObject("nutrients").keys();
                            while(it.hasNext()){
                                String key = it.next();
                                double value = foodHint.getJSONObject("nutrients").getDouble(key);
                                if(key.toLowerCase().equals("fat") && value > user.nutrient_1){
                                    nutrientsWarning.append("fat: " + String.format("%.2f", value) + ", ");
                                }
                                if(key.toLowerCase().equals("sugar") && value > user.nutrient_2){
                                    nutrientsWarning.append("sugar: " + String.format("%.2f", value) + ", ");
                                }
                                nutrients.append(key.substring(0, 1)).append(key.substring(1).toLowerCase())
                                        .append(": ")
                                        .append(String.format("%.2f", value)).append("; ");
                            }

                            ((TextView) v.findViewById(R.id.scannedItemNutrients)).setText(nutrients.toString());
                            String nutrientsWarningString = "";
                            if(nutrientsWarning.length() != 0){
                                nutrientsWarningString = "Nutrients exceeds threshold: " + nutrientsWarning.toString().substring(0, nutrientsWarning.length() - 2);
                            }
                            if(nutrientsWarningString.length() + allergenString.length() > 0){
                                warningString = "Warnings:\n" + allergenString+ nutrientsWarningString;
                            }
                            ((TextView) v.findViewById(R.id.scannedItemWarnings)).setText(warningString);
                            ((TextView) v.findViewById(R.id.scannedItemWarnings)).setTextColor(Color.RED);
                            Picasso.with(v.getContext()).load(foodHint.getString("image")).into((ImageView)v.findViewById(R.id.scannedItemImage));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                        rawResponseTextView.setText("Response is: " + response);
                        rawResponseTextView.setText("");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rawResponseTextView.setText("Response error");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return v;

    }
    @Override
    public void onDetach() {
        super.onDetach();
        ((LivePreviewActivity)getActivity()).restartCamera();
    }
}