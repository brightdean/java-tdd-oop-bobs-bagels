package com.booleanuk.core;

import java.util.ArrayList;
import java.util.List;

public class BasketManager {

    static List<Item> stock;
    private List<Product> basket;

    public BasketManager(){

        this.basket = new ArrayList<>();
    }

   static {
        stock = new ArrayList<>();
        // Bagels
        stock.add(new Item("BGLO", 0.49, "Bagel", "Onion"));
        stock.add(new Item("BGLP", 0.39, "Bagel", "Plain"));
        stock.add(new Item("BGLE", 0.49, "Bagel", "Everything"));
        stock.add(new Item("BGLS", 0.49, "Bagel", "Sesame"));

        // Coffees
        stock.add(new Item("COFB", 0.99, "Coffee", "Black"));
        stock.add(new Item("COFW", 1.19, "Coffee", "White"));
        stock.add(new Item("COFC", 1.29, "Coffee", "Cappuccino"));
        stock.add(new Item("COFL", 1.29, "Coffee", "Latte"));

        // Fillings
        stock.add(new Item("FILB", 0.12, "Filling", "Bacon"));
        stock.add(new Item("FILE", 0.12, "Filling", "Egg"));
        stock.add(new Item("FILC", 0.12, "Filling", "Cheese"));
        stock.add(new Item("FILX", 0.12, "Filling", "Cream Cheese"));
        stock.add(new Item("FILS", 0.12, "Filling", "Smoked Salmon"));
        stock.add(new Item("FILH", 0.12, "Filling", "Ham"));

    }

    public List<Product> getBasket() {
        return basket;
    }

    public void setBasket(List<Product> basket) {
        this.basket = basket;
    }
}
