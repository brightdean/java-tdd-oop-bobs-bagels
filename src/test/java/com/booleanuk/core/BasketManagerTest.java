package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasketManagerTest {
    private BasketManager basketManager;

    @BeforeEach
    public void setup() {
        basketManager = new BasketManager(10);
    }

    @Test
    public void testBasketManagerInitialState() {
        Assertions.assertTrue(basketManager.getBasket().isEmpty());
        Assertions.assertFalse(BasketManager.stock.isEmpty());
    }

    @Test
    public void testAddBagel() {

        Assertions.assertTrue(basketManager.add("Bagel", "Plain"));
        Assertions.assertTrue(basketManager.add("Bagel", "Plain"));
        Assertions.assertEquals("Plain", basketManager.getBasket().get(1).getVariant());
        System.out.println(basketManager.getBasket().get(1) == basketManager.getBasket().get(0));
        Assertions.assertFalse(basketManager.add("Bagel", "Capuccino"));

    }

    @Test
    public void testRemoveBagel() {

        Assertions.assertFalse(basketManager.remove("Product id not in Basket"));

        basketManager.add("Bagel", "Onion");
        String id = basketManager.getBasket().get(0).getId();

        Assertions.assertFalse(basketManager.remove("Product id not in Basket"));

        Assertions.assertTrue(basketManager.remove(id));
        Assertions.assertTrue(basketManager.getBasket().isEmpty());

    }

    @Test
    public void testBasketCapacity() {
        basketManager.setCapacity(2);
        basketManager.add("Bagel", "Onion");
        basketManager.add("Bagel", "Plain");

        Assertions.assertFalse(basketManager.add("Bagel", "Onion"));
        Assertions.assertEquals(2, basketManager.getBasket().size());

    }

    @Test
    public void testExpandBasketCapacity() {
        basketManager.setCapacity(2);
        Assertions.assertTrue(basketManager.expandBasket(4));
        Assertions.assertFalse(basketManager.expandBasket(3));
    }

    @Test
    public void testGetProductsTotalPrice() {
        basketManager.add("Bagel", "Onion"); // 0.49
        basketManager.add("Bagel", "Plain"); // 0.39

        Assertions.assertEquals(0.88, basketManager.totalPrice());
    }

    @Test
    public void testGetProductPrice() {
        //Check bagel price
        Assertions.assertEquals(0.49, basketManager.getPrice("Bagel", "Onion"));
        Assertions.assertEquals(0.39, basketManager.getPrice("Bagel", "Plain"));

        //Check price - item doesn't exist
        Assertions.assertEquals(-1, basketManager.getPrice("Bagel", "Cheetos"));

        //Check filling price
        Assertions.assertEquals(0.12, basketManager.getPrice("Filling", "Bacon"));
    }

}
