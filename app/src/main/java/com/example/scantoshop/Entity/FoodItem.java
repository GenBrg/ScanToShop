package com.example.scantoshop.Entity;
import androidx.room.*;

/**
 * Item:Food
 * One to One:
 *      parentColumn set to the name of the primary key column of the parent entity
 *      entityColumn set to the name of the column of the child entity that references
 *      the parent entity's primary key.
 */
@Entity
public class FoodItem {
    @Embedded public Item itm;
    @Relation(
            parentColumn = "upc",
            entityColumn = "item_upc"
    )
    public Food food;
}
