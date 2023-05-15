package com.booleanuk.core;

import java.util.*;

import static com.booleanuk.core.Store.stock;

public class Basket {
    private List<Product> products;
    private int capacity;

    public Basket(int capacity) {
        this.capacity = capacity;
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean remove(String id) {
        Product productToRemove = getProductFromBasketById(id);
        boolean result = products.remove(productToRemove);

        System.out.println(result ? "Product removed from basket" : "Product not in basket");
        return result;
    }

    private Product getProductFromBasketById(String id) {
        return this.products.stream()
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
        for (Product product : products) {
            sum += product.getPrice();
        }
        return sum;
    }

    public double getPrice(String sku) {
        if (stock.containsKey(sku)) {
            return stock.get(sku).getPrice();
        }
        return -1;
    }

    public boolean add(String sku, String... fillingSkus) {
        if (products.size() >= capacity) return false;

        Item item = stock.get(sku);
        if (item != null) {
            if (item.getName().equals("Bagel")) {
                Bagel newBagel = new Bagel(item.getItem());
                if (fillingSkus.length > 0) {
                    addFillings(newBagel, fillingSkus);
                }
                products.add(newBagel);
                return true;
            }
            if (item.getName().equals("Coffee")) {
                products.add(new Coffee(item.getItem()));
                return true;
            }
        }
        return false;
    }

    private void addFillings(Bagel bagel, String... fillingSkus) {
        for (String sku : fillingSkus) {
            if (!stock.containsKey(sku)) {
                continue;
            }
            if (stock.get(sku).getName().equals("Filling")) {
                bagel.addFilling(new Filling(stock.get(sku).getItem()));
            }
        }
    }
}
