package com.example.scantoshop.DAO;

import androidx.room.*;

import com.example.scantoshop.Entity.*;

@Dao
public interface EntryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEntries(CurrentShoppingListEntry... entries);

    @Update
    public void updateEntries(CurrentShoppingListEntry... entries);

    @Delete
    public void deleteEntries(CurrentShoppingListEntry entry);

    @Query("DELETE FROM currentshoppinglistentry")
    public void deleteAllEntries();
}
