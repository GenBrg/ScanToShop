package com.example.scantoshop.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.scantoshop.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
                            StringBuilder nutrients = new StringBuilder("Nutrients:\n");
                            Iterator<String> it = foodHint.getJSONObject("nutrients").keys();
                            while(it.hasNext()){
                                String key = it.next();
                                nutrients.append(key.substring(0, 1)).append(key.substring(1).toLowerCase())
                                        .append(": ")
                                        .append(String.format("%.2f", foodHint.getJSONObject("nutrients").getDouble(key))).append("\n");
                            }
                            ((TextView) v.findViewById(R.id.scannedItemNutrients)).setText(nutrients.toString());
                            Picasso.with(v.getContext()).load(foodHint.getString("image")).into((ImageView)v.findViewById(R.id.scannedItemImage));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        rawResponseTextView.setText("Response is: " + response);
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