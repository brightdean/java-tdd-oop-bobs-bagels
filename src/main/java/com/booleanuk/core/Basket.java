package com.booleanuk.core;

import java.util.*;

import static com.booleanuk.core.Store.stock;

public class Basket {
    private List<Product> products;

    private int capacity;

    public Basket(int capacity) {
        this.capacity = capacity;
        this.products = new ArrayList<>();

    }

    public List<Product> getProducts() {
        return products;
    }


    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean remove(String id) {
        Product productToRemove = getProductFromBasketById(id);
        boolean result = products.remove(productToRemove);

        System.out.println(result ? "Product removed from basket" : "Product not in basket");
        return result;
    }

    private Product getProductFromBasketById(String id) {
        return this.products.stream()
                .filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean expandBasket(int capacity) {
        if (capacity > this.capacity) {
            this.capacity = capacity;
            return true;
        }
        return false;
    }
    public void createReceipt(){
        Map<String, ReceiptItem> receiptItems = new HashMap<>();
        Map<String, Integer> quantities = new HashMap<>();
        double total = 0.0;
        //group products by their SKU and how many times they appear inside the basket
        for (Product product : products) {
            String key = product.getSKU();
            if (quantities.containsKey(key)) {
                quantities.replace(key, quantities.get(key) + 1);
            } else
                quantities.put(key, 1);

            if(key.startsWith("BGL")){
                Bagel bagel = product.getItem();
                for(Filling f : bagel.getFillings()){

                    String fKey = f.getSKU();
                    if (quantities.containsKey(fKey)) {
                        quantities.replace(fKey, quantities.get(fKey) + 1);
                    } else
                        quantities.put(fKey, 1);
                }

            }
        }
        for(String key : quantities.keySet()){
            String displayName = stock.get(key).getVariant() + " " + stock.get(key).getName();
            receiptItems.put(key, new ReceiptItem(displayName, quantities.get(key)));
        }
        if (quantities.containsKey("BGLP")) {
            int special_12_BGLP = quantities.get("BGLP") / 12;
            if (special_12_BGLP > 0) {
                quantities.replace("BGLP", quantities.get("BGLP") - 12 * special_12_BGLP);
                if (quantities.get("BGLP") <= 0) quantities.remove("BGLP");
                total += 3.99 * special_12_BGLP;
                receiptItems.get("BGLP").updatePrice(3.99 * special_12_BGLP);
            }
        }

        if (quantities.containsKey("BGLO")) {
            int special_12_BGLO = quantities.get("BGLO") / 6;
            if (special_12_BGLO > 0) {
                quantities.replace("BGLO", quantities.get("BGLO") - 6 * special_12_BGLO);
                if (quantities.get("BGLO") <= 0) quantities.remove("BGLO");
                total += 2.49 * special_12_BGLO;
                receiptItems.get("BGLO").updatePrice(2.49 * special_12_BGLO);
            }
        }

        if (quantities.containsKey("BGLE")) {
            int special_12_BGLE = quantities.get("BGLE") / 6;
            if (special_12_BGLE > 0) {
                quantities.replace("BGLE", quantities.get("BGLE") - 6 * special_12_BGLE);
                if (quantities.get("BGLE") <= 0) quantities.remove("BGLE");
                total += 2.49 * special_12_BGLE;
                receiptItems.get("BGLE").updatePrice(2.49 * special_12_BGLE);
            }
        }

        if (quantities.containsKey("COFB")) {
            int count = quantities.get("COFB");
            while(count > 0){
                count--;
                for (String key : quantities.keySet()) {
                    if (key.startsWith("BGL")) {

                        quantities.replace(key, quantities.get(key) - 1);
                        if (quantities.get(key) <= 0) quantities.remove(key);
                        quantities.replace("COFB", quantities.get("COFB") - 1);
                        if (quantities.get("COFB") <= 0) quantities.remove("COFB");
                        total += 1.25;
                        receiptItems.get("COFB").updatePrice(0.99);
                        receiptItems.get(key).updatePrice(0.26); //1.25-0.99
                        break;
                    }
                }
            }

        }


        for (String key : quantities.keySet()) {
            total += stock.get(key).getPrice() * quantities.get(key);
            receiptItems.get(key).updatePrice(stock.get(key).getPrice() * quantities.get(key));
        }

        printReceipt(receiptItems);
    }

    public void printReceipt(Map<String, ReceiptItem> receiptItems){
        String content = "Bob's Bagels\n\n------------------------\n\n";
        for(String key : receiptItems.keySet()){
            content += receiptItems.get(key) + "\n";
        }

        System.out.println(content);

    }

    public double totalPrice() {
        Map<String, Integer> quantities = new HashMap<>();
        double total = 0.0;
        //group products by their SKU and how many times they appear inside the basket
        for (Product product : products) {
            String key = product.getSKU();
            if (quantities.containsKey(key)) {
                quantities.replace(key, quantities.get(key) + 1);
            } else
                quantities.put(key, 1);

            if(key.startsWith("BGL")){
                Bagel bagel = product.getItem();
                for(Filling f : bagel.getFillings()){

                    String fKey = f.getSKU();
                    if (quantities.containsKey(fKey)) {
                        quantities.replace(fKey, quantities.get(fKey) + 1);
                    } else
                        quantities.put(fKey, 1);
                }

            }
        }

        if (quantities.containsKey("BGLP")) {
            int special_12_BGLP = quantities.get("BGLP") / 12;
            if (special_12_BGLP > 0) {
                quantities.replace("BGLP", quantities.get("BGLP") - 12 * special_12_BGLP);
                if (quantities.get("BGLP") <= 0) quantities.remove("BGLP");
                total += 3.99 * special_12_BGLP;
            }
        }

        if (quantities.containsKey("BGLO")) {
            int special_12_BGLO = quantities.get("BGLO") / 6;
            if (special_12_BGLO > 0) {
                quantities.replace("BGLO", quantities.get("BGLO") - 6 * special_12_BGLO);
                if (quantities.get("BGLO") <= 0) quantities.remove("BGLO");
                total += 2.49 * special_12_BGLO;
            }
        }

        if (quantities.containsKey("BGLE")) {
            int special_12_BGLE = quantities.get("BGLE") / 6;
            if (special_12_BGLE > 0) {
                quantities.replace("BGLE", quantities.get("BGLE") - 6 * special_12_BGLE);
                if (quantities.get("BGLE") <= 0) quantities.remove("BGLE");
                total += 2.49 * special_12_BGLE;
            }
        }

        if (quantities.containsKey("COFB")) {
            int count = quantities.get("COFB");
            while(count > 0){
                count--;
                for (String key : quantities.keySet()) {
                    if (key.startsWith("BGL")) {

                        quantities.replace(key, quantities.get(key) - 1);
                        if (quantities.get(key) <= 0) quantities.remove(key);
                        quantities.replace("COFB", quantities.get("COFB") - 1);
                        if (quantities.get("COFB") <= 0) quantities.remove("COFB");
                        total += 1.25;

                        break;
                    }
                }
            }

        }


        for (String key : quantities.keySet()) {
            total += stock.get(key).getPrice() * quantities.get(key);
        }

        return (double)Math.round(total*100)/100;

    }

    public double getPrice(String sku) {
        if (stock.containsKey(sku)) {
            return stock.get(sku).getPrice();
        }
        return -1;
    }

    public boolean add(String sku, String... fillingSkus) {
        if (products.size() >= capacity) return false;

        Item item = stock.get(sku);
        if (item != null) {
            if (item.getName().equals("Bagel")) {
                Bagel newBagel = new Bagel(item.getItem());
                if (fillingSkus.length > 0) {
                    addFillings(newBagel, fillingSkus);
                }
                products.add(newBagel);
                return true;
            }
            if (item.getName().equals("Coffee")) {
                products.add(new Coffee(item.getItem()));
                return true;
            }
        }
        return false;
    }

    private void addFillings(Bagel bagel, String... fillingSkus) {
        for (String sku : fillingSkus) {
            if (!stock.containsKey(sku)) {
                continue;
            }
            if (stock.get(sku).getName().equals("Filling")) {
                bagel.addFilling(new Filling(stock.get(sku).getItem()));
            }
        }
    }
}
