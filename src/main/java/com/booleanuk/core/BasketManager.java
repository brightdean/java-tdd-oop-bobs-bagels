package com.booleanuk.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BasketManager {

    //TODO: convert stock to HashMap with key = SKU || [name, variant]
    // new keySet[name,variant]
    static List<Item> stock;
    private List<Product> basket;
    private int capacity;

    public BasketManager(int capacity) {
        this.capacity = capacity;
        this.basket = new ArrayList<>();
    }

    static {
        stock = new ArrayList<>();
        // Bagels
        stock.add(new Bagel("BGLO", 0.49, "Onion") {
        });
        stock.add(new Bagel("BGLP", 0.39, "Plain"));
        stock.add(new Bagel("BGLE", 0.49, "Everything"));
        stock.add(new Bagel("BGLS", 0.49, "Sesame"));

        // Coffees
        stock.add(new Coffee("COFB", 0.99, "Black"));
        stock.add(new Coffee("COFW", 1.19, "White"));
        stock.add(new Coffee("COFC", 1.29, "Cappuccino"));
        stock.add(new Coffee("COFL", 1.29, "Latte"));

        // Fillings
        stock.add(new Filling("FILB", 0.12, "Bacon"));
        stock.add(new Filling("FILE", 0.12, "Egg"));
        stock.add(new Filling("FILC", 0.12, "Cheese"));
        stock.add(new Filling("FILX", 0.12, "Cream Cheese"));
        stock.add(new Filling("FILS", 0.12, "Smoked Salmon"));
        stock.add(new Filling("FILH", 0.12, "Ham"));
    }

    public List<Product> getBasket() {
        return basket;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean remove(String id) {
        Product productToRemove = getProductFromBasketById(id);
        boolean result = basket.remove(productToRemove);

        System.out.println(result ? "Product removed from basket" : "Product not in basket");
        return result;
    }

    private Product getProductFromBasketById(String id) {
        return this.basket.stream()
                .filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean expandBasket(int capacity) {
        if (capacity > this.capacity) {
            this.capacity = capacity;
            return true;
        }
        return false;
    }

    public double totalPrice() {
        double sum = 0;
        for (Product product : basket) {
            sum += product.getPrice();
        }
        return sum;
    }

    public double getPrice(String name, String variant) {
        Optional<Item> foundItem = stock.stream()
                .filter(item -> item.getName().equals(name)
                        && item.getVariant().equals(variant))
                .findFirst();

        return foundItem.map(Item::getPrice).orElse(-1.0);
    }

    public boolean add (String name, String variant, String ...fillings) {
        if (basket.size() >= capacity) return false;

        // If item is in stock
        for (Item item: stock) {
            if (item.getName().equals(name) && item.getVariant().equals(variant)) {
                if (item.getName().equals("Bagel")) {
                    Bagel newBagel = new Bagel((Bagel) item);
                    if (fillings.length > 0) {
                       addFillings(newBagel, fillings);
                    }
                    basket.add(newBagel);
                    return true;
                }
                if (item.getName().equals("Coffee")) {
                    basket.add (new Coffee((Coffee) item));
                    return true;
                }
            }
        }
        return false;
    }

    private void addFillings(Bagel bagel, String ...fillings) {
        for(String filling: fillings) {
            for(Item item: stock) {
                if (item.getVariant().equals(filling) && item.getName().equals("Filling"))
                    bagel.addFilling(new Filling ((Filling) item));
            }
        }
    }
}
