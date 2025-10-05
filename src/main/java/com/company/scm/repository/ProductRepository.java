package com.company.scm.repository;

import com.company.scm.model.Product;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public void save(Product product) {
        products.put(product.getProductId(), product);
    }

    public Product findById(String productId) {
        return products.get(productId);
    }

    public boolean exists(String productId) {
        return products.containsKey(productId);
    }

    public Map<String, Product> findAll() {
        return Collections.unmodifiableMap(products);
    }

    public void clearAll() {
        products.clear();
    }
}