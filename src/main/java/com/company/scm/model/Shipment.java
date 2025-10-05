package com.company.scm.model;

import java.time.LocalDateTime;

public class Shipment {
    public enum Status { IN_TRANSIT, DELIVERED }

    private String shipmentId;
    private String orderId;
    private String originWarehouseId;
    private String destination;
    private LocalDateTime shipmentDate;
    private Status status;

    public Shipment(String shipmentId, String orderId, String originWarehouseId, String destination, LocalDateTime shipmentDate, Status status) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.originWarehouseId = originWarehouseId;
        this.destination = destination;
        this.shipmentDate = shipmentDate;
        this.status = status;
    }

    public String getShipmentId() { return shipmentId; }
    public String getOrderId() { return orderId; }
    public String getOriginWarehouseId() { return originWarehouseId; }
    public String getDestination() { return destination; }
    public LocalDateTime getShipmentDate() { return shipmentDate; }
    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }
}