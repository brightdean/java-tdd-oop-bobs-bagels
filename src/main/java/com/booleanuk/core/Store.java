package com.booleanuk.core;

import java.util.HashMap;
import java.util.Map;

public class Store {
    static Map<String, Item> stock;
    static Map<String, OfferItem> offers;

    static final String ONION_BAGEL_DISCOUNT_CODE = "BGLO";
    static final String EVERYTHING_BAGEL_DISCOUNT_CODE = "BGLE";
    static final String PLAIN_BAGEL_DISCOUNT_CODE = "BGLP";

    static {
        stock = new HashMap<>();

        offers = new HashMap<>();

        // Bagels
        stock.put("BGLO", new Bagel("BGLO", 0.49, "Onion") {
        });
        stock.put("BGLP", new Bagel("BGLP", 0.39, "Plain"));
        stock.put("BGLE", new Bagel("BGLE", 0.49, "Everything"));
        stock.put("BGLS", new Bagel("BGLS", 0.49, "Sesame"));

        // Coffees
        stock.put("COFB", new Coffee("COFB", 0.99, "Black"));
        stock.put("COFW", new Coffee("COFW", 1.19, "White"));
        stock.put("COFC", new Coffee("COFC", 1.29, "Cappuccino"));
        stock.put("COFL", new Coffee("COFL", 1.29, "Latte"));

        // Fillings
        stock.put("FILB", new Filling("FILB", 0.12, "Bacon"));
        stock.put("FILE", new Filling("FILE", 0.12, "Egg"));
        stock.put("FILC", new Filling("FILC", 0.12, "Cheese"));
        stock.put("FILX", new Filling("FILX", 0.12, "Cream Cheese"));
        stock.put("FILS", new Filling("FILS", 0.12, "Smoked Salmon"));
        stock.put("FILH", new Filling("FILH", 0.12, "Ham"));

        offers.put(PLAIN_BAGEL_DISCOUNT_CODE, new OfferItem(12, "12 Plain Bagels Disc", 3.99, 0, PLAIN_BAGEL_DISCOUNT_CODE));
        offers.put(ONION_BAGEL_DISCOUNT_CODE, new OfferItem(6, "6 Onion Bagels Disc", 2.49, 0, ONION_BAGEL_DISCOUNT_CODE));
        offers.put(EVERYTHING_BAGEL_DISCOUNT_CODE, new OfferItem(6, "6 Everything Bagels Disc", 2.49, 0, EVERYTHING_BAGEL_DISCOUNT_CODE));

    }
/*


    public static void applySingleDiscounts(Map<String, ProductGroup> products){
        for(String key : products.keySet()){
            String sku = key.split("-")[0];
            if(sku.equals("BGLO") || sku.equals("BGLE") || sku.equals("BGLP")){
                applySingleDiscount(key, products);
            }
        }
    }

    public static void applySingleDiscount(String discountKey, Map<String, ProductGroup> products){

        if(products.containsKey(discountKey)){
            int productCount = products.get(discountKey).getProducts().size();
            //Discount discount = discounts.get(discountKey);
            Discount discount = discounts.get(discountKey.split("-")[0]);
            int discNum = productCount / discount.getBatches();
            if(discNum >= 1){
                double discountPrice = discNum * discount.getPrice();
                //double groupTotal = discountPrice;
                int i = 0;
                for(String key : products.get(discountKey).getProducts().keySet()){
                    if(i < discount.getBatches() * discNum){
                        products.get(discountKey).getProducts().get(key).setPrice(discount.getPlaceHolderPrice());
                        products.get(discountKey).getProducts().get(key).setUnderDiscount(true);

                    }
                    //groupTotal += products.get(discountKey).getProducts().get(key).getPrice();
                    i++;
                }
                //products.get(discountKey).setAfterCost(groupTotal);
                products.get(discountKey).setDiscount(discountPrice);
            }
        }
    }

    public static void applyComboDiscount(String SKU1, String ofSKU, double discountPrice2, Map<String, ProductGroup> products){
        //find product1 occurrences
        int p1 = 0;
        for(String key : products.keySet()){
            if(key.split("-")[0].equals(SKU1))
                p1 = products.get(key).getProducts().size();
        }

        int bagelsFound = 0;

        while (p1 > 0){
            for(String key : products.keySet()){
                boolean found = false;
                if(key.startsWith(ofSKU)){
                    for(String id : products.get(key).getProducts().keySet()){
                        if(!products.get(key).getProducts().get(id).isUnderDiscount()){
                            products.get(key).getProducts().get(id).setUnderDiscount(true);

                            products.get(key).getProducts().get(id).setPrice(discountPrice2);

                            found = true;
                            bagelsFound++;
                            break;
                        }
                    }
                }
                if(found) break;
            }
            p1--;
        }
        for(String key : products.keySet()){
            if(key.split("-")[0].equals(SKU1)){
                for (String id : products.get(key).getProducts().keySet()){
                    if(bagelsFound > 0) products.get(key).getProducts().get(id).setUnderDiscount(true);
                    bagelsFound--;
                }
            }
        }

    }
*/


}
