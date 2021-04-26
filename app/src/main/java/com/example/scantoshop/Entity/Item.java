package com.example.scantoshop.Entity;
import androidx.room.*;
import java.nio.file.Path;

/**
 * Item Entity
 */
@Entity
public class Item {
    @PrimaryKey
    public int upc;

    @ColumnInfo(name = "item_name")
    public String iname;

    @ColumnInfo(name = "item_image")
    public String image_path;
}
