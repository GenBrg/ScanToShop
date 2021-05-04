package com.example.scantoshop.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.scantoshop.Entity.ItemHistoryCrossRef;
import com.example.scantoshop.Entity.PurchaseHistory;

import java.util.List;

@Dao
public interface HistoryDAO {
    @Insert
    public long insertPurchaseHistory(PurchaseHistory purchaseHistory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItemHistoryCrossRef(ItemHistoryCrossRef itemHistoryCrossRef);

    @Query("SELECT * FROM purchasehistory ORDER BY pid DESC")
    List<PurchaseHistory> loadAllPurchaseHistory();

    @Query("SELECT * FROM itemhistorycrossref WHERE pid == :pid")
    public List<ItemHistoryCrossRef> loadItemHistoryCrossRefPID(int pid);
}
