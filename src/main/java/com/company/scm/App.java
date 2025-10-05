package com.company.scm;

import com.company.scm.exception.*;
import com.company.scm.model.*;
import com.company.scm.model.SalesOrder.Status;
import com.company.scm.repository.*;
import com.company.scm.service.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize repositories
        ProductRepository productRepo = new ProductRepository();
        InventoryRepository inventoryRepo = new InventoryRepository();
        SalesOrderRepository salesOrderRepo = new SalesOrderRepository();
        PurchaseOrderRepository purchaseOrderRepository = new PurchaseOrderRepository();

        // Initialize services
        InventoryService inventoryService = new InventoryService(inventoryRepo, productRepo);
        OrderService orderService = new OrderService(inventoryService, salesOrderRepo, inventoryRepo);
        ReportService reportService = new ReportService(inventoryService);
        PurchaseOrderService purchaseOrderService =  new PurchaseOrderService(purchaseOrderRepository);

        // Add demo product and stock
        Product prod1 = new Product("P001", "Laptop", "Gaming Laptop", "Electronics", BigDecimal.valueOf(1500));
        productRepo.save(prod1);
        try {
            inventoryService.addStock("P001", "WH1", 10);
        } catch (Exception e) {
            System.out.println("Error initializing stock: " + e.getMessage());
        }

        System.out.println("=== Welcome to Supply Chain Management System Dashboard ===");

        while (true) {
            System.out.println("\nSelect your action, warehouse manager:");
            System.out.println("1. View Stock");
            System.out.println("2. Add Stock");
            System.out.println("3. Remove Stock");
            System.out.println("4. Transfer Stock");
            System.out.println("5. Create Sales Order");
            System.out.println("6. Fulfill Sales Order");
            System.out.println("7. Low Stock Report");
            System.out.println("8. Create Purchase Order");
            System.out.println("9. View All Inventory");
            System.out.println("0. Exit");

            int choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Warehouse ID to view stock: ");
                        String warehouseId = scanner.nextLine();

                        Map<String, InventoryItem> allInventory = inventoryRepo.findAll();
                        System.out.println("=== Inventory in Warehouse " + warehouseId + " ===");

                        boolean found = false;
                        for (InventoryItem item : allInventory.values()) {
                            if (item.getWarehouseId().equalsIgnoreCase(warehouseId)) {
                                System.out.println("Product: " + item.getProductId() +
                                        " | Qty: " + item.getQuantity());
                                found = true;
                            }
                        }

                        if (!found) {
                            System.out.println("No products found in this warehouse.");
                        }
                        break;
                    case 2:
                        System.out.print("Enter Warehouse ID to add stock: ");
                        String addWarehouseId = scanner.nextLine();
                        System.out.print("Enter quantity to add: ");
                        int qtyAdd = Integer.parseInt(scanner.nextLine());
                        inventoryService.addStock("P001", addWarehouseId, qtyAdd);
                        System.out.println("Hooray! " + qtyAdd + " units added successfully to " + addWarehouseId + ".");
                        break;
                    case 3:
                        System.out.print("Enter Warehouse ID to remove stock: ");
                        String removeWarehouseId = scanner.nextLine();
                        System.out.print("Enter quantity to remove: ");
                        int qtyRemove = Integer.parseInt(scanner.nextLine());
                        inventoryService.removeStock("P001", removeWarehouseId, qtyRemove);
                        System.out.println("Removed! " + qtyRemove + " units removed from " + removeWarehouseId + ".");
                        break;
                    case 4:
                        System.out.print("Enter Source Warehouse ID: ");
                        String sourceWarehouseId = scanner.nextLine();

                        System.out.print("Enter Destination Warehouse ID: ");
                        String destWarehouseId = scanner.nextLine();

                        if (sourceWarehouseId.equalsIgnoreCase(destWarehouseId)) {
                            System.out.println("Error: Source and destination warehouse cannot be the same!");
                            break; // Skip transfer
                        }

                        System.out.print("Enter quantity to transfer: ");
                        int qtyTransfer = Integer.parseInt(scanner.nextLine());

                        try {
                            inventoryService.transferStock("P001", sourceWarehouseId, destWarehouseId, qtyTransfer);
                            System.out.println("Stock transferred successfully from " + sourceWarehouseId + " to " + destWarehouseId + ".");
                        } catch (Exception e) {
                            System.out.println("Error transferring stock: " + e.getMessage());
                        }
                        break;
                    case 5:
                        System.out.print("Enter customer name: ");
                        String customer = scanner.nextLine();
                        System.out.print("Enter quantity to order: ");
                        int qtyOrder = Integer.parseInt(scanner.nextLine());

                        try {
                            SalesOrder order = orderService.createSalesOrder("P001", qtyOrder, customer);
                            System.out.println("Sales Order created for: " + customer + " ! Order ID: " + order.getOrderId());
                            System.out.println("Stock allocated:");
                            order.getWarehouseAllocations().forEach((wh, q) ->
                                    System.out.println("  " + q + " units from " + wh)
                            );
                        } catch (InsufficientStockException e) {
                            System.out.println("Error: Not enough stock available across all warehouses. " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Error creating sales order: " + e.getMessage());
                        }
                        break;
                    case 6:
                        System.out.print("Enter Sales Order ID to fulfill: ");
                        String orderId = scanner.nextLine();
                        Shipment shipment = orderService.fulfillSalesOrder(orderId);
                        System.out.println(" Shipment Ready! Shipment ID: " + shipment.getShipmentId());
                        break;
                    case 7:
                        Map<String, InventoryItem> inventoryMap = inventoryRepo.findAll();
                        List<String> lowStock = reportService.generateLowStockReport(5, inventoryMap);
                        if (lowStock.isEmpty()) {
                            System.out.println("No products are running low. All stocked up!");
                        } else {
                            lowStock.forEach(System.out::println);
                        }
                        break;

                    case 8:
                        System.out.print("Enter Supplier ID: ");
                        String supplierId = scanner.nextLine();
                        System.out.print("Enter Product ID: ");
                        String productId = scanner.nextLine();
                        System.out.print("Enter quantity to order: ");
                        int poQty = Integer.parseInt(scanner.nextLine());
                        PurchaseOrder po = purchaseOrderService.createPurchaseOrder(supplierId, productId,
                                poQty);
                        System.out.println("Purchase Order created! Order ID: " + po.getOrderId());
                        break;
                    case 9:
                        System.out.println("All Inventory:");
                        inventoryRepo.findAll().values().forEach(i ->
                                System.out.println(i.getProductId() + " | " + i.getWarehouseId() + " | Qty: " + i.getQuantity())
                        );
                        break;
                    case 0:
                        System.out.println("Exiting Supply Chain Management System. May your warehouses stay full! Goodbye!");
                        return;
                    default:
                        System.out.println("Oops! Invalid option. Try again");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
