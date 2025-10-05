package com.company.scm.service;

import com.company.scm.exception.*;
import com.company.scm.model.SalesOrder;
import com.company.scm.model.Shipment;
import com.company.scm.repository.InventoryRepository;
import com.company.scm.repository.SalesOrderRepository;
import com.company.scm.repository.ProductRepository;
import com.company.scm.model.Product;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;

public class OrderServiceTest {

    private InventoryService inventoryService;
    private OrderService orderService;
    private ProductRepository productRepository;
    private InventoryRepository inventoryRepository;
    private SalesOrderRepository salesOrderRepository;

    @BeforeClass
    public void setupClass() {
        productRepository = new ProductRepository();
        inventoryRepository = new InventoryRepository();
        salesOrderRepository = new SalesOrderRepository();
        inventoryService = new InventoryService(inventoryRepository, productRepository);
        orderService = new OrderService(inventoryService, salesOrderRepository, inventoryRepository);
    }

    @BeforeMethod
    public void setup() throws Exception {
        Product product = new Product("P001", "Laptop", "Gaming Laptop", "Electronics", BigDecimal.valueOf(1500));
        productRepository.save(product);
        inventoryService.addStock("P001", "WH1", 10);
    }

    @AfterMethod
    public void cleanup() {
        inventoryRepository.clearAll();
        productRepository.clearAll();
        salesOrderRepository.clearAll();
    }

    @Test
    public void testCreateSalesOrderSuccess() throws Exception {
        SalesOrder order = orderService.createSalesOrder("P001", 5, "Customer A");
        Assert.assertEquals(order.getQuantity(), 5);
        Assert.assertEquals(order.getStatus(), SalesOrder.Status.PROCESSING);
    }

    @Test(expectedExceptions = InsufficientStockException.class)
    public void testCreateSalesOrderInsufficientStock() throws Exception {
        orderService.createSalesOrder("P001", 20, "Customer B");
    }

    // Updated test to depend on successful order creation
    @Test(dependsOnMethods = {"testCreateSalesOrderSuccess"})
    public void testFulfillSalesOrder() throws Exception {
        // Create a new order here instead of using shared state
        SalesOrder order = orderService.createSalesOrder("P001", 5, "Customer C");
        Shipment shipment = orderService.fulfillSalesOrder(order.getOrderId());

        Assert.assertEquals(order.getStatus(), SalesOrder.Status.FULFILLED);
        Assert.assertEquals(inventoryService.getStockLevel("P001", "WH1"), 5);
        Assert.assertEquals(shipment.getOrderId(), order.getOrderId());
    }
}