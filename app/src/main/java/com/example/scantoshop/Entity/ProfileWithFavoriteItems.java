package com.example.scantoshop.Entity;
import androidx.room.*;
import java.util.List;

/**
 * Profile:Item
 * Many to Many:
 *      Associative Entity: ItemProfileCrossRef
 *      Query a Profile and a list of its corresponding favorite Item objects
 */
public class ProfileWithFavoriteItems {
    @Embedded public Profile profile;
    @Relation(
            parentColumn = "uid",
            entityColumn = "upc",
            associateBy = @Junction(ItemProfileCrossRef.class)
    )
    public List<Item> favorite_items;
}
