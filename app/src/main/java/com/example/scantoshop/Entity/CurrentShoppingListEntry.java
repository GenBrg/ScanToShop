package com.example.scantoshop.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"entryid", "uid", "upc"})
public class CurrentShoppingListEntry {
    @NonNull
    public int entryid;
    @NonNull
    public String uid;
    @NonNull
    public String upc;
    public int quantity;
}
