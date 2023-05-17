package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;

public class Coffee extends Product{
    public Coffee(String SKU, double price, String variant) {
        super(SKU, price, "Coffee", variant);
    }

    public Coffee(Coffee coffee) {
        this(coffee.getSKU(), coffee.getPrice(), coffee.getVariant());
    }

    @Override
    public Coffee getItem() {
        return this;
    }

    @Override
    public double getTotalPrice(){
        return this.getPrice();
    }

    @Override
    public double getTotalPrice(double price){
        return price;
    }

    @Override
    public List<Filling> getFillings() {
        return new ArrayList<>();
    }
}
