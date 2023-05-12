package com.booleanuk.core;

public class Coffee extends Product{
    public Coffee(String SKU, double price, String variant) {
        super(SKU, price, "Coffee", variant);
    }

    public Coffee(Coffee coffee) {
        this(coffee.getSKU(), coffee.getPrice(), coffee.getVariant());
    }
}
