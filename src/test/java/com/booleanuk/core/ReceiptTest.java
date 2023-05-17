package com.booleanuk.core;

import com.booleanuk.core.model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReceiptTest {

    private Basket basket;

    @BeforeEach
    public void setup() {
        Inventory inventory = Inventory.getInstance();
        int initialCapacity = 100;
        basket = new Basket(inventory, initialCapacity);
    }
    @Test
    public void receiptFormatting() {
        basket.add("BGLO");
        basket.add("BGLO");
        basket.add("BGLO");
        basket.add("BGLO", "FILE");
        basket.add("BGLO", "FILE");
        basket.add("BGLO", "FILE", "FILB");
        basket.add("COFB");
        Receipt receipt = new Receipt();
        receipt.print(basket.getProducts());
    }

    @Test
    public void discount12BGLP() {
        for (int i=0; i < 26; i++) {
            basket.add("BGLP");
        }
        basket.add("BGLO", "FILE");
        Receipt receipt = new Receipt();
        receipt.print(basket.getProducts());
    }

    @Test
    public void discount6BGL0() {
        for (int i=0; i < 7; i++) {
            basket.add("BGLO");
        }
        Receipt receipt = new Receipt();
        receipt.print(basket.getProducts());
    }

    @Test
    public void discountCoffeeAndPlainBagel() {
        basket.add("BGLP");
        basket.add("BGLP");
        basket.add("BGLP");
        basket.add("COFB");
        basket.add("COFB");
        Receipt receipt = new Receipt();
        receipt.print(basket.getProducts());
    }

    @Test
    public void discountComboOffer() {
        for(int i=0; i<13; i++) {
            basket.add("BGLP");
        }
        basket.add("COFB");
        basket.add("COFB");
        Receipt receipt = new Receipt();
        receipt.print(basket.getProducts());
    }
}
