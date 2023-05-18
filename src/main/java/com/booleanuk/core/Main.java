package com.booleanuk.core;

import com.booleanuk.core.model.ProductSupplementAssociation;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        BigDecimal price = new BigDecimal("2.234");
        BigDecimal displayPrice = price.setScale(2, RoundingMode.HALF_EVEN);
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.GERMANY);

        System.out.println(nf.format(displayPrice.doubleValue()));
        System.out.println("\u0394");
    }

    public static void createDemoData() {
        // Create Special Offers
        OffersManager.createProductOffer("Offer: 12 Plain Bagels", "BGLP", 12, 3.99, 1);
        OffersManager.createProductOffer("Offer: 6 Onion Bagels", "BGLO", 6, 2.49, 1);
        OffersManager.createProductOffer("Offer: 6 Ever Bagels", "BGLE", 6, 2.49, 1);

        //OffersManager.createComboProductOffer("Offer: Coff & Bagel", "COFB", 1, 0.99, 2);
        //OffersManager.createComboProductOffer("Offer: Coff & Bagel", "BGLP", 1, 0.26, 2);

        //Populate Inventory
        Inventory inventory = Inventory.getInstance();

        // Products
        inventory.addProduct("BGLO", 0.49, "Bagel", "Onion");
        inventory.addProduct("BGLP", 0.39, "Bagel", "Plain");
        inventory.addProduct("BGLE", 0.49, "Bagel", "Everything");
        inventory.addProduct("BGLS", 0.49, "Bagel", "Sesame");

        inventory.addProduct("COFB", 0.99, "Coffee", "Black");
        inventory.addProduct("COFW", 1.19, "Coffee", "White");
        inventory.addProduct("COFC", 1.29, "Coffee", "Cappuccino");
        inventory.addProduct("COFL", 1.29, "Coffee", "Latte");

        // Supplements
        inventory.addSupplement("FILB", 0.12, "Filling", "Bacon");
        inventory.addSupplement("FILE", 0.12, "Filling", "Egg");
        inventory.addSupplement("FILC", 0.12, "Filling", "Cheese");
        inventory.addSupplement("FILX", 0.12, "Filling", "Cream Cheese");
        inventory.addSupplement("FILS", 0.12, "Filling", "Smoked Salmon");
        inventory.addSupplement("FILH", 0.12, "Filling", "Ham");
        inventory.addSupplement("FILNOT", 0.12, "NotFilling", "Feeling");

        //Associate Products with Supplements
        ProductSupplementAssociation.add("BGLO",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
        ProductSupplementAssociation.add("BGLP",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
        ProductSupplementAssociation.add("BGLE",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
        ProductSupplementAssociation.add("BGLS",
                "FILB", "FILE", "FILC", "FILX", "FILS", "FILH");
    }
}
