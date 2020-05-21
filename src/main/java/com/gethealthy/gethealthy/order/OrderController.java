package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountService;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.order.form.OrderForm;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.ProductRepository;
import com.gethealthy.gethealthy.products.form.ProductForm;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class OrderController {
    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private AccountService accountService;
    @Autowired private ProductRepository productRepository;
    @Autowired private ModelMapper modelMapper;
    @PostMapping("/order")
    public String orderItem(@CurrentUser Account account, ProductForm productForm,
                            Model model) {

        Product product = productRepository.findByName(productForm.getName());
        model.addAttribute(product);
        OrderForm orderForm = new OrderForm();
        model.addAttribute(orderForm);
        model.addAttribute(account);
        return "order/form";
    }
    @GetMapping("/order")
    public String orderForm(@CurrentUser Account account, @RequestParam String name, Model model){
        Product product = productRepository.findByName(name);
        model.addAttribute(product);
        model.addAttribute(account);
        model.addAttribute(new OrderForm());
        return "order/form";
    }

}
