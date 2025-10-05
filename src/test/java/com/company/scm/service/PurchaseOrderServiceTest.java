package com.company.scm.service;
import com.company.scm.model.PurchaseOrder;
import com.company.scm.repository.PurchaseOrderRepository;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class PurchaseOrderServiceTest {
    private PurchaseOrderService purchaseOrderService;
    private PurchaseOrderRepository purchaseOrderRepository;
    @BeforeMethod
    public void setup() {
        purchaseOrderRepository = new PurchaseOrderRepository();
        purchaseOrderService = new PurchaseOrderService(purchaseOrderRepository);
    }
    @Test
    public void testCreatePurchaseOrder() {
        PurchaseOrder order = purchaseOrderService.createPurchaseOrder("S001", "P001", 10);
        Assert.assertNotNull(order.getOrderId());
        Assert.assertEquals(order.getQuantity(), 10);
        Assert.assertEquals(order.getStatus(), PurchaseOrder.Status.PENDING);
    }
}
