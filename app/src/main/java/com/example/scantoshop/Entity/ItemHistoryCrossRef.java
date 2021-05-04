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

    public ItemHistoryCrossRef(@NonNull String upc, int pid, int quantity) {
        this.upc = upc;
        this.pid = pid;
        this.quantity = quantity;
    }
}
