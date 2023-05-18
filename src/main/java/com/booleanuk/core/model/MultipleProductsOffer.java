package com.booleanuk.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//TODO: maybe connect the two Product offer classes(interface of inheritance)
//  and even with the OffersManager maybe methods can be rearranged
// can extract: name, price priority, Comparable
public class MultipleProductsOffer implements Comparable<MultipleProductsOffer> {
    private String offerName;
    private List<OfferItem> offerItems;
    private BigDecimal price;
    private int priority;

    /**
     * <p>Stores the discounted price for specific quantities of products.</p>
     * @param offerName the name of the offer in order to display it.
     * @param price the discounted price.
     * @param priority the priority in which the offers are to be applied. The smaller the number
     *                 the higher the priority.
     */
    public MultipleProductsOffer(String offerName, BigDecimal price, int priority) {
        this.offerName = offerName;
        this.price = price;
        this.priority = priority;
        offerItems = new ArrayList<>();
    }

    /**
     * <p>Adds a products sku and required quantity to the list of products
     * for this offer.</p>
     * @param productSku the sku of the product to which the offer is to be applied.
     * @param requiredQuantity the quantity of products needed for the discounted price to be applied.
     */
    public void addProduct(String productSku, int requiredQuantity) {
        var offerItem = new OfferItem(productSku, requiredQuantity);
        if (offerItems.contains(offerItem)) {
            System.out.println("Product already in offer");
        } else {
            offerItems.add(offerItem);
        }
        offerItems.add(offerItem);
    }

    /**
     * <p>Calculates the discount and returns it. Also sets the isOfferApplied
     * field to true for each product that was included in the offer.</p>
     *
     * @param products is the list of products currently in the basket.
     * @return the discount that is applied to the original price.
     */
    public BigDecimal applyOffer(List<Product> products) {
        if (offerItems.isEmpty() || products.isEmpty()) return null;
        //TODO: implement
        return null;
    }

    @Override
    public int compareTo(MultipleProductsOffer o) {
        return Integer.compare(this.priority, o.priority);
    }


}

class OfferItem {
    private String productSku;
    private int requiredQuantity;

    /**
     * @param productSku the sku of the product to which the offer is to be applied.
     * @param requiredQuantity the quantity of products needed for the discounted price to be applied.
     */
    public OfferItem(String productSku, int requiredQuantity) {
        this.productSku = productSku;
        this.requiredQuantity = requiredQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferItem that = (OfferItem) o;
        return requiredQuantity == that.requiredQuantity && Objects.equals(productSku, that.productSku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productSku, requiredQuantity);
    }
}