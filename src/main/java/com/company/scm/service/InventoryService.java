package com.company.scm.service;

import com.company.scm.exception.InsufficientStockException;
import com.company.scm.exception.InvalidQuantityException;
import com.company.scm.exception.EntityNotFoundException;
import com.company.scm.model.InventoryItem;
import com.company.scm.model.Product;
import com.company.scm.repository.InventoryRepository;
import com.company.scm.repository.ProductRepository;

import java.time.LocalDateTime;

public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
    }

    public void addStock(String productId, String warehouseId, int quantity) throws InvalidQuantityException, EntityNotFoundException {
        if (quantity <= 0) throw new InvalidQuantityException("Quantity must be positive");
        if (!productRepository.exists(productId)) throw new EntityNotFoundException("Product not found: " + productId);

        InventoryItem item = inventoryRepository.find(productId, warehouseId);
        if (item == null) {
            item = new InventoryItem(productId + ":" + warehouseId, productId, warehouseId, quantity, LocalDateTime.now());
        } else {
            item.setQuantity(item.getQuantity() + quantity);
            item.setLastRestockedDate(LocalDateTime.now());
        }
        inventoryRepository.save(item);
    }

    public void removeStock(String productId, String warehouseId, int quantity) throws InvalidQuantityException, InsufficientStockException, EntityNotFoundException {
        if (quantity <= 0) throw new InvalidQuantityException("Quantity must be positive");
        InventoryItem item = inventoryRepository.find(productId, warehouseId);
        if (item == null) throw new EntityNotFoundException("Inventory item not found for product: " + productId + " in warehouse: " + warehouseId);

        if (item.getQuantity() < quantity) throw new InsufficientStockException("Insufficient stock for product: " + productId);
        item.setQuantity(item.getQuantity() - quantity);
        inventoryRepository.save(item);
    }

    public int getStockLevel(String productId, String warehouseId) {
        InventoryItem item = inventoryRepository.find(productId, warehouseId);
        return item != null ? item.getQuantity() : 0;
    }

    public void transferStock(String productId, String fromWarehouseId, String toWarehouseId, int quantity) throws InsufficientStockException, InvalidQuantityException, EntityNotFoundException {
        if (quantity <= 0) throw new InvalidQuantityException("Quantity must be positive");
        removeStock(productId, fromWarehouseId, quantity);
        addStock(productId, toWarehouseId, quantity);
    }
}