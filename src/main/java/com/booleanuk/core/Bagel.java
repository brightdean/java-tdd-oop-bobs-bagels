package com.booleanuk.core;

import com.booleanuk.core.model.Filling;
import com.booleanuk.core.model.Product;

import java.util.ArrayList;
import java.util.List;

public class Bagel extends Product {

    private List<Filling> fillings;

    public Bagel(String SKU, double price, String variant) {
        super(SKU, price, "Bagel", variant);
        fillings = new ArrayList<>();
    }

    public Bagel(Bagel bagel){
        this(bagel.getSKU(), bagel.getPrice(), bagel.getVariant());
    }

    @Override
    public List<Filling> getFillings() {
        return fillings;
    }

    public void setFillings(List<Filling> fillings) {
        this.fillings = fillings;
    }

    public void addFilling(Filling filling) {
        this.fillings.add(filling);
    }

    @Override
    public Bagel getItem() {
        return this;
    }

    @Override
    public double getTotalPrice() {
        double total = 0;
        total += this.getPrice();
        for(Filling filling : this.fillings) total += filling.getPrice();

        return total;
    }

    @Override
    public double getTotalPrice(double price){
        double total = 0;
        total += price;
        for(Filling filling : this.fillings) total += filling.getPrice();

        return total;
    }
}
