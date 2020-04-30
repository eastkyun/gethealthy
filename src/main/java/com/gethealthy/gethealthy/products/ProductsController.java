package com.gethealthy.gethealthy.products;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.community.Post;
import com.gethealthy.gethealthy.community.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/products")
    public String getProducts(Model model, Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        model.addAttribute("productList",productPage.toList());
        return "products/index";
    }
    @GetMapping("/products/details/{name}")
    public String productDetails(@CurrentUser Account account, @PathVariable String name,
                                 @PageableDefault(size = 5, sort = "created", direction = Sort.Direction.DESC)
                                 Pageable pageable, Model model){
        Product product = productRepository.findByName(name);
        if(product==null){return "redirect:/products";}
        if (account!=null){
            model.addAttribute(account);
            if(accountRepository.existsByLikedList(product)){
                model.addAttribute("isLiked","true");
            }else{
                model.addAttribute("isLiked","false");
            }
        }
        Page<Post> reviews = postRepository.findAllByCategoryAndProduct(3L, product, pageable);
        model.addAttribute("reviews", reviews);
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
    @PostMapping("{name}/liked")
    @ResponseBody
    public ResponseEntity likedProduct(@CurrentUser Account account, ProductForm productForm, Model model, @PathVariable String name){
        Account user = accountRepository.findByNickname(account.getNickname());
        Product product = productRepository.findByName(productForm.getName());

        if(user.getLikedList().contains(product)){
            productService.decreaseLiked(product);
            accountService.removeLikedProduct(account, product);
        }
        else{
            productService.increaseLiked(product);
            accountService.addLikedProduct(account, product);
        }
        model.addAttribute(productForm);
        return ResponseEntity.ok().build();
    }

}
