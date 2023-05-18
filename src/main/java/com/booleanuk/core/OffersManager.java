package com.booleanuk.core;

import com.booleanuk.core.model.MultipleProductsOffer;
import com.booleanuk.core.model.Product;
import com.booleanuk.core.model.ProductOffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OffersManager {
    private static List<ProductOffer> productOffers;
    private static List<MultipleProductsOffer> multipleProductsOffers;

    static {
        productOffers = new ArrayList<>();
    }

    /**
     * <p>Creates a product offer and adds it to the list of product offers.
     * Then sorts the array by priority.</p>
     * @param name is the name of the offer.
     * @param productSku is the sku of the product on which the offer is applied.
     * @param requiredQuantity the quantity of the products needed for the offer to be applied,
     *                 and the quantity of the products the offer is applied on.
     * @param price the total price for the quantity of products.
     * @param priority the priority in which the offers are applied. Smaller number means higher priority.
     */
    public static void createProductOffer(String name, String productSku, int requiredQuantity, double price, int priority) {
        ProductOffer productOffer = new ProductOffer(name, productSku, requiredQuantity, price, priority);
        if (productOffers.contains(productOffer)) {
            System.out.println("Product offer already exists");
        } else {
            productOffers.add(productOffer);
            Collections.sort(productOffers);
        }
    }

    /**
     * <p>productOffer is a list with all the product offers.</p>
     * @return List<ProductOffer>
     */
    public static List<ProductOffer> getProductOffers() {
        return productOffers;
    }
}
