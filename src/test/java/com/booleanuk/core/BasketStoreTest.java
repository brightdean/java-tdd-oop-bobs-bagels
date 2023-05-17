package com.booleanuk.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.booleanuk.core.Store.stock;

public class BasketStoreTest {
    private Basket basket;

    @BeforeEach
    public void setup() {
        int initialCapacity = 100;
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

    @Test
    public void test12BGLPDiscountTotal() {
        for(int i = 0; i < 13; i++){
            basket.add("BGLP");
        }

        Assertions.assertEquals(13, basket.getProducts().size());
        Assertions.assertEquals(4.38, basket.totalPrice());
    }

    @Test
    public void test12BGLODiscountTotal() {
        for(int i = 0; i < 13; i++){
            basket.add("BGLO");
        }

        Assertions.assertEquals(13, basket.getProducts().size());
        Assertions.assertEquals(2.49+2.49+0.49, basket.totalPrice());
    }

    @Test
    public void test12BGLO3COFBDiscountTotal() {
        for(int i = 0; i < 13; i++){
            basket.add("BGLO");
        }
        basket.add("COFB");
        basket.add("COFB");
        basket.add("COFB");
        Assertions.assertEquals(16, basket.getProducts().size());
        Assertions.assertEquals(2.49+2.49+1.25+0.99+0.99, basket.totalPrice());
    }

    @Test
    public void testComplexDiscountTotal() {
        for(int i = 0; i < 12; i++){
            basket.add("BGLO");
            basket.add("BGLP");
            basket.add("BGLP");
        }
        basket.add("COFB");
        basket.add("COFB");
        basket.add("BGLE");
        basket.add("BGLS");

        double expected = 2.49+2.49+3.99+3.99+1.25+1.25;

        Assertions.assertEquals(expected, basket.totalPrice());
    }

    @Test
    public void testComplexDiscountTotalWithFillings() {
        for(int i = 0; i < 12; i++){
            if(i == 0)
                basket.add("BGLO", "FILB", "FILE");
            else if(i == 1)
                basket.add("BGLO", "FILC", "FILB");
            else
                basket.add("BGLO");
            basket.add("BGLP");
            basket.add("BGLP");
        }
        basket.add("COFB");
        basket.add("COFB");
        basket.add("BGLE");
        basket.add("BGLS");


        double expected = 2.49+2.49+3.99+3.99+1.25+1.25+(4*0.12);

        Assertions.assertEquals((double)Math.round(expected*100)/100, basket.totalPrice());
    }

    @Test
    public void testCreateReceipt(){
        for(int i = 0; i < 12; i++){
            if(i == 0)
                basket.add("BGLO", "FILB", "FILE");
            else if(i == 1)
                basket.add("BGLO", "FILC", "FILB");
            else
                basket.add("BGLO");
            basket.add("BGLP");
            basket.add("BGLP");
        }
        basket.add("COFB");
        basket.add("COFB");
        basket.add("BGLE");
        basket.add("BGLS");


        basket.createReceipt();

    }

    @Test
    public void testProductGroup(){

        for(int i = 0; i < 17; i++){
            if(i < 2){
                basket.add("BGLP", "FILB", "FILE");
                basket.add("COFC");
            }

            else if(i < 16){
                if(i > 14)
                    basket.add("BGLS", "FILB");
                else
                    basket.add("BGLP");
            }

            else{
                basket.add("BGLP", "FILE");
                basket.add("COFB");
            }

        }
        basket.createReceipt2();

    }

    @Test
    public void testDiscounts(){

        for(int i = 0; i < 12; i++){
            if(i < 6){
                basket.add("BGLP", "FILE");
                basket.add("BGLO", "FILE", "FILC");
            }

            else
                basket.add("BGLP", "FILE", "FILB");

        }
        basket.add("BGLO", "FILE");basket.add("BGLO", "FILE");basket.add("BGLO", "FILE", "FILB");

        basket.add("COFB");
        basket.add("COFC");


        basket.createReceipt2();
    }
}
