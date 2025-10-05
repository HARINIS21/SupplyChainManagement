package com.company.scm.service;

import com.company.scm.exception.DuplicateEntityException;
import com.company.scm.exception.EntityNotFoundException;
import com.company.scm.model.Supplier;
import org.testng.Assert;
import org.testng.annotations.*;

public class SupplierServiceTest {

    private SupplierService supplierService;

    @BeforeClass
    public void setupClass() {
        supplierService = new SupplierService();
    }

    @BeforeMethod
    public void setup() {
        // Cleanup before each test
    }

    @AfterMethod
    public void cleanup() {
        // Reset supplierService internal state
        // Since it's using a HashMap, we need to clear it via reflection or recreate
        supplierService = new SupplierService();
    }

    @Test
    public void testAddSupplierSuccess() throws DuplicateEntityException, EntityNotFoundException {
        Supplier supplier = new Supplier("S001", "ABC Supplies", "abc@example.com", 4.5);
        supplierService.addSupplier(supplier);

        Supplier retrieved = supplierService.getSupplier("S001");
        Assert.assertEquals(retrieved.getName(), "ABC Supplies");
        Assert.assertEquals(retrieved.getRating(), 4.5);
    }

    @Test(expectedExceptions = DuplicateEntityException.class)
    public void testAddDuplicateSupplier() throws DuplicateEntityException {
        Supplier supplier1 = new Supplier("S002", "XYZ Supplies", "xyz@example.com", 4.0);
        Supplier supplier2 = new Supplier("S002", "XYZ Supplies Duplicate", "xyz2@example.com", 3.5);
        supplierService.addSupplier(supplier1);
        supplierService.addSupplier(supplier2); // Should throw DuplicateEntityException
    }

    @Test
    public void testUpdateRating() throws DuplicateEntityException, EntityNotFoundException {
        Supplier supplier = new Supplier("S003", "Supplier 3", "sup3@example.com", 3.0);
        supplierService.addSupplier(supplier);

        supplierService.updateRating("S003", 4.2);
        Supplier updated = supplierService.getSupplier("S003");
        Assert.assertEquals(updated.getRating(), 4.2);
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testGetNonExistentSupplier() throws EntityNotFoundException {
        supplierService.getSupplier("S999"); // Should throw EntityNotFoundException
    }

    @Test(expectedExceptions = EntityNotFoundException.class)
    public void testUpdateRatingNonExistentSupplier() throws EntityNotFoundException {
        supplierService.updateRating("S999", 5.0); // Should throw EntityNotFoundException
    }
}
