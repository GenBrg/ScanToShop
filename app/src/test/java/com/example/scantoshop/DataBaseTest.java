package com.example.scantoshop;

import android.app.Activity;
import androidx.room.Room;
import com.example.scantoshop.DAO.*;
import com.example.scantoshop.Entity.*;
import com.example.scantoshop.util.*;

public class DataBaseTest extends Activity {
    public void dbTest() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "scan_to_shop_database").build();
        ProfileDAO profileDao = db.profileDAO();
        Profile[] profiles = profileDao.loadAllProfiles();
    }
    public static void main(String[] args){
        DataBaseTest dt = new DataBaseTest();
        dt.dbTest();
    }
}
