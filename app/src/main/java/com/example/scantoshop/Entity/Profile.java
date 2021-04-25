package com.example.scantoshop.Entity;
import androidx.room.*;

/**
 * Profile Entity
 */
@Entity
public class Profile {
    @PrimaryKey
    public String uid;

    @ColumnInfo(name = "user_name")
    public String uname;

    @ColumnInfo(name = "allergy_1")
    public Boolean allergy_1;

    @ColumnInfo(name = "allergy_2")
    public Boolean allergy_2;

    @ColumnInfo(name = "nutrient_1")
    public Float nutrient_1;

    @ColumnInfo(name = "nutrient_2")
    public Float nutrient_2;
}
