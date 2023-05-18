//package com.booleanuk.core;
//
//import com.booleanuk.core.model.*;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//public class Receipt {
//    private double total;
//    private double totalDiscount;
//    private Map<String, ReceiptItem> receiptItems;
//
//    public Receipt() {
//        receiptItems = new LinkedHashMap<>();
//    }
//
//    public void print(List<Product> products) {
//        // Calculate and create receipt values
//        createReceiptItems(products);
//        applySpecialOffers(products);
//        // Receipt Header
//        int lineLength = 33;
//        String context = addPadding(lineLength, "~~~ Bob's Bagels ~~~");
//        LocalDateTime now = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        context += "\n\n" + addPadding(lineLength, now.format(formatter)) + "\n\n";
//
//        // Receipt Items and Total
//        context += "-".repeat(lineLength) + "\n\n";
//        for (String key : receiptItems.keySet()) {
//            context += receiptItems.get(key) + "\n";
//        }
//        context += "-".repeat(lineLength) + "\n";
//        context += String.format("Total%27.02f�", Math.floor(total*100) / 100);
//
//
//        // Receipt Footer
//        if (this.totalDiscount > 0) {
//            this.totalDiscount = Math.floor(this.totalDiscount*100)/100; //format discount number
//            String temp = String.format("You saved a total of " + this.totalDiscount + "�");
//            context += "\n\n" + addPadding(lineLength, temp) + "\n";
//            context += addPadding(lineLength, "on this shop");
//        }
//        context += "\n\n" + addPadding(lineLength, "Thank you") + "\n";
//        context += addPadding(lineLength, "for your order!");
//        System.out.println(context);
//    }
//    private void createReceiptItems(List<Product> basketProducts) {
//        // Generate a ReceiptItem for each unique product
//        for (Product product : basketProducts) {
//            String key = receiptItemKey(product);
//            if (receiptItems.containsKey(key)) {
//                receiptItems.get(key).increaseQuantity();
//                receiptItems.get(key).updatePrice(product.getPrice());
//            } else {
//                ReceiptItem item = new ReceiptItem(product.getVariant() + " " + product.getName(),
//                        1, product.getPrice());
//                receiptItems.put(key, item);
//            }
//            // Add supplements to receipt and price
//            for (Supplement supplement : product.getSupplements()) {
//                receiptItems.get(key).updateExtras(supplement.getVariant());
//                receiptItems.get(key).updatePrice(supplement.getPrice());
//            }
//        }
//
//        for(String key: receiptItems.keySet()) {
//            this.total += receiptItems.get(key).getPrice();
//        }
//    }
//
//    private static String addPadding(int length, String str) {
//        String padding = " ".repeat((length - str.length()) / 2);
//        return padding + str + padding;
//    }
//
//    private static String receiptItemKey(Product product) {
//        String key = product.getSku();
//        ArrayList<String> skuList = new ArrayList<>();
//        if (!product.getSupplements().isEmpty()) {
//            for (Supplement supplement : product.getSupplements()) {
//                skuList.add(supplement.getSku());
//            }
//        }
//        Collections.sort(skuList);
//        for (String sku : skuList) {
//            key += sku;
//        }
//        return key;
//    }
//
//    private void applySpecialOffers(List<Product> basketProducts) {
//        // Populate a map with products, separated by SKU (map key)
//        Map<String, List<Product>> products = new HashMap<>();
//        for (Product product : basketProducts) {
//            String sku = product.getSku();
//            if (products.containsKey(sku)) {
//                products.get(sku).add(product);
//            } else {
//                products.put(sku, new ArrayList<>());
//                products.get(sku).add(product);
//            }
//        }
//
//        Map<String, List<OfferItem>> offers =  Offers.getOfferItems();
//
//        for (String key: offers.keySet()) {
//            if (offers.get(key).size() == 1) {
//                applySingleProductOffer(products, offers.get(key).get(0));
//            } else {
//                applyComboOffer(products, offers.get(key).toArray(new OfferItem[offers.get(key).size()]));
//            }
//        }
//    }
//
//    private void applySingleProductOffer(Map<String, List<Product>> products, OfferItem offer) {
//        if (products.containsKey(offer.getSku())) {
//            int timesOfferIsApplied = products.get(offer.getSku()).size() / offer.getQuantity();
//            if (timesOfferIsApplied > 0) {
//                double discountedPrice = timesOfferIsApplied * offer.getPrice();
//                double originalPrice = timesOfferIsApplied * offer.getQuantity() *
//                        Inventory.getInstance().getPrice(offer.getSku());
//                double discount = Math.floor((discountedPrice - originalPrice) * 100) / 100;
//
//                ReceiptItem item = new ReceiptItem(offer.getName(), timesOfferIsApplied, discount);
//                receiptItems.put(offer.getName(), item);
//                this.total += discount;
//                this.totalDiscount += Math.abs(discount);
//                products.get(offer.getSku()).removeIf(p -> products.get(offer.getSku()).indexOf(p)
//                        < timesOfferIsApplied * offer.getQuantity());
//            }
//        }
//    }
//
//    private void applyComboOffer(Map<String, List<Product>> products, OfferItem... offers) {
//        int timesOfferIsApplied = -1;
//        double offerTotalPrice = 0;
//        double originalTotalPrice = 0;
//        boolean firstComparison = true;
//
//        if (offers.length == 0) return;
//
//        // Check if all products necessary for the offer exist in the basket
//        // and set local variables
//        for (OfferItem offer: offers) {
//            if (!products.containsKey(offer.getSku())) {
//                System.out.println("Product not in offer");
//                return ;
//            } else {
//                int count = products.get(offer.getSku()).size() / offer.getQuantity();
//                timesOfferIsApplied = firstComparison ? count : Math.min(timesOfferIsApplied, count);
//                offerTotalPrice += offer.getPrice();
//                originalTotalPrice += Inventory.getInstance().getPrice(offer.getSku());
//                firstComparison = false;
//            }
//        }
//        // Generate receipt item, update total
//        if (timesOfferIsApplied > 0) {
//            double discountedPrice = timesOfferIsApplied * offerTotalPrice;
//            double originalPrice = timesOfferIsApplied * originalTotalPrice;
//            double discount = Math.floor((discountedPrice - originalPrice) * 100) / 100;
//
//            ReceiptItem item = new ReceiptItem(offers[0].getName(), timesOfferIsApplied, discount);
//            receiptItems.put(offers[0].getName(), item);
//            this.total += discount;
//            this.totalDiscount += Math.abs(discount);
//        }
//
//        //Remove items used in discount from List
//        for (OfferItem offer: offers) {
//            int finalTimesOfferIsApplied = timesOfferIsApplied;
//            products.get(offer.getSku()).removeIf(p -> products.get(offer.getSku()).indexOf(p)
//                    < finalTimesOfferIsApplied * offer.getQuantity());
//        }
//    }
//}