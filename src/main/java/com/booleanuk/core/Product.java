package com.booleanuk.core;

import java.util.List;

public abstract class Product extends Item {
    private boolean underDiscount;
    public Product(String SKU, double price, String name, String variant) {
        super(SKU, price, name, variant);
        this.underDiscount = false;
    }
    public boolean isUnderDiscount() {
        return underDiscount;
    }

    public void setUnderDiscount(boolean underDiscount) {
        this.underDiscount = underDiscount;
    }

    public abstract double getTotalPrice();
    public abstract double getTotalPrice(double price);

    public abstract List<Filling> getFillings();
}
