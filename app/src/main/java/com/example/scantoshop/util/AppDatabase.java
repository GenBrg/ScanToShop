package com.example.scantoshop.util;

import androidx.room.*;
import com.example.scantoshop.DAO.*;
import com.example.scantoshop.Entity.*;

@Database(entities = {Profile.class, Item.class, PurchaseHistory.class,
        ItemHistoryCrossRef.class, ItemProfileCrossRef.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProfileDAO profileDAO();
    public abstract ItemDAO itemDAO();
}
