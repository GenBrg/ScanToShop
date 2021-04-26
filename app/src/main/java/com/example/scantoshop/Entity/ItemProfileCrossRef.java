package com.example.scantoshop.Entity;
import androidx.room.*;

import org.jetbrains.annotations.NotNull;

/**
 * Profile:Item
 * Many to Many Associative Entity
 */
@Entity(primaryKeys = {"upc", "uid"})
public class ItemProfileCrossRef {
    @NotNull
    public int upc;
    @NotNull
    public String uid;
}
