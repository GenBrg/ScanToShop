package com.example.scantoshop.ui.history;

import com.example.scantoshop.ui.shoplist.ShoppingItem;

import java.util.Date;
import java.util.List;

public class ShoppingHistory {
    private List<ShoppingItem> shoppingItems;
    private Date date;

    public ShoppingHistory(List<ShoppingItem> shoppingItems, Date date) {
        this.shoppingItems = shoppingItems;
        this.date = date;
    }

    public List<ShoppingItem> getShoppingItems() {
        return shoppingItems;
    }

    public void setShoppingItems(List<ShoppingItem> shoppingItems) {
        this.shoppingItems = shoppingItems;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int size() {
        return shoppingItems.size();
    }

    @Override
    public String toString() {
        return "ShoppingHistory{" +
                "shoppingItems=" + shoppingItems +
                ", date=" + date +
                '}';
    }
}
