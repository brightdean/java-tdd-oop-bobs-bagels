package com.booleanuk.core.model;

import java.util.UUID;

public abstract class Item {
    private String id;
    private String sku;
    private double price;
    private String name;
    private String variant;

    public Item(String sku, double price, String name, String variant) {
        this.id = UUID.randomUUID().toString();
        this.sku = sku;
        this.price = price;
        this.name = name;
        this.variant = variant;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getVariant() {
        return variant;
    }
}
