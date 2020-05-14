package com.gethealthy.gethealthy.products.validator;

import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.ProductRepository;
import com.gethealthy.gethealthy.products.form.ProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductFormValidator implements Validator {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(ProductForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductForm productForm = (ProductForm) target;
        Product byName = productRepository.findByName(productForm.getName());
        if(byName != null){
                errors.rejectValue("name","wrong.value","이미 존재하는 상품입니다.");
        }
    }
}
