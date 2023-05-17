package com.booleanuk.core.model;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Product> products;
    private Map<String, Supplement> supplements;

    private static Inventory instance;

    private Inventory() {
        products = new HashMap();
        supplements = new HashMap<>();
        startingInventory();
    }

    public static Inventory getInstance() {
        if (instance == null)
            instance = new Inventory();
        return instance;
    }

    private void startingInventory() {
        // Products
        products.put("BGLO", new Product("BGLO", 0.49, "Bagel", "Onion"));
        products.put("BGLP", new Product("BGLP", 0.39, "Bagel", "Plain"));
        products.put("BGLE", new Product("BGLE", 0.49, "Bagel", "Everything"));
        products.put("BGLS", new Product("BGLS", 0.49, "Bagel", "Sesame"));

        products.put("COFB", new Product("COFB", 0.99, "Coffee", "Black"));
        products.put("COFW", new Product("COFW", 1.19, "Coffee", "White"));
        products.put("COFC", new Product("COFC", 1.29, "Coffee", "Cappuccino"));
        products.put("COFL", new Product("COFL", 1.29, "Coffee", "Latte"));

        // Supplements
        supplements.put("FILB", new Supplement("FILB", 0.12, "Filling", "Bacon"));
        supplements.put("FILE", new Supplement("FILE", 0.12, "Filling", "Egg"));
        supplements.put("FILC", new Supplement("FILC", 0.12, "Filling", "Cheese"));
        supplements.put("FILX", new Supplement("FILX", 0.12, "Filling", "Cream Cheese"));
        supplements.put("FILS", new Supplement("FILS", 0.12, "Filling", "Smoked Salmon"));
        supplements.put("FILH", new Supplement("FILH", 0.12, "Filling", "Ham"));
        supplements.put("NFIL", new Supplement("FILNOT", 0.12, "NotFilling", "Feeling"));

        //Associate Product with Supplements
        ProductSupplementAssociation.add("BGLO",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
        ProductSupplementAssociation.add("BGLP",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
        ProductSupplementAssociation.add("BGLE",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
        ProductSupplementAssociation.add("BGLS",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
    }

    public int size() {
        return products.size() + supplements.size();
    }

    public Product getProduct(String sku) {
        return products.get(sku);
    }

    public double getPrice(String sku) {
        if (products.get(sku) != null)
            return products.get(sku).getPrice();
        if (supplements.get(sku) != null)
            return supplements.get(sku).getPrice();
        System.out.println("Item not in inventory");
        return -1;
    }

    public Supplement getSupplement(String sku) {
        return supplements.get(sku);
    }
}
