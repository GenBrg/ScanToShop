package com.example.scantoshop.Entity;
import androidx.room.*;

/**
 * Food Item
 */
@Entity
public class Food {
    @PrimaryKey
    public int fid;

    @ColumnInfo(name = "item_upc")
    public int item_upc;

    @ColumnInfo(name = "nutrient")
    public String nutrient;

    @ColumnInfo(name = "health_label")
    public String health_label;
}
