package com.example.scantoshop.DAO;
import androidx.room.*;
import com.example.scantoshop.Entity.*;

@Dao
public interface ProfileDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertProfiles(Profile... profiles);

    @Update
    public void updateProfiles(Profile... profiles);

    @Delete
    public void deleteProfiles(Profile... profiles);

    @Query("SELECT * FROM profile")
    public Profile[] loadAllProfiles();

    @Query("SELECT * FROM profile WHERE uid == :user_name")
    public Profile[] loadProfileByName(String user_name);
}