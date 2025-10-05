package com.company.scm.model;

import java.math.BigDecimal;

public class Product {
    private String productId;
    private String name;
    private String description;
    private String category;
    private BigDecimal unitPrice;

    public Product(String productId, String name, String description, String category, BigDecimal unitPrice) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.unitPrice = unitPrice;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public BigDecimal getUnitPrice() { return unitPrice; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}