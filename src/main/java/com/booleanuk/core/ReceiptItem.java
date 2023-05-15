package com.booleanuk.core;

public class ReceiptItem {

    private String name;
    private int quantity;
    private double price;

    //TODO add order for printing items in order??

    public ReceiptItem(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void updatePrice(double price){
        this.price += price;
    }


    @Override
    public String toString(){
        return String.format("%-20s %3d %8.2f", getName(), getQuantity(), getPrice());
    }
}
