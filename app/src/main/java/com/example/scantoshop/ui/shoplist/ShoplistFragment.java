package com.example.scantoshop.ui.shoplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.R;

import java.util.zip.Inflater;

public class ShoplistFragment extends Fragment {
    private ShoplistViewModel shoplistViewModel;
    private RecyclerView itemView;
    private Button submitButton;
    private static final int SPAN_COUNT = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shoplist, container, false);
        final CurrentShoppingListAdapter currentShoppingListAdapter = new CurrentShoppingListAdapter();

        itemView = root.findViewById(R.id.shop_list);
        itemView.setAdapter(currentShoppingListAdapter);
        itemView.setLayoutManager(new GridLayoutManager(requireContext(), SPAN_COUNT));

        submitButton = root.findViewById(R.id.submit_shoppinglist_button);

        submitButton.setOnClickListener(v->{
            currentShoppingListAdapter.commitShoppingList();
        });

        return root;
    }


}
