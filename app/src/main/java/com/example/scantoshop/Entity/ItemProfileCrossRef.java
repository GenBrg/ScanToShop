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
    public int upc;
    @NonNull
    public String uid;
}
