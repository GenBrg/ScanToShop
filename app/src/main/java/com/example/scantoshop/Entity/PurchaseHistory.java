package com.example.scantoshop.Entity;
import androidx.room.*;
import java.util.Date;

/**
 * Purchase History Entity
 */
@Entity
public class PurchaseHistory {
    @PrimaryKey(autoGenerate = true)
    public int pid;

    @ColumnInfo(name = "user_create_id")
    public String userCreateId;

    @ColumnInfo(name = "purchase_date")
    public String purchase_date;

    public PurchaseHistory(String userCreateId, String purchase_date) {
        this.userCreateId = userCreateId;
        this.purchase_date = purchase_date;
    }
}
