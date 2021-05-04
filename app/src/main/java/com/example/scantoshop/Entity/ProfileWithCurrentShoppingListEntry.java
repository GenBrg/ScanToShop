package com.example.scantoshop.Entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ProfileWithCurrentShoppingListEntry {
    @Embedded
    public Profile profile;
    @Relation(
            parentColumn = "uid",
            entityColumn = "entryid"
    )
    public List<CurrentShoppingListEntry> currentShoppingList;
}
