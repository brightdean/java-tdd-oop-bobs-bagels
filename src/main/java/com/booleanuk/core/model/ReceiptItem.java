//package com.booleanuk.core.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ReceiptItem {
//    private String name;
//    private List<String> extras;
//    private int quantity;
//    private double price;
//
//    public ReceiptItem(String name, int quantity, double price) {
//        this.name = name;
//        this.quantity = quantity;
//        this.price = price;
//        extras = new ArrayList<>();
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public void updatePrice(double price){
//        this.price += price;
//    }
//
//    @Override
//    public String toString(){
//        String result = String.format("%-20.20s %3d %7.2f�", getName(), getQuantity(), getPrice());
//        for(String extra: extras) {
//            result += "\n +" + extra;
//        }
//        return result;
//    }
//
//    public void increaseQuantity() {
//        this.quantity++;
//    }
//
//    public void updateExtras(String supplementName) {
//        if(!extras.contains(supplementName))
//            extras.add(supplementName);
//    }
//}