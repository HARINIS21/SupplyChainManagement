package com.company.scm.service;

import com.company.scm.exception.*;
import com.company.scm.model.SalesOrder;
import com.company.scm.model.SalesOrder.Status;
import com.company.scm.model.Shipment;
import com.company.scm.model.InventoryItem;
import com.company.scm.repository.SalesOrderRepository;
import com.company.scm.repository.InventoryRepository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class OrderService {

    private final InventoryService inventoryService;
    private final SalesOrderRepository salesOrderRepository;
    private final InventoryRepository inventoryRepository;

    public OrderService(InventoryService inventoryService, SalesOrderRepository salesOrderRepository, InventoryRepository inventoryRepository) {
        this.inventoryService = inventoryService;
        this.salesOrderRepository = salesOrderRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public SalesOrder createSalesOrder(String productId, int quantity, String customerDetails)
            throws InsufficientStockException, InvalidQuantityException, EntityNotFoundException {

        if (quantity <= 0) {
            throw new InvalidQuantityException("Quantity must be greater than zero.");
        }

        // Calculate total stock across all warehouses
        int totalAvailable = inventoryRepository.findAll().values().stream()
                .filter(i -> i.getProductId().equals(productId))
                .mapToInt(InventoryItem::getQuantity)
                .sum();

        if (totalAvailable < quantity) {
            throw new InsufficientStockException("Not enough stock for product: " + productId);
        }

        SalesOrder order = new SalesOrder(
                UUID.randomUUID().toString(),
                productId,
                quantity,
                LocalDateTime.now(),
                Status.PROCESSING,
                customerDetails
        );

        int remainingQty = quantity;

        // Allocate stock from warehouses
        for (InventoryItem item : inventoryRepository.findAll().values()) {
            if (!item.getProductId().equals(productId)) continue;
            if (item.getQuantity() <= 0) continue;

            int qtyToAllocate = Math.min(item.getQuantity(), remainingQty);
            inventoryService.removeStock(productId, item.getWarehouseId(), qtyToAllocate);
            order.addAllocation(item.getWarehouseId(), qtyToAllocate);

            remainingQty -= qtyToAllocate;
            if (remainingQty == 0) break;
        }

        salesOrderRepository.save(order);
        return order;
    }

    public Shipment fulfillSalesOrder(String orderId) throws EntityNotFoundException {
        SalesOrder order = salesOrderRepository.findById(orderId);
        if (order == null) {
            throw new EntityNotFoundException("Sales order not found: " + orderId);
        }

        if (order.getStatus() != Status.PROCESSING) {
            throw new IllegalStateException("Order cannot be fulfilled in its current state: " + order.getStatus());
        }

        // Update order status
        order.setStatus(Status.FULFILLED);
        salesOrderRepository.save(order);

        Shipment shipment = new Shipment(
                UUID.randomUUID().toString(),
                orderId,
                "Multiple", // because it can come from multiple warehouses
                order.getCustomerDetails(),
                LocalDateTime.now(),
                Shipment.Status.IN_TRANSIT
        );

        return shipment;
    }
}