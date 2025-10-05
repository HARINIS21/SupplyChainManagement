package com.company.scm.repository;

import com.company.scm.model.SalesOrder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SalesOrderRepository {
    private final Map<String, SalesOrder> salesOrders = new HashMap<>();

    public void save(SalesOrder order) {
        salesOrders.put(order.getOrderId(), order);
    }

    public SalesOrder findById(String orderId) {
        return salesOrders.get(orderId);
    }


    public Map<String, SalesOrder> findAll() { return Collections.unmodifiableMap(salesOrders); }
    public void clearAll() { salesOrders.clear(); }

}