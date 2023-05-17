package com.booleanuk.core.model;

public class ItemOffer {

    private String sku;
    private int quantity;
    private double price;

    private String name;

    public ItemOffer(String name, String sku, int quantity, double price) {
        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
