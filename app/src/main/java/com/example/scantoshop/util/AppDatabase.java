package com.example.scantoshop.util;

import android.content.Context;

import androidx.room.*;
import com.example.scantoshop.DAO.*;
import com.example.scantoshop.Entity.*;

@Database(entities = {Profile.class, Item.class, PurchaseHistory.class,
        ItemHistoryCrossRef.class, ItemProfileCrossRef.class,
        CurrentShoppingListEntry.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;
    public abstract ProfileDAO profileDAO();
    public abstract ItemDAO itemDAO();
    public abstract EntryDAO entryDAO();
    public abstract HistoryDAO historyDAO();
    public abstract ItemProfileCrossRefDAO itemProfileCrossRefDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            AppDatabase.class, "MyDatabase")
                            .createFromAsset("database/scan2shopDB.db")
                            .allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }
}
