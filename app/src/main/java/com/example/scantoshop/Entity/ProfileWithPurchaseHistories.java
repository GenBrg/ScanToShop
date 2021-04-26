package com.example.scantoshop.Entity;
import androidx.room.*;
import java.util.List;

/**
 * Profile:PurchaseHistory
 * One to Many:
 *      parentColumn set to the name of the primary key column of the parent entity
 *      entityColumn set to the name of the column of the child entity that references
 *      the parent entity's primary key.
 */
public class ProfileWithPurchaseHistories {
    @Embedded public Profile profile;
    @Relation(
            parentColumn = "uid",
            entityColumn = "user_create_id"
    )
    public List<PurchaseHistory> purchaseHistoryList;
}
