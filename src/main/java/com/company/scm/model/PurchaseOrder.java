package com.company.scm.model;

import java.time.LocalDateTime;

public class PurchaseOrder {
    public enum Status { PENDING, DELIVERED, CANCELLED }

    private String orderId;
    private String supplierId;
    private String productId;
    private int quantity;
    private LocalDateTime orderDate;
    private Status status;

    public PurchaseOrder(String orderId, String supplierId, String productId, int quantity, LocalDateTime orderDate, Status status) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getSupplierId() { return supplierId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}