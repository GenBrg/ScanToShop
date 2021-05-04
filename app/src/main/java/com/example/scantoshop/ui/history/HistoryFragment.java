package com.example.scantoshop.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;

public class HistoryFragment extends Fragment {
    private RecyclerView historyView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        historyView = root.findViewById(R.id.history_list);
        HistoryShoppingListAdapter adapter = new HistoryShoppingListAdapter();
        historyView.setAdapter(adapter);
        historyView.setLayoutManager(new LinearLayoutManager(requireContext()));

        AppDatabase db = AppDatabase.getInstance(requireContext());

        adapter.setShoppingHistory(db.historyDAO().loadAllPurchaseHistory());

        return root;
    }


}