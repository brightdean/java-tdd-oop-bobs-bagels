package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasketManagerTest {


    private BasketManager basketManager;
    @BeforeEach
    public void setup(){
        basketManager = new BasketManager();
    }

    @Test
    public void testBasketManagerInitialState(){
        Assertions.assertTrue(basketManager.getBasket().isEmpty());
        Assertions.assertFalse(BasketManager.stock.isEmpty());
    }

    @Test
    public void testAddBagel(){

        Assertions.assertTrue(basketManager.add("Bagel", "Plain"));
        Assertions.assertTrue(basketManager.add("Bagel", "Plain"));
        Assertions.assertEquals("Plain", basketManager.getBasket().get(1).getVariant());
        System.out.println(basketManager.getBasket().get(1) == basketManager.getBasket().get(0));
        Assertions.assertFalse(basketManager.add("Bagel", "Capuccino"));

    }
}
