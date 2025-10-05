package com.company.scm.repository;

import com.company.scm.model.Warehouse;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class WarehouseRepository {
    private final Map<String, Warehouse> warehouses = new HashMap<>();
    public void save(Warehouse warehouse) {
        warehouses.put(warehouse.getWarehouseId(), warehouse);
    }
    public Warehouse findById(String warehouseId) {
        return warehouses.get(warehouseId);
    }
    public boolean exists(String warehouseId) {
        return warehouses.containsKey(warehouseId);
    }

    public Map<String, Warehouse> findAll() { return Collections.unmodifiableMap(warehouses); }
    public void clearAll() { warehouses.clear(); }
    }

