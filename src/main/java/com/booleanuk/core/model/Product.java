package com.booleanuk.core.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product extends Item {
    private List<Supplement> supplements;
    private boolean isOfferApplied = false;

    public Product(String sku, double price, String name, String variant) {
        super(sku, price, name, variant);
        supplements = new ArrayList<>();
    }

    /**
     * <p>Adds a supplement to the product and sorts the
     * list of supplements, in order to be able to use the equals
     * method effectively.</p>
     *
     * @param supplement the supplement to be added to the product.
     */
    public void addSupplement(Supplement supplement) {
        supplements.add(supplement);
        Collections.sort(supplements); //TODO: can use this for equals.
    }

    public List<Supplement> getSupplements() {
        return supplements;
    }

    /**
     * <p>isOfferApplied specifies if an offer has been applied on this
     * product</p>
     * @return boolean
     */
    public boolean isOfferApplied() {
        return this.isOfferApplied;
    }

    /**
     * <p>isOfferApplied specifies if an offer has been applied on this
     * product</p>
     */
    public void setOfferApplied(boolean offerApplied) {
        this.isOfferApplied = offerApplied;
    }

    /**
     * @return the sum of all supplement prices.
     */
    public BigDecimal getSupplementsPrice() {
        BigDecimal total = BigDecimal.ZERO;
        for (Supplement item : supplements) {
            total = total.add(item.getPrice());
        }
        return total;
    }

    /**
     * @return the names of the supplements in a format used to print
     * them in the receipt.
     */
    public String supplementsToString() {
        String result = "";
        for (Supplement supplement : supplements) {
            result += " +" + supplement + '\n';
        }
        return result;
    }

    /**
     * @return the products variant and name in a format used to print
     * it in the receipt.
     */
    @Override
    public String toString() {
        return this.getVariant() + " " + this.getName();
    }
}
