package com.company.scm.service;

import com.company.scm.exception.*;
import com.company.scm.model.InventoryItem;
import com.company.scm.model.Product;
import com.company.scm.repository.InventoryRepository;
import com.company.scm.repository.ProductRepository;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InventoryServiceTest {

    private InventoryService inventoryService;
    private ProductRepository productRepository;
    private InventoryRepository inventoryRepository;

    @BeforeClass
    public void setupClass() {
        productRepository = new ProductRepository();
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository, productRepository);
    }

    @BeforeMethod
    public void setup() throws InvalidQuantityException, EntityNotFoundException {
        Product product = new Product("P001", "Laptop", "Gaming Laptop", "Electronics", BigDecimal.valueOf(1500));
        productRepository.save(product);
        inventoryService.addStock("P001", "WH1", 10);
    }

    @AfterMethod
    public void cleanup() {
        inventoryRepository.clearAll();
        productRepository.clearAll();

    }

    @Test
    public void testAddStock() throws InvalidQuantityException, EntityNotFoundException {
        inventoryService.addStock("P001", "WH1", 5);
        int stock = inventoryService.getStockLevel("P001", "WH1");
        Assert.assertEquals(stock, 15);
    }

    @Test(expectedExceptions = InvalidQuantityException.class)
    public void testAddStockNegativeQuantity() throws InvalidQuantityException, EntityNotFoundException {
        inventoryService.addStock("P001", "WH1", -5);
    }

    @Test
    public void testRemoveStock() throws InvalidQuantityException, InsufficientStockException, EntityNotFoundException {
        inventoryService.removeStock("P001", "WH1", 5);
        int stock = inventoryService.getStockLevel("P001", "WH1");
        Assert.assertEquals(stock, 5);
    }

    @Test(expectedExceptions = InsufficientStockException.class)
    public void testRemoveStockExceeding() throws InvalidQuantityException, InsufficientStockException, EntityNotFoundException {
        inventoryService.removeStock("P001", "WH1", 20);
    }

    @DataProvider(name = "transferData")
    public Object[][] transferData() {
        return new Object[][]{
                {2, 8, 3},  // transfer 2: WH1 = 10-2=8, WH2 = 1+2=3
                {5, 5, 6}   // transfer 5: WH1 = 10-5=5, WH2 = 1+5=6
        };
    }

    @Test(dataProvider = "transferData")
    public void testTransferStock(int qtyToTransfer, int expectedSourceQty, int expectedDestQty) throws Exception {
        // Ensure destination warehouse exists with positive stock
        inventoryService.addStock("P001", "WH2", 1); // must be > 0

        // Perform the transfer
        inventoryService.transferStock("P001", "WH1", "WH2", qtyToTransfer);

        // Verify stock levels
        Assert.assertEquals(inventoryService.getStockLevel("P001", "WH1"), expectedSourceQty, "Source warehouse stock mismatch");
        Assert.assertEquals(inventoryService.getStockLevel("P001", "WH2"), expectedDestQty, "Destination warehouse stock mismatch");
    }
}