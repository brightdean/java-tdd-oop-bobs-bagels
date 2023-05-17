package com.booleanuk.core.model;

public class OfferItem {

    private int quantity;
    private String name;
    private double totalPrice;
    private double productPrice;
    private String productSKU;

    public OfferItem(int quantity, String name, double totalPrice, double productPrice, String productSKU) {
        this.quantity = quantity;
        this.name = name;
        this.totalPrice = totalPrice;
        this.productPrice = productPrice;
        this.productSKU = productSKU;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getProductSKU() {
        return productSKU;
    }

    public void setProductSKU(String productSKU) {
        this.productSKU = productSKU;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
}
