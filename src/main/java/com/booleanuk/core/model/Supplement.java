package com.booleanuk.core.model;

public class Supplement extends Item {
    public Supplement(String sku, double price, String name, String variant) {
        super(sku, price, name, variant);
    }

    public Supplement(Supplement supplement) {
        this(supplement.getSku(), supplement.getPrice(), supplement.getName(), supplement.getVariant());
    }
}
