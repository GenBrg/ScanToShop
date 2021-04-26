package com.example.scantoshop.Entity;
import androidx.room.*;

/**
 * Profile:Item
 * Many to Many Associative Entity
 */
@Entity(primaryKeys = {"upc", "uid"})
public class ItemProfileCrossRef {
    public int upc;
    public String uid;
}
