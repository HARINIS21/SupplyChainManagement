
package com.company.scm.model;

import java.time.LocalDateTime;

public class InventoryItem {
    private String itemId;
    private String productId;
    private String warehouseId;
    private int quantity;
    private LocalDateTime lastRestockedDate;

    public InventoryItem(String itemId, String productId, String warehouseId, int quantity, LocalDateTime lastRestockedDate) {
        this.itemId = itemId;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.lastRestockedDate = lastRestockedDate;
    }

    public String getItemId() { return itemId; }
    public String getProductId() { return productId; }
    public String getWarehouseId() { return warehouseId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getLastRestockedDate() { return lastRestockedDate; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setLastRestockedDate(LocalDateTime lastRestockedDate) { this.lastRestockedDate = lastRestockedDate; }
}

