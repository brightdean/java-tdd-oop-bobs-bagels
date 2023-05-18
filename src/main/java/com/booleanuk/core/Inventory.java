package com.booleanuk.core;

import com.booleanuk.core.model.Product;
import com.booleanuk.core.model.Supplement;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;
    private Map<String, Supplement> supplements;
    private static Inventory instance;
    //TODO: consider changing to static (better performance)
    private Inventory() {
        products = new HashMap();
        supplements = new HashMap<>();
    }

    public static Inventory getInstance() {
        if (instance == null)
            instance = new Inventory();
        return instance;
    }

    int size() {
        return products.size() + supplements.size();
    }

    public Product getProduct(String sku) {
        return products.get(sku);
    }

    public double getPrice(String sku) {
        if (products.get(sku) != null)
            return products.get(sku).getPrice().doubleValue();
        if (supplements.get(sku) != null)
            return supplements.get(sku).getPrice().doubleValue();
        System.out.println("Item not in inventory");
        return -1;
    }

    public Supplement getSupplement(String sku) {
        return supplements.get(sku);
    }

    public void addProduct(String sku, double price, String name, String variant) {
        products.put(sku, new Product(sku, price, name, variant));
    }

    public void addSupplement(String sku, double price, String name, String variant) {
        supplements.put(sku, new Supplement(sku, price, name, variant));
    }
}
