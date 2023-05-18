package com.booleanuk.core;

import com.booleanuk.core.model.Product;
import org.junit.jupiter.api.*;

public class BasketTest {
    private Basket basket;

    @BeforeAll
    public static void globalSetup() {
        Main.createDemoData();
    }

    @BeforeEach
    public void setup() {
        int initialCapacity = 100;
        basket = new Basket(initialCapacity);
    }

    @Test
    public void basketInitialState() {
        Assertions.assertTrue(basket.getProducts().isEmpty());
        Assertions.assertEquals(15, Inventory.getInstance().size());
    }

    @Test
    public void addProductToBasket() {
        Assertions.assertTrue(basket.add("BGLP"));
        Assertions.assertEquals("BGLP", basket.getProducts().get(0).getSku());
    }

    @Test
    public void addProductToBasket_ThatDoesNotExist() {
        Assertions.assertFalse(basket.add("BGLPA"));
        Assertions.assertTrue(basket.getProducts().isEmpty());
    }

    @Test
    public void removeProductFromBasket() {
        basket.add("BGLO");
        Assertions.assertEquals(1, basket.getProducts().size());

        String id = basket.getProducts().get(0).getId();
        basket.remove(id);
        Assertions.assertTrue(basket.getProducts().isEmpty());
    }

    @Test
    public void removeProductFromBasket_IdDoesNotExist() {
        basket.add("BGLO");
        Assertions.assertEquals(1, basket.getProducts().size());

        String id = basket.getProducts().get(0).getId() + "1";
        basket.remove(id);
        Assertions.assertFalse(basket.getProducts().isEmpty());
    }

    @Test
    public void basketCapacityExceeded() {
        Inventory inventory = Inventory.getInstance();
        int initialCapacity = 100;
        basket = new Basket(2);

        Assertions.assertTrue(basket.add("BGLP"));
        Assertions.assertTrue(basket.add("BGLP"));
        Assertions.assertFalse(basket.add("BGLP"));
    }

    @Test
    @DisplayName("Change basket capacity, only if new capacity is larger")
    public void expandBasket() {
        int initialCapacity = basket.getCapacity();

        int largerCapacity = initialCapacity + 1;
        int smallerCapacity = initialCapacity - 1;
        // Try with smaller capacity
        basket.expandBasket(smallerCapacity);
        Assertions.assertEquals(initialCapacity, basket.getCapacity());
        // Try with larger capacity
        basket.expandBasket(largerCapacity);
        Assertions.assertEquals(largerCapacity, basket.getCapacity());
    }

    @Test
    public void totalCostOfProductsInBasket() {
        basket.add("BGLO"); // 0.49
        basket.add("BGLO"); // 0.49
        basket.add("BGLP"); // 0.39

        double price = Math.floor((0.49 + 0.49 + 0.39) * 100) / 100;
        Assertions.assertEquals(price, basket.totalPrice());
    }

    @Test
    public void totalCostOfProductsInBasket_EmptyBasket() {
        Assertions.assertEquals(0, basket.totalPrice());
    }

    @Test
    @DisplayName("Check an items price before adding it to the basket")
    public void peekProductPrice() {
        Assertions.assertEquals(0.39, Inventory.getInstance().getPrice("BGLP"));
        Assertions.assertEquals(0.12, Inventory.getInstance().getPrice("FILE"));
        Assertions.assertEquals(-1, Inventory.getInstance().getPrice("BGLPASD"));
    }

    @Test
    public void addFillingsToBagel() {
        basket.add("BGLP", "FILE", "FILB");
        Product bagel = basket.getProducts().get(0);
        Assertions.assertEquals(2, bagel.getSupplements().size());
    }

    @Test
    public void getPriceWithSupplements() {
        basket.add("BGLP", "FILE", "FILB");

        double price = Math.floor((0.39 + 0.12 + 0.12) * 100) / 100;
        Assertions.assertEquals(price, basket.totalPrice());
    }

    @Test
    public void getPriceWithProductDiscount() {
        for (int i = 0; i < 13; i++) {
            basket.add("BGLP");
        }
        double expected = Math.floor((3.99 + 0.39) * 100) / 100;
        Assertions.assertEquals(expected, basket.totalPrice());
    }

    @Test
    public void getPriceWithProductDiscount_Complex() {
        for (int i = 0; i < 25; i++) {
            basket.add("BGLP");
            basket.add("BGLO");
        }
        double expected = Math.floor((2*3.99 + 0.39 + 4*2.49 + 0.49) * 100) / 100;
        Assertions.assertEquals(expected, basket.totalPrice());
    }
}
