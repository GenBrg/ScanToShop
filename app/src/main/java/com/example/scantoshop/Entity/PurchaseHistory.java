package com.example.scantoshop.Entity;
import androidx.room.*;
import java.sql.Date;

/**
 * Purchase History Entity
 */
@Entity
public class PurchaseHistory {
    @PrimaryKey
    public int pid;

    @ColumnInfo(name = "user_create_id")
    public String userCreateId;

    @ColumnInfo(name = "purchase_date")
    public String purchase_date;
}
