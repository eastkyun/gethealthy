package com.gethealthy.gethealthy.products.form;

import lombok.Data;

@Data
public class ProductForm {

    private Long id;

    private String name;

    private String price;

    private String description;

    private String origin;

    private Long liked;

    private String numberOf;

    private Long amount;

    private String productImage;


}
