package com.example.scantoshop.Entity;
import androidx.annotation.NonNull;
import androidx.room.*;

import javax.annotation.Nonnull;

/**
 * Item Entity
 */
@Entity
public class Item {
    @PrimaryKey
    @NonNull
    public String upc;

    @ColumnInfo(name = "item_name")
    public String iname;

    @ColumnInfo(name = "item_image")
    public String image_path;

    @ColumnInfo(name = "nutrient")
    public String nutrient;

    @ColumnInfo(name = "health_label")
    public String health_label;

    @ColumnInfo(name = "category")
    public String category;
}
