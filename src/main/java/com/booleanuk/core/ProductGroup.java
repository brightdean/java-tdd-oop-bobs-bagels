package com.booleanuk.core;

import java.util.HashMap;
import java.util.Map;

public class ProductGroup {

    private Map<String, Product> products;

    private double initialCost;
    private double discount;

    public ProductGroup(){
        products = new HashMap<>();
        initialCost = 0;
        discount = 0;
    }

    public void addProduct(Product product){
        this.products.put(product.getId(), product);
        setInitialCost(getInitialCost() + product.getTotalPrice());
    }

    public double getCost(){
        double total = discount;
        for(String id : this.getProducts().keySet()){
            //total += getProducts().get(id).getPrice();
            total += getProducts().get(id).getTotalPrice();
        }
        return total;
    }

    public double getDifference(){
        return this.getCost() - initialCost;
    }
    public Map<String, Product> getProducts() {
        return products;
    }


    public double getInitialCost() {
        return initialCost;
    }

    public void setInitialCost(double initialCost) {
        this.initialCost = initialCost;
    }


    public void setDiscount(double discount) {
        this.discount = discount;
    }


    @Override
    public String toString(){
        String content = String.format("Initial cost: %.2f\n", initialCost);
        content += String.format("After cost: %.2f\n", getCost());
        content += String.format("Saved: %.2f\n", getDifference());
        for(String id : products.keySet()){
            content += products.get(id).getName() + " " + products.get(id).getVariant() + " " + products.get(id).getPrice() + ", " + products.get(id).isUnderDiscount()+ ", ";
        }

        return content;
    }

}
