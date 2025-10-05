package com.company.scm.service;

import com.company.scm.exception.DuplicateEntityException;
import com.company.scm.exception.EntityNotFoundException;
import com.company.scm.model.Supplier;

import java.util.HashMap;
import java.util.Map;

public class SupplierService {
    private final Map<String, Supplier> suppliers = new HashMap<>();

    public void addSupplier(Supplier supplier) throws DuplicateEntityException {
        if (suppliers.containsKey(supplier.getSupplierId())) throw new DuplicateEntityException("Supplier already exists: " + supplier.getSupplierId());
        suppliers.put(supplier.getSupplierId(), supplier);
    }

    public Supplier getSupplier(String supplierId) throws EntityNotFoundException {
        if (!suppliers.containsKey(supplierId)) throw new EntityNotFoundException("Supplier not found: " + supplierId);
        return suppliers.get(supplierId);
    }

    public void updateRating(String supplierId, double rating) throws EntityNotFoundException {
        Supplier supplier = getSupplier(supplierId);
        supplier.setRating(rating);
    }
}
