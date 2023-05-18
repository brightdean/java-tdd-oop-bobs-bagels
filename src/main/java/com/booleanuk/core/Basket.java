package com.booleanuk.core;

import com.booleanuk.core.model.Product;
import com.booleanuk.core.model.ProductOffer;
import com.booleanuk.core.model.ProductSupplementAssociation;
import com.booleanuk.core.model.Supplement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Basket {
    private List<Product> products;
    private Inventory inventory;
    private int capacity;

    public Basket(int capacity) {
        this.inventory = Inventory.getInstance();
        this.products = new ArrayList<>();
        this.capacity = capacity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getCapacity() {
        return this.capacity;
    }

    /**
     * @param productSku     the sku of a product we want to add to the basket.
     * @param supplementSkus an array of supplement sku's to add to the product.
     *                       This is an optional parameter (VarArg).
     * @return true if product was added to the basket.
     */
    public boolean add(String productSku, String... supplementSkus) {
        // Check if basket is full
        if (this.products.size() >= this.capacity) {
            System.out.println("Basket is full");
            return false;
        }
        // Check if product is in inventory
        if (this.inventory.getProduct(productSku) == null) {
            System.out.println("Product not in inventory");
            return false;
        }
        //Create Product
        Product invProduct = this.inventory.getProduct(productSku);
        Product product = new Product(invProduct.getSku(), invProduct.getPrice().doubleValue(),
                invProduct.getName(), invProduct.getVariant());
        // Add supplements to the product if they were provided
        if (supplementSkus.length > 0) {
            addSupplementsToProduct(product, supplementSkus);
        }
        this.products.add(product);
        return true;
    }

    /**
     * <p>Checks for each supplement sku if it is associated with the product
     * and if it is, it is added to the product, else it prints an
     * appropriate message.</p>
     *
     * @param product        the product we want to add the supplements on.
     * @param supplementSkus an array of supplement sku's to add to the product
     *                       This is an optional parameter (VarArg).
     */
    private void addSupplementsToProduct(Product product, String... supplementSkus) {
        for (String sku : supplementSkus) {
            if (ProductSupplementAssociation.hasSupplement(product.getSku(), sku)) {
                Supplement supplement = this.inventory.getSupplement(sku);
                product.addSupplement(new Supplement(supplement.getSku(),
                        supplement.getPrice().doubleValue(), supplement.getName(),
                        supplement.getVariant()));
            } else {
                System.out.println("You can't add " + sku + " to " + product.getSku());
            }
        }
    }

    /**
     * <p>Removes a product from the basket.</p>
     *
     * @param id the id of a product.
     * @return true if the basket contained the specified product.
     */
    public boolean remove(String id) {
        for (Product product : this.products) {
            if (product.getId() == id) {
                this.products.remove(product);
                return true;
            }
        }
        System.out.println("Product not in basket");
        return false;
    }

    /**
     * <p>Expands the capacity of the basket.</p>
     *
     * @param capacity is the new capacity.
     */
    public void expandBasket(int capacity) {
        if (this.capacity < capacity) this.capacity = capacity;
    }

    /**
     * @return the total price of the items in the basket.
     */
    public double totalPrice() {
        if (this.products.isEmpty())
            return 0;

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Product product : this.products) {
            totalPrice = totalPrice.add(product.getPrice()); // TODO: maybe make a getTotalPrice() {product totalPrice + supplements totalPrice}
            totalPrice = totalPrice.add(product.getSupplementsPrice());
        }

        var productOffers = OffersManager.getProductOffers();
        for (ProductOffer offer : productOffers) {
            BigDecimal discountPrice = offer.applyOffer(this.products);
            if ( discountPrice != null) {
                totalPrice = totalPrice.subtract(discountPrice);
            }
        }

        BigDecimal displayPrice = totalPrice.setScale(2, RoundingMode.HALF_EVEN);
        //TODO: move this to the printing method - Class
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        //return nf.format(displayPrice.doubleValue());
        return displayPrice.doubleValue();
    }
}
