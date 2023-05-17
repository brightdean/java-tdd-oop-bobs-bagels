package com.booleanuk.core;

import com.booleanuk.core.model.Product;
import com.booleanuk.core.model.ProductSupplementAssociation;
import com.booleanuk.core.model.Supplement;

import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Product> products;
    private Inventory inventory;
    private int capacity;

    public Basket(Inventory inventory, int capacity) {
        this.inventory = inventory;
        this.products = new ArrayList<>();
        this.capacity = capacity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean add(String sku, String... supplementSkus) {
        // Check if basket is full
        if (products.size() >= capacity) {
            System.out.println("Basket is full");
            return false;
        }
        // Check if product in inventory
        if (inventory.getProduct(sku) == null) {
            System.out.println("Product not in inventory");
            return false;
        }
        Product newProduct = new Product(inventory.getProduct(sku));
        // If supplements where added, add them to the product if supported
        if (supplementSkus.length > 0) {
            addSupplementsToProduct(newProduct, supplementSkus);
        }
        products.add(newProduct);
        return true;
    }

    private void addSupplementsToProduct(Product product, String... supplementSkus) {
        for (String sku : supplementSkus) {
            if (ProductSupplementAssociation.hasSupplement(product.getSku(), sku)) {
                product.addSupplement(new Supplement(inventory.getSupplement(sku)));
            } else {
                System.out.println("You can't add " + sku + " to " + product.getSku());
            }
        }
    }

    public boolean remove(String id) {
        for (Product product : products) {
            if (product.getId() == id) {
                products.remove(product);
                return true;
            }
        }
        System.out.println("Product not in basket");
        return false;
    }

    public void expandBasket(int capacity) {
        if (this.capacity < capacity) this.capacity = capacity;
    }

    @Deprecated
    // method doesn't calculate special offers
    public double totalPrice() {
        if (products.isEmpty())
            return 0;

        double price = 0;
        for (Product product : products) {
            price += product.getPrice();
            if (!product.getSupplements().isEmpty()) {
                for (Supplement supplement : product.getSupplements()) {
                    price += supplement.getPrice();
                }
            }
        }
        return Math.floor(price * 100) / 100;
    }
}
