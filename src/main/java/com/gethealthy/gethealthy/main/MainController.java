package com.gethealthy.gethealthy.main;

import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.domain.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(@CurrentUser Account account, Model model){
        if(account !=null){
            model.addAttribute(account);
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/products")
    public String product(){
        return "products";
    }

    @GetMapping("/community")
    public String community(){
        return "community";
    }
}
