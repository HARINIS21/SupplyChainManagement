package com.company.scm.repository;

import com.company.scm.model.PurchaseOrder;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class PurchaseOrderRepository {
    private final Map<String, PurchaseOrder> purchaseOrders = new HashMap<>();

    public void save(PurchaseOrder order) {
        purchaseOrders.put(order.getOrderId(), order);
    }

    public PurchaseOrder findById(String orderId) {
        return purchaseOrders.get(orderId);
    }

    // PurchaseOrderRepository
    public Map<String, PurchaseOrder> findAll() { return Collections.unmodifiableMap(purchaseOrders); }
    public void clearAll() { purchaseOrders.clear(); }

}