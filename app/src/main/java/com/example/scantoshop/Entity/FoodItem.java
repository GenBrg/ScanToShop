package com.example.scantoshop.Entity;
import androidx.room.*;

import java.util.List;

/**
 * Item:Food
 * One to One:
 *      parentColumn set to the name of the primary key column of the parent entity
 *      entityColumn set to the name of the column of the child entity that references
 *      the parent entity's primary key.
 */
public class FoodItem {
    @Embedded
    public Item itm;
    @Relation(
            parentColumn = "upc",
            entityColumn = "item_upc"
    )
    public Food food;
}
