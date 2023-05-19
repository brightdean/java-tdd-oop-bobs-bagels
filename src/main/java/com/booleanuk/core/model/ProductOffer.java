package com.booleanuk.core.model;

import com.booleanuk.core.Inventory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ProductOffer implements Comparable<ProductOffer> {
    private String offerName;
    private String productSku;
    private int requiredQuantity;
    private BigDecimal price;
    private int priority;

    /**
     * <p>Stores the discounted price for specific quantities of a product.</p>
     * @param offerName the name of the offer in order to display it.
     * @param productSku the sku of the product to which the offer is to be applied.
     * @param requiredQuantity the quantity of products needed for the discounted price to be applied.
     * @param price the discounted price.
     * @param priority the priority in which the offers are to be applied. The smaller the number
     *                 the higher the priority.
     */
    public ProductOffer(String offerName, String productSku, int requiredQuantity, double price, int priority) {
        this.offerName = offerName;
        this.productSku = productSku;
        this.requiredQuantity = requiredQuantity;
        this.price = new BigDecimal(Double.toString(price));
        this.priority = priority;
    }

    public BigDecimal getPrice() {
        return price;
    }

    /**
     * <p>Calculates the discount and returns it. Also sets the isOfferApplied
     * field to true for each product that was included in the offer.</p>
     *
     * @param products is the list of products currently in the basket.
     * @return the discount that is applied to the original price.
     */
    public BigDecimal applyOffer(List<Product> products) {
        if (products.isEmpty()) return null;

        List<Product> eligibleProducts = products.stream()
                .filter(product -> product.getSku().equals(this.productSku))
                .filter(product -> !product.isOfferApplied()).toList();

        int timesOfferApplies = eligibleProducts.size() / this.requiredQuantity;
        if (timesOfferApplies > 0) {
            int productsToDiscountCount = timesOfferApplies * this.requiredQuantity;
            for (int i = 0; i < productsToDiscountCount; i++) {
                //TODO: change the whole method. The setOfferApplied way
                // makes it so I can't getTotalPrice twice or more.
                // Think of a way, and if it is better before doing it
                eligibleProducts.get(i).setOfferApplied(true);
            }
            BigDecimal regularPrice = new BigDecimal(Double.toString(
                    Inventory.getInstance().getPrice(this.productSku) * productsToDiscountCount));
            BigDecimal offerPrice = this.price.multiply(BigDecimal.valueOf(timesOfferApplies));
            return regularPrice.subtract(offerPrice);
        }
        return null;
    }

    @Override
    public int compareTo(ProductOffer o) {
        return Integer.compare(this.priority, o.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOffer that = (ProductOffer) o;
        return requiredQuantity == that.requiredQuantity &&
                priority == that.priority &&
                Objects.equals(offerName, that.offerName) &&
                Objects.equals(productSku, that.productSku) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerName, productSku, requiredQuantity, price, priority);
    }
}
