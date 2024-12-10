package com.android.rproject.data.models;

public class Items {
    private String id;
    private String itemName;
    private String itemDescription;
    private Double itemPrice;

    public Items(String id, String itemName, String itemDescription, Double itemPrice) {
        this.id = id;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public Double getItemPrice() {
        return itemPrice;
    }
}
