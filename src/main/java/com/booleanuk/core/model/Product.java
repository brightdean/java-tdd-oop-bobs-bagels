package com.booleanuk.core.model;

import java.util.ArrayList;
import java.util.List;

public class Product extends Item {
    private List<Supplement> supplements;

    public Product(String sku, double price, String name, String variant) {
        super(sku, price, name, variant);
        supplements = new ArrayList<>();
    }

    public Product(Product product) {
        this(product.getSku(), product.getPrice(), product.getName(), product.getVariant());
    }

    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
    }

    public List<Supplement> getSupplements() {
        return supplements;
    }
}
