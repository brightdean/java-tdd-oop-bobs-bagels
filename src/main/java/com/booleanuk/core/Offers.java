package com.booleanuk.core;

import com.booleanuk.core.model.OfferItem;

import java.util.*;

//TODO: maybe can merge Inventory and Offer Classes to 1 Class with responsibility to save state
public class Offers {
    private static Map<String, List<OfferItem>> offerItems;

    static {
        offerItems = new LinkedHashMap<>();
    }

    public static void createOfferItem(String name, String sku, int quantity, double price, int priority) {
        OfferItem item = new OfferItem(name, sku, quantity, price, priority);
        if (!offerItems.containsKey(name)) {
            offerItems.put(name, new ArrayList<>());
        }
        offerItems.get(name).add(item);
        orderHashMapEntries();
    }

    public static Map<String, List<OfferItem>> getOfferItems() {
        return offerItems;
    }

    private static void orderHashMapEntries() {
        // Create a list of entries, sort the list based on the Item's priority value
        List<Map.Entry<String, List<OfferItem>>> entryList = new ArrayList<>(offerItems.entrySet());
        entryList.sort(Comparator.comparingInt(entry -> entry.getValue().get(0).getPriority()));
        // Update my HashMap
        offerItems.clear();
        for (Map.Entry<String, List<OfferItem>> entry : entryList) {
            offerItems.put(entry.getKey(), entry.getValue());
        }
    }
}
