package com.booleanuk.core;

import java.util.UUID;

public abstract class Item {
    private final String id;
    private final String SKU;
    private double price;
    private String name;
    private String variant;

    public Item(String SKU, double price, String name, String variant) {
        this.id = UUID.randomUUID().toString();
        this.SKU = SKU;
        this.price = price;
        this.name = name;
        this.variant = variant;
    }

    public String getId() {
        return id;
    }

    public String getSKU() {
        return SKU;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public abstract <E extends Item> E getItem();
}
