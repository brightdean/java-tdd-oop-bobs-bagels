package com.booleanuk.core;

public abstract class Product extends Item {
    public Product(String SKU, double price, String name, String variant) {
        super(SKU, price, name, variant);
    }
}
