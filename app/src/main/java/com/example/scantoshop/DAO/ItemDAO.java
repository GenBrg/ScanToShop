package com.example.scantoshop.DAO;
import androidx.room.*;
import com.example.scantoshop.Entity.*;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItems(Item... items);

    @Insert
    public void insertItem(Item item);

    @Update
    public void updateItems(Item... items);

    @Delete
    public void deleteItem(Item item);

    @Query("DELETE FROM item")
    public void deleteAllItems();

    @Query("SELECT * FROM item")
    public Item[] loadAllItems();

    @Query("SELECT * FROM item WHERE upc == :upc")
    public Item[] loadItemByUPC(String upc);

    @Query("SELECT * FROM itemprofilecrossref WHERE uid == :uid")
    public Item[] loadItemByUID(int uid);

    @Query("SELECT * FROM itemhistorycrossref WHERE pid == :pid")
    public Item[] loadHistoryByPID(int pid);

    @Query("SELECT purchase_date FROM purchasehistory WHERE pid == :pid")
    public String loadDateByPID(int pid);
}
