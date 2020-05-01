package com.gethealthy.gethealthy.community.form;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class PostForm {

    private String title;

    @NotBlank
    private Long category;

    @Lob
    @NotBlank
    private String contents;

    @NotBlank
    private Account author;

    private Product product;

    private Long liked;

    @Lob
    private String productImage;
}
