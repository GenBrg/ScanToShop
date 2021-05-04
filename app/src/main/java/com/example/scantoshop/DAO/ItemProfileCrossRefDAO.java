package com.example.scantoshop.DAO;

import androidx.room.*;

import com.example.scantoshop.Entity.*;

@Dao
public interface ItemProfileCrossRefDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertItemProfileCrossRefs(ItemProfileCrossRef... ipCrosses);

    @Insert
    public void insertItemProfileCrossRef(ItemProfileCrossRef ipCross);

    @Update
    public void updateItemProfileCrossRefs(ItemProfileCrossRef... ipCrosses);

    @Delete
    public void deleteItemProfileCrossRef(ItemProfileCrossRef ipCrosses);
}
