package com.booleanuk.core.model;

import java.math.BigDecimal;

public class Supplement extends Item implements Comparable<Supplement> {
    public Supplement(String sku, double price, String name, String variant) {
        super(sku, price, name, variant);
    }

    @Override
    public int compareTo(Supplement supplement) {
        return this.getSku().compareTo(supplement.getSku());
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
