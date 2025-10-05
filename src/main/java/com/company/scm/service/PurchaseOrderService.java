package com.company.scm.service;
import com.company.scm.exception.EntityNotFoundException;
import com.company.scm.model.PurchaseOrder;
import com.company.scm.repository.PurchaseOrderRepository;
import java.time.LocalDateTime;
import java.util.UUID;
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public PurchaseOrder createPurchaseOrder(String supplierId, String productId, int quantity) {
        PurchaseOrder order = new PurchaseOrder(
                UUID.randomUUID().toString(),
                supplierId,
                productId,
                quantity,
                LocalDateTime.now(),
                PurchaseOrder.Status.PENDING
        );
        purchaseOrderRepository.save(order);
        return order;
    }

    public void updateStatus(String orderId, PurchaseOrder.Status status) throws
            EntityNotFoundException {
        PurchaseOrder order = purchaseOrderRepository.findById(orderId);
        if (order == null) throw new EntityNotFoundException("Purchase order not found: " +
                orderId);
        order.setStatus(status);
        purchaseOrderRepository.save(order);
    }
}
