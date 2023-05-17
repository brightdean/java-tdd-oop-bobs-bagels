package com.booleanuk.core;

public class ReceiptItem {

    private String name;
    private int quantity;
    private double price;

    private String[] fillings;
    private double priceBeforeDiscount;

    private String content="";


    public ReceiptItem(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }


    public ReceiptItem(String name, int quantity, double price, double priceBeforeDiscount, String...fillings) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;
        this.fillings = fillings;


    }
    public ReceiptItem(String name){
        this.name = name;
        this.content = String.format("%-20s\n", getName());
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

    public double getDifference(){
        return this.price - this.priceBeforeDiscount;
    }

    public void update(double price){
        this.quantity += 1;
        this.price += price;
    }

    @Override
    public String toString(){
        this.content =  String.format("%-20.20s %3d %8.2f\n", getName(), getQuantity(), getPrice());
        for(String filling : fillings) this.content+= String.format("+%-20s\n", filling);
        return content;

    }
}
