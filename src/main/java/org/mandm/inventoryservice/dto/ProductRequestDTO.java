package org.mandm.inventoryservice.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
    String id;
    String name;
    double price;
    int quantity;
    Long categoryId;
}

