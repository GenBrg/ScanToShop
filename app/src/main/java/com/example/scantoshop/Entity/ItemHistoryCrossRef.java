package com.example.scantoshop.Entity;
import androidx.annotation.NonNull;
import androidx.room.*;

/**
 * PurchaseHistory:Item
 * Many to Many Associative Entity
 */
@Entity(primaryKeys = {"upc", "pid"})
public class ItemHistoryCrossRef {
    @NonNull
    public String upc;
    public int pid;
    public int quantity;
}
