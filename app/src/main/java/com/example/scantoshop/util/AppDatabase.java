package com.example.scantoshop.util;

import androidx.room.*;
import com.example.scantoshop.DAO.*;
import com.example.scantoshop.Entity.*;

@Database(entities = {Profile.class, Food.class, Item.class, PurchaseHistory.class, ItemProfileCrossRef.class, ItemHistoryCrossRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProfileDAO profileDAO();
}
