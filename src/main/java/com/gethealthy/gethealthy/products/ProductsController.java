package com.gethealthy.gethealthy.products;

import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductsController {
    @GetMapping("/main")
    public String products(Model model){
        return "products/main";
    }
}
