package com.gethealthy.gethealthy.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.community.Post;
import com.gethealthy.gethealthy.community.PostRepository;
import com.gethealthy.gethealthy.products.form.ProductForm;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private ObjectMapper objectMapper;
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
            if(accountRepository.existsByLikedListAndId(product,account.getId())){
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
    public String addProductInCart(@CurrentUser Account account, @RequestBody ProductForm productForm,
                                   RedirectAttributes attributes, Model model) {
        String name = productForm.getName();
        Product product = productRepository.findByName(name);
        accountService.addProductInCart(account, product);
        attributes.addFlashAttribute("message","장바구니에서 추가했습니다.");
        return "redirect:/mypage/cart";
    }
    @PostMapping("/cart/remove")
    public String removeProductInCart(@CurrentUser Account account, @RequestBody ProductForm productForm,
                                      RedirectAttributes attributes, Model model){
        String name = productForm.getName();
        Product product = productRepository.findByName(name);
        accountService.removeProductInCart(account, product);
        attributes.addFlashAttribute("message","장바구니에서 삭제했습니다.");
        return "redirect:/mypage/cart";
    }
    @PostMapping("/liked")
    @ResponseBody
    public ResponseEntity likedProduct(@CurrentUser Account account, @RequestBody ProductForm productForm, Model model){
        String productName = productForm.getName();
        Account user = accountRepository.findByNickname(account.getNickname());
        Product product = productRepository.findByName(productName);
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
