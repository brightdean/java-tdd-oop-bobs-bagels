package com.booleanuk.core;

public class Discount {
    private int batches;
    private double price;
    private double placeHolderPrice;

    private String name;

    public Discount(String name, int batches, double price, double placeHolderPrice) {
        this.name = name;
        this.batches = batches;
        this.price = price;
        this.placeHolderPrice = placeHolderPrice;
    }

    public int getBatches() {
        return batches;
    }

    public void setBatches(int batches) {
        this.batches = batches;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPlaceHolderPrice() {
        return placeHolderPrice;
    }

    public void setPlaceHolderPrice(double placeHolderPrice) {
        this.placeHolderPrice = placeHolderPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
