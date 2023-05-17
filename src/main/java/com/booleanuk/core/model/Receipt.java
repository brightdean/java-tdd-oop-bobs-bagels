package com.booleanuk.core.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Receipt {
    private List<Product> products;

    private HashMap<String, ReceiptItem> receiptItems = new HashMap<>();
    private HashMap<String, ReceiptItem> discountItems = new HashMap<>();


    public Receipt(List<Product> products){
        this.products = products;
    }

    private HashMap<String, ReceiptItem> createReceiptItems(){

        HashMap<String, ReceiptItem> receiptItems = new HashMap<>();

        for(Product product : products){
            StringBuilder keyBuilder = new StringBuilder(product.getSKU() + "-");

            product.getFillings().sort(Comparator.comparing(Item::getSKU));
            if(product.getFillings().size() > 0){

                for(Filling f : product.getFillings()){
                    keyBuilder.append(f.getSKU().charAt(f.getSKU().length()-1));

                }
            }
            String key = keyBuilder.toString();

            if(receiptItems.containsKey(key)){
                receiptItems.get(key).update(product.getTotalPrice());
            }else{
                String displayName = product.getVariant() + " " + product.getName();
                int quantity = 1;
                double price = product.getTotalPrice();
                String[] fillings = new String[0];
                fillings = product.getFillings().stream().map(Item::getVariant).toList().toArray(fillings);

                receiptItems.put(key, new ReceiptItem(displayName, quantity, price, 0, fillings));
            }
        }
        return receiptItems;
    }
    public void applySingleDiscount(OfferItem offerItem){

        List<Product> group = products.stream().filter(product -> product.getSKU().equals(offerItem.getProductSKU())).toList();

        int groupCount = group.size();
        if(groupCount > 0){
            int quantity = groupCount / offerItem.getQuantity();
            if(quantity > 0){
                double discAmount = offerItem.getTotalPrice()*quantity;
                double initialAmount = 0.0;
                for(int i = 0; i < groupCount; i++){

                    initialAmount += group.get(i).getTotalPrice();
                    if(i < quantity* offerItem.getQuantity()) {
                        discAmount += group.get(i).getTotalPrice(offerItem.getProductPrice());
                        group.get(i).setUnderDiscount(true);
                    }

                    else
                        discAmount += group.get(i).getTotalPrice();
                }
                discountItems.put(offerItem.getProductSKU(), new ReceiptItem(offerItem.getName(), quantity, discAmount-initialAmount, 0));
            }

        }
    }


    public void applyComboDiscount(String SKU1, String SKU2, double discountPrice){
        //TODO make Discount abstract and have SingleDiscount and ComboDiscount children

        //list of products with sku = SKU1
        List<Product> primaryProducts = products.stream().filter(product -> product.getSKU().equals(SKU1)).toList();
        int p1Count = primaryProducts.size();

        //list of products with sku = SKU2 that are not already on a discount
        List<Product> childProducts = products.stream().filter(product -> product.getSKU().equals(SKU2) && !product.isUnderDiscount()).toList();
        int p2Count = childProducts.size();

        int quantity = Math.min(p1Count, p2Count);
        int discountCount = quantity;

        if(quantity > 0){
            double discount = 0.0;
            while(discountCount > 0){
                childProducts.get(discountCount-1).setUnderDiscount(true);
                primaryProducts.get(discountCount-1).setUnderDiscount(true);
                double comboPrice = childProducts.get(discountCount-1).getTotalPrice() + primaryProducts.get(discountCount-1).getTotalPrice();
                discount += discountPrice - comboPrice;
                discountCount--;
            }

            discountItems.put(SKU1, new ReceiptItem("Black Coffee & Bagel Disc", quantity, discount, 0));
        }

    }
    public void print(){

        String content = String.format("\n      ~~~ Bob's Bagels ~~~      \n");
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateTime.format(formatter);
        content += String.format("\n      %s\n", formattedDate);

        content += "\n--------------------------------\n";

        receiptItems = createReceiptItems();

        for(String key : this.receiptItems.keySet()){
            content += receiptItems.get(key);
        }

        for(String key : this.discountItems.keySet()){
            content += discountItems.get(key);
        }

        content += "\n--------------------------------\n";

        //calculate total
        double total = 0d;
        double discount = 0.d;
        for(String key : receiptItems.keySet()){
            total += receiptItems.get(key).getPrice();
        }
        for(String key : discountItems.keySet()){
            discount += discountItems.get(key).getPrice();
        }

        content += String.format("Total%28.2f\n", total+discount);
        discount = Math.floor(discount * 100)/100.0;
        content += String.format("%20.26s\n%20.26s\n%20.26s\n", "You saved a total of " + Math.abs(discount), "on this shop","Thank you for your order!");
        System.out.println(content);
    }

}
