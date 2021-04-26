package com.example.scantoshop.Entity;
import androidx.room.*;

/**
 * PurchaseHistory:Item
 * Many to Many Associative Entity
 */
@Entity(primaryKeys = {"upc", "pid"})
public class ItemHistoryCrossRef {
    public int upc;
    public int pid;
    public int quantity;
}
