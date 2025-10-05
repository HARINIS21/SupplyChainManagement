package com.company.scm.service;

import com.company.scm.model.InventoryItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportService {

    private final InventoryService inventoryService;

    public ReportService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public List<String> generateLowStockReport(int threshold, Map<String, InventoryItem> inventory) {
        List<String> lowStockProducts = new ArrayList<>();
        for (InventoryItem item : inventory.values()) {
            if (item.getQuantity() <= threshold) {
                lowStockProducts.add(item.getProductId() + " in " + item.getWarehouseId() + " has stock: " + item.getQuantity());
            }
        }
        return lowStockProducts;
    }
}
