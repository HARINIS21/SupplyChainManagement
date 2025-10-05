package com.company.scm.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SalesOrder {
    public enum Status { PROCESSING, FULFILLED, CANCELLED }

    private String orderId;
    private String productId;
    private int quantity;
    private LocalDateTime orderDate;
    private Status status;
    private String customerDetails;

    // Track warehouse allocations for this order
    private Map<String, Integer> warehouseAllocations = new HashMap<>();

    public SalesOrder(String orderId, String productId, int quantity, LocalDateTime orderDate, Status status, String customerDetails) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
        this.customerDetails = customerDetails;
    }

    // Getters and setters
    public String getOrderId() { return orderId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Status getStatus() { return status; }
    public String getCustomerDetails() { return customerDetails; }
    public void setStatus(Status status) { this.status = status; }

    // New: allocation methods
    public void addAllocation(String warehouseId, int quantity) {
        warehouseAllocations.put(warehouseId, warehouseAllocations.getOrDefault(warehouseId, 0) + quantity);
    }

    public Map<String, Integer> getWarehouseAllocations() {
        return warehouseAllocations;
    }
}