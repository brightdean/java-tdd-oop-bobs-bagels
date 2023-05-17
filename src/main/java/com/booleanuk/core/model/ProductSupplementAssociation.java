package com.booleanuk.core.model;

import java.util.*;

public class ProductSupplementAssociation {
    private static Map<String, List<String>> associations;

    static {
        associations = new HashMap<>();
    }

    public static void add(String key, String ... supplementSkus) {
        associations.put(key, Arrays.asList(supplementSkus));
    }

    public static boolean hasSupplement(String productSku, String supplementSku) {
        return associations.get(productSku).contains(supplementSku);
    }
}
