package com.example.scantoshop.Entity;
import androidx.room.*;
import java.util.List;

/**
 * PurchaseHistory:Item
 * Many to Many:
 *      Associative Entity: ItemHistoryCrossRef
 *      Query a PurchaseHistory and a list of its corresponding included Item objects
 */
public class PurchaseHistoryWithItems {
    @Embedded public PurchaseHistory purchaseHistory;
    @Relation(
            parentColumn = "pid",
            entityColumn = "upc",
            associateBy = @Junction(ItemHistoryCrossRef.class)
    )
    public List<Item> itemList;
}
