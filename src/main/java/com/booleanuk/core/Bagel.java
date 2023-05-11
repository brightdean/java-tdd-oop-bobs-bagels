package com.booleanuk.core;

public class Bagel extends Product{
    public Bagel(String SKU, double price, String name, String variant) {
        super(SKU, price, name, variant);
    }

    public Bagel(Bagel bagel){
        super(bagel.getSKU(), bagel.getPrice(), bagel.getName(), bagel.getVariant());
    }
}
