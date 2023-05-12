package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;

public class Bagel extends Product{

    private List<Filling> fillings;

    public Bagel(String SKU, double price, String variant) {
        super(SKU, price, "Bagel", variant);
        fillings = new ArrayList<>();
    }

    public Bagel(Bagel bagel){
        this(bagel.getSKU(), bagel.getPrice(), bagel.getVariant());
    }

    public List<Filling> getFillings() {
        return fillings;
    }

    public void addFilling(Filling filling) {
        this.fillings.add(filling);
    }
}
