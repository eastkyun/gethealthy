package com.gethealthy.gethealthy.products.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ProductForm {

    @NotBlank
    @Length(min=3, max=20)
    private String name;

    @Pattern(regexp = "^[0-9]*$",message = "가격을 입력해주세요.")
    private String price;

    @NotBlank
    private String description;

    private String origin;

    private Long liked;

    private boolean displayed;

    @Pattern(regexp = "^[0-9]*$",message = "수량을 입력해주세요.")
    private String numberOf;

    private Long amount;

    private String productImage;


}
