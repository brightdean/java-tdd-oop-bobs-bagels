package com.booleanuk.core.model;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Item {
    private final String id;
    private String sku;
    private BigDecimal price;
    private String name;
    private String variant;

    public Item(String sku, double price, String name, String variant) {
        this.id = UUID.randomUUID().toString();
        this.sku = sku;
        this.price = new BigDecimal(Double.toString(price));
        this.name = name;
        this.variant = variant;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getVariant() {
        return variant;
    }
}
