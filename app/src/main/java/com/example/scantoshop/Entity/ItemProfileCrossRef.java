package com.example.scantoshop.Entity;
import androidx.annotation.NonNull;
import androidx.room.*;

/**
 * Profile:Item
 * Many to Many Associative Entity
 */
@Entity(primaryKeys = {"upc", "uid"})
public class ItemProfileCrossRef {
    @NonNull
    public String upc;
    @NonNull
    public String uid;


    public ItemProfileCrossRef(String upc, String uid) {
        this.upc = upc;
        this.uid = uid;
    }
}
