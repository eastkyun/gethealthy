package com.gethealthy.gethealthy.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;

    @GetMapping("")
    public String products(Model model) throws JsonProcessingException {
        List<Product> productList = productRepository.findAll();
        model.addAttribute(productList);
        return "products/index";
    }
    @GetMapping("/details/{name}")
    public String productDetails(@PathVariable String name, Model model){
        Product product = productRepository.findByName(name);
        if(product==null){
            return "redirect:/products/main";
        }
        model.addAttribute(modelMapper.map(product, ProductForm.class));
        return "products/details";
    }
    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity addProductInCart(@CurrentUser Account account, @RequestBody ProductForm productForm, Model model){
        String name = productForm.getName();
        Product product = productRepository.findByName(name);
        accountService.addProductInCart(account, product);
        return ResponseEntity.ok().build() ;
    }
    @PostMapping("/cart/remove")
    @ResponseBody
    public ResponseEntity removeProductInCart(@CurrentUser Account account, @RequestBody ProductForm productForm, Model model){
        String name = productForm.getName();
        Product product = productRepository.findByName(name);
        accountService.removeProductInCart(account, product);
        return ResponseEntity.ok().build() ;
    }
}
