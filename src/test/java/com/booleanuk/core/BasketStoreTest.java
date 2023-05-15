package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.booleanuk.core.Store.stock;

public class BasketStoreTest {
    private Basket basket;

    @BeforeEach
    public void setup() {
        int initialCapacity = 10;
        basket = new Basket(initialCapacity);
    }

    @Test
    public void testBasketManagerInitialState() {
        Assertions.assertTrue(basket.getProducts().isEmpty());
        Assertions.assertFalse(stock.isEmpty());
    }

    @Test
    public void testAddBagel() {

        Assertions.assertTrue(basket.add("BGLP"));
        Assertions.assertTrue(basket.add("BGLP"));
        Assertions.assertEquals("Plain", basket.getProducts().get(1).getVariant());

        Assertions.assertNotEquals(basket.getProducts().get(1), basket.getProducts().get(0));

        Assertions.assertFalse(basket.add("BGLCOA"));
    }

    @Test
    public void testRemoveBagel() {

        Assertions.assertFalse(basket.remove("Product id not in Basket"));

        basket.add("BGLO");
        String id = basket.getProducts().get(0).getId();

        Assertions.assertFalse(basket.remove("Product id not in Basket"));

        Assertions.assertTrue(basket.remove(id));
        Assertions.assertTrue(basket.getProducts().isEmpty());

    }

    @Test
    public void testBasketCapacity() {
        basket.setCapacity(2);
        basket.add("BGLO");
        basket.add("BGLP");

        Assertions.assertFalse(basket.add("BGLO"));
        Assertions.assertEquals(2, basket.getProducts().size());

    }

    @Test
    public void testExpandBasketCapacity() {
        basket.setCapacity(2);
        Assertions.assertTrue(basket.expandBasket(4));
        Assertions.assertFalse(basket.expandBasket(3));
    }

    @Test
    public void testGetProductsTotalPrice() {
        basket.add("BGLO"); // 0.49
        basket.add("BGLP"); // 0.39

        Assertions.assertEquals(0.88, basket.totalPrice());
    }

    @Test
    public void testGetProductPrice() {
        //Check bagel price
        Assertions.assertEquals(0.49, basket.getPrice("BGLO"));
        Assertions.assertEquals(0.39, basket.getPrice("BGLP"));

        //Check price - item doesn't exist
        Assertions.assertEquals(-1, basket.getPrice("BGLCAS"));

        //Check filling price
        Assertions.assertEquals(0.12, basket.getPrice("FILB"));
    }

    @Test
    public void testAddFillingsToBagel() {
        basket.add("BGLO", "FILB");
        basket.add("BGLP", "FILB", "FILE");

        Bagel onionBagel = (Bagel) basket.getProducts().get(0);
        Bagel plainBagel = (Bagel) basket.getProducts().get(1);

        Assertions.assertEquals(1, onionBagel.getFillings().size());
        Assertions.assertEquals("Bacon", onionBagel.getFillings().get(0).getVariant());
        Assertions.assertEquals(2, plainBagel.getFillings().size());

        //Test fillings are different objects
        Assertions.assertNotEquals(onionBagel.getFillings().get(0), plainBagel.getFillings().get(0));
    }
}
