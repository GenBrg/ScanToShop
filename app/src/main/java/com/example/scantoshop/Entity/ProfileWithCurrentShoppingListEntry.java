package com.example.scantoshop.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProfileWithCurrentShoppingListEntry {
    @Embedded
    public Profile profile;
    @Relation(
            parentColumn = "uid",
            entityColumn = "uid"
    )
    public List<CurrentShoppingListEntry> currentShoppingList;
}
