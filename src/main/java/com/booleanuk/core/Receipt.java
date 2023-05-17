package com.booleanuk.core;

import com.booleanuk.core.model.*;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Receipt {
    private double total;
    private double totalDiscount; //TODO: show in receipt
    private Map<String, ReceiptItem> receiptItems;

    public Receipt() {
        receiptItems = new LinkedHashMap<>();
    }

    public void print(List<Product> products) {
        createReceiptItems(products);
        applySpecialOffers(products);

        int lineLength = 33;
        String context = addPadding(lineLength, "~~~ Bob's Bagels ~~~");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        context += "\n\n" + addPadding(lineLength, now.format(formatter)) + "\n\n";

        context += "-".repeat(lineLength) + "\n\n";

        for (String key : receiptItems.keySet()) {
            context += receiptItems.get(key) + "\n";
        }

        context += "-".repeat(lineLength) + "\n";
        context += String.format("Total%27.02f€", Math.floor(total*100) / 100);
        context += "\n\n" + addPadding(lineLength, "Thank you") + "\n";
        context += addPadding(lineLength, "for your order!");
        System.out.println(context);
    }
    private void createReceiptItems(List<Product> basketProducts) {
        // Generate a ReceiptItem for each unique product
        for (Product product : basketProducts) {
            String key = receiptItemKey(product); //TODO: improve
            if (receiptItems.containsKey(key)) {
                receiptItems.get(key).increaseQuantity();
                receiptItems.get(key).updatePrice(product.getPrice());
            } else {
                ReceiptItem item = new ReceiptItem(product.getVariant() + " " + product.getName(),
                        1, product.getPrice());
                receiptItems.put(key, item);
            }
            // Add supplements to receipt and price
            for (Supplement supplement : product.getSupplements()) {
                receiptItems.get(key).updateExtras(supplement.getVariant());
                receiptItems.get(key).updatePrice(supplement.getPrice());
            }
        }

        for(String key: receiptItems.keySet()) {
            total += receiptItems.get(key).getPrice();
        }
    }

    private static String addPadding(int length, String str) {
        String padding = " ".repeat((length - str.length()) / 2);
        return padding + str + padding;
    }

    private static String receiptItemKey(Product product) {
        String key = product.getSku();
        ArrayList<String> skuList = new ArrayList<>();
        if (!product.getSupplements().isEmpty()) {
            for (Supplement supplement : product.getSupplements()) {
                skuList.add(supplement.getSku());
            }
        }
        Collections.sort(skuList);
        for (String sku : skuList) {
            key += sku;
        }
        return key;
    }

    private void applySpecialOffers(List<Product> basketProducts) {
        // Populate a map with products, separated by SKU (map key)

        Map<String, List<Product>> products = new HashMap<>(); //TODO: replace with filter Arraylist by SKU
        for (Product product : basketProducts) {
            String sku = product.getSku();
            if (products.containsKey(sku)) {
                products.get(sku).add(product);
            } else {
                products.put(sku, new ArrayList<>());
                products.get(sku).add(product);
            }
        }

        ItemOffer bglp12 = new ItemOffer("Offer: 12 Plain Bagels", "BGLP", 12, 3.99);
        ItemOffer bglo6 = new ItemOffer("Offer: 6 Onion Bagels","BGLO", 6, 2.49);
        ItemOffer bgle6 = new ItemOffer("Offer: 6 Ever Bagels","BGLE", 6, 2.49);

        ItemOffer coffeeB = new ItemOffer("Offer: Coff & Bagel","COFB", 1, 0.99);
        ItemOffer bagelP = new ItemOffer("Offer: Coff & Bagel","BGLP", 1, 0.26);

        applySingleProductOffer(products, bglp12);
        applySingleProductOffer(products, bglo6);
        applySingleProductOffer(products, bgle6);

        //TODO: implement COFB + BGLP offer
        applyComboOffer(products, coffeeB, bagelP);
    }
    
    private void applySingleProductOffer(Map<String, List<Product>> products, ItemOffer offer) {
        if (products.containsKey(offer.getSku())) {
            int timesOfferIsApplied = products.get(offer.getSku()).size() / offer.getQuantity();
            if (timesOfferIsApplied > 0) {
                double discountedPrice = timesOfferIsApplied * offer.getPrice();
                double originalPrice = timesOfferIsApplied * offer.getQuantity() *
                        Inventory.getInstance().getPrice(offer.getSku());
                double discount = Math.floor((discountedPrice - originalPrice) * 100) / 100;

                ReceiptItem item = new ReceiptItem(offer.getName(), timesOfferIsApplied, discount);
                receiptItems.put(offer.getName(), item);
                total += discount;
                // TODO: check if this works
                products.get(offer.getSku()).removeIf(p -> products.get(offer.getSku()).indexOf(p)
                        < timesOfferIsApplied * offer.getQuantity());
            }
        }
    }

    private void applyComboOffer(Map<String, List<Product>> products, ItemOffer ... offers) {
        int timesOfferIsApplied = -1;
        double offerTotalPrice = 0;
        double originalTotalPrice = 0;
        boolean firstComparison = true;

        if (offers.length == 0) return;

        // Check if all products necessary for the offer exist in the basket
        // and set local variables
        for (ItemOffer offer: offers) {
            if (!products.containsKey(offer.getSku())) {
                System.out.println("Product not in offer");
                return ;
            } else {
                int count = products.get(offer.getSku()).size() / offer.getQuantity();
                timesOfferIsApplied = firstComparison ? count : Math.min(timesOfferIsApplied, count);
                offerTotalPrice += offer.getPrice();
                originalTotalPrice += Inventory.getInstance().getPrice(offer.getSku());
                firstComparison = false;
            }
        }
        // Generate receipt item, update total
        if (timesOfferIsApplied > 0) {
            double discountedPrice = timesOfferIsApplied * offerTotalPrice;
            double originalPrice = timesOfferIsApplied * originalTotalPrice;
            double discount = Math.floor((discountedPrice - originalPrice) * 100) / 100;

            ReceiptItem item = new ReceiptItem(offers[0].getName(), timesOfferIsApplied, discount);
            receiptItems.put(offers[0].getName(), item);
            total += discount;
        }

        //Remove items used in discount from List
        for (ItemOffer offer: offers) {
            int finalTimesOfferIsApplied = timesOfferIsApplied;
            products.get(offer.getSku()).removeIf(p -> products.get(offer.getSku()).indexOf(p)
                    < finalTimesOfferIsApplied * offer.getQuantity());
        }
    }
}