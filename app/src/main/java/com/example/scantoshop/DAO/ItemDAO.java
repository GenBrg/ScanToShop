package com.example.scantoshop.DAO;
import androidx.room.*;
import com.example.scantoshop.Entity.*;

@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItems(Item... items);

    @Update
    public void updateItems(Item... items);

    @Delete
    public void deleteItems(Item... items);

    @Query("SELECT * FROM item")
    public Item[] loadAllItems();

    @Query("SELECT * FROM item WHERE upc == :upc")
    public Item[] loadItemByUPC(int upc);
}
