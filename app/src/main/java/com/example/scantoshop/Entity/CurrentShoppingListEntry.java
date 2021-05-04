package com.example.scantoshop.Entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class CurrentShoppingListEntry {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    public int entryid;

    @NonNull
    public String uid;
    @NonNull
    public String upc;
    public int quantity;

    public CurrentShoppingListEntry(String uid, String upc, int quantity) {
        this.uid = uid;
        this.upc = upc;
        this.quantity = quantity;
    }
}
