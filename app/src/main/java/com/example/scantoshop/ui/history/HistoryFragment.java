package com.example.scantoshop.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scantoshop.DAO.ItemDAO;
import com.example.scantoshop.Entity.Item;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.favorite.FavoriteListAdapter;
import com.example.scantoshop.util.AppDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryFragment extends Fragment {

    private RecyclerView itemView;
    private Item[] historyList1;
    private Item[] historyList2;
    private static final int SPAN_COUNT = 1;
    private int uid = 1; // default user

    private HistoryViewModel historyViewModel;
    private Button btn1;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        historyViewModel =
//                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history_new, container, false);
//        btn1 = (Button) root.findViewById(R.id.button1);
//        btn1.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), HistoryDetailActivity.class);
//                startActivity(intent);
//            }
//        });
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "MyDatabase")
                .createFromAsset("database/scan2shopDB.db")
                .allowMainThreadQueries().build();
        ItemDAO itemDAO = db.itemDAO();

        this.historyList1 = itemDAO.loadHistoryByPID(1);
        this.historyList2 = itemDAO.loadHistoryByPID(2);

        List<Item> historyItemsFull1 = new ArrayList<>();
        for (Item item: this.historyList1) {
            String upc = item.upc;
            Item fullItem = itemDAO.loadItemByUPC(upc)[0];
            historyItemsFull1.add(fullItem);
        }

        List<Item> historyItemsFull2 = new ArrayList<>();
        for (Item item: this.historyList2) {
            String upc = item.upc;
            Item fullItem = itemDAO.loadItemByUPC(upc)[0];
            historyItemsFull2.add(fullItem);
        }

        String categories = "";
        Map<String, List<Item>> categorizedItems = new HashMap<>();
        List<Item> fullList = new ArrayList<>();
        for (Item item: historyItemsFull1) {
            categories += item.category + "\n";
            if (!categorizedItems.containsKey(item.category)) {
                categorizedItems.put(item.category, new ArrayList<>());
            }
            categorizedItems.get(item.category).add(item);
        }

        for (Map.Entry<String, List<Item>> entry: categorizedItems.entrySet()) {
            fullList.addAll(entry.getValue());
        }

        final HistoryAdapter currentHisListAdapter = new HistoryAdapter(fullList);

        itemView = root.findViewById(R.id.history_recycle);
        itemView.setAdapter(currentHisListAdapter);
        itemView.setLayoutManager(new GridLayoutManager(requireContext(), 1));

        return root;

    }


}