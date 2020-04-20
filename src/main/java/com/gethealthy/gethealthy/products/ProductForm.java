package com.gethealthy.gethealthy.products;

import lombok.Data;

@Data
public class ProductForm {

    private Long id;

    private String name;

    private String price;

    private String description;

    private String origin;

    private String numberOf;

    private Long amount;

    private String productImage;
}
