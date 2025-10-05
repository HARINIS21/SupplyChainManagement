package com.company.scm.repository;

import com.company.scm.model.InventoryItem;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class InventoryRepository {
    private final Map<String, InventoryItem> inventory = new HashMap<>();

    private String buildKey(String productId, String warehouseId) {
        return productId + ":" + warehouseId;
    }

    public void save(InventoryItem item) {
        inventory.put(buildKey(item.getProductId(), item.getWarehouseId()), item);
    }

    public InventoryItem find(String productId, String warehouseId) {
        return inventory.get(buildKey(productId, warehouseId));
    }

    public boolean exists(String productId, String warehouseId) {
        return inventory.containsKey(buildKey(productId, warehouseId));
    }

    // Return an unmodifiable view to avoid accidental external mutation
    public Map<String, InventoryItem> findAll() {
        return Collections.unmodifiableMap(inventory);
    }

    // Helper for tests / cleanup
    public void clearAll() {
        inventory.clear();
    }
}