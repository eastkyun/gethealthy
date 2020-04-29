package com.gethealthy.gethealthy.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public String getProducts(Model model, Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        model.addAttribute("productList",productPage.toList());
        return "products/index";
    }
    @GetMapping("/products/details/{name}")
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
    @GetMapping("{name}/liked")
    public String like(@CurrentUser Account account,@PathVariable String name, Model model){
        Account user = accountRepository.findByNickname(account.getNickname());
        Product product = productRepository.findByName(name);

        if(user.getLikedList().contains(product)){
            System.out.println("좋아요 취소");
            productService.decreaseLiked(product);
            accountService.removeLikedProduct(account, product);
        }
        else{
            System.out.println("좋아요");
            productService.increaseLiked(product);
            accountService.addLikedProduct(account, product);
        }
        return "products/details";
    }
    @PostMapping("{name}/liked")
    public ResponseEntity likedProduct(@CurrentUser Account account, ProductForm productForm, Model model, @PathVariable String name){
        Account user = accountRepository.findByNickname(account.getNickname());
        Product product = productRepository.findByName(productForm.getName());

        if(user.getLikedList().contains(product)){
            System.out.println("좋아요 취소");
            productService.decreaseLiked(product);
            accountService.removeLikedProduct(account, product);
        }
        else{
            System.out.println("좋아요");
            productService.increaseLiked(product);
            accountService.addLikedProduct(account, product);
        }
        model.addAttribute(productForm);
        return ResponseEntity.ok().build() ;
    }
    @GetMapping("{name}/liked/increase")
    public ResponseEntity increaseLike(@CurrentUser Account account,@PathVariable String name, Model model){
        Product product = productRepository.findByName(name);
        productService.increaseLiked(product);
        accountService.addLikedProduct(account, product);
        return ResponseEntity.ok().build() ;
    }

    @GetMapping("{name}/liked/decrease")
    public ResponseEntity decreaseLike(@CurrentUser Account account, @PathVariable String name, Model model){
        Product product = productRepository.findByName(name);
        productService.decreaseLiked(product);
        accountService.removeLikedProduct(account, product);
        return ResponseEntity.ok().build() ;
    }

}
