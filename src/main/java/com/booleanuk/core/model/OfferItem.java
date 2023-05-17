package com.booleanuk.core.model;

public class OfferItem {

    private String name;
    private String sku;
    private int quantity;
    private double price;
    private int priority;

    public OfferItem(String name, String sku, int quantity, double price, int priority) {
        this.name = name;
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
        this.priority = priority;
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

    public int getPriority() { return priority;}
}
