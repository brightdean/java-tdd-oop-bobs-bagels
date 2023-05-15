package com.booleanuk.core;

import java.util.HashMap;
import java.util.Map;

public class Store {
    static Map<String, Item> stock;

    static {
        stock = new HashMap<>();
        // Bagels
        stock.put("BGLO", new Bagel("BGLO", 0.49, "Onion") {
        });
        stock.put("BGLP", new Bagel("BGLP", 0.39, "Plain"));
        stock.put("BGLE", new Bagel("BGLE", 0.49, "Everything"));
        stock.put("BGLS", new Bagel("BGLS", 0.49, "Sesame"));

        // Coffees
        stock.put("COFB", new Coffee("COFB", 0.99, "Black"));
        stock.put("COFW", new Coffee("COFW", 1.19, "White"));
        stock.put("COFC", new Coffee("COFC", 1.29, "Cappuccino"));
        stock.put("COFL", new Coffee("COFL", 1.29, "Latte"));

        // Fillings
        stock.put("FILB", new Filling("FILB", 0.12, "Bacon"));
        stock.put("FILE", new Filling("FILE", 0.12, "Egg"));
        stock.put("FILC", new Filling("FILC", 0.12, "Cheese"));
        stock.put("FILX", new Filling("FILX", 0.12, "Cream Cheese"));
        stock.put("FILS", new Filling("FILS", 0.12, "Smoked Salmon"));
        stock.put("FILH", new Filling("FILH", 0.12, "Ham"));
    }

}
