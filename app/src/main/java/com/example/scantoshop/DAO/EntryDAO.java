package com.example.scantoshop.DAO;

import androidx.room.*;

import com.example.scantoshop.Entity.*;

@Dao
public interface EntryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEntries(CurrentShoppingListEntry... entries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertEntry(CurrentShoppingListEntry entry);

    @Update
    public void updateEntries(CurrentShoppingListEntry... entries);

    @Update
    public void updateEntry(CurrentShoppingListEntry entry);

    @Query("SELECT * FROM currentshoppinglistentry WHERE uid == :uid")
    public CurrentShoppingListEntry[] loadEntryByUID(String uid);

    @Delete
    public void deleteEntries(CurrentShoppingListEntry entry);

    @Query("DELETE FROM currentshoppinglistentry")
    public void deleteAllEntries();
}
