package com.example.scantoshop.Entity;
import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return upc.equals(item.upc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(upc);
    }
}
