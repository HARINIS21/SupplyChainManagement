package com.company.scm.service;

import com.company.scm.model.InventoryItem;
import com.company.scm.model.Product;
import com.company.scm.repository.InventoryRepository;
import com.company.scm.repository.ProductRepository;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ReportServiceTest {

    private InventoryRepository inventoryRepository;
    private ProductRepository productRepository;
    private InventoryService inventoryService;
    private ReportService reportService;

    @BeforeClass
    public void setupClass() {
        productRepository = new ProductRepository();
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository, productRepository);
        reportService = new ReportService(inventoryService);
    }

    @BeforeMethod
    public void setup() throws Exception {
        Product product = new Product("P001", "Laptop", "Gaming Laptop", "Electronics", BigDecimal.valueOf(1500));
        productRepository.save(product);
        inventoryService.addStock("P001", "WH1", 10);
        inventoryService.addStock("P001", "WH2", 3);
    }

    @AfterMethod
    public void cleanup() {
        inventoryRepository.clearAll();
        productRepository.clearAll();

    }

    @Test
    public void testGenerateLowStockReport() {
        Map<String, InventoryItem> inventoryMap = inventoryRepository.findAll();
        List<String> lowStockReport = reportService.generateLowStockReport(5, inventoryMap);
        Assert.assertEquals(lowStockReport.size(), 1);
        Assert.assertTrue(lowStockReport.get(0).contains("WH2"));
    }
}