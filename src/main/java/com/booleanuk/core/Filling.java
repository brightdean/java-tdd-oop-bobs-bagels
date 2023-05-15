package com.booleanuk.core;

public class Filling extends Item{
    public Filling(String SKU, double price, String variant) {
        super(SKU, price, "Filling", variant);
    }

    public Filling(Filling filling) {
        this(filling.getSKU(), filling.getPrice(), filling.getVariant());
    }

    @Override
    public Filling getItem() {
        return this;
    }
}
