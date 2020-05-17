package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.CurrentUser;
import com.gethealthy.gethealthy.order.form.OrderForm;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.form.ProductForm;
import org.aspectj.weaver.ast.Or;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class OrderController {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ModelMapper modelMapper;
    @PostMapping("/order")
    public String orderForm(@CurrentUser Account account, ProductForm productForm,
                            Model model){
        Product product = modelMapper.map(productForm, Product.class);
        //Todo 주문 결제
        model.addAttribute(account);
        return "redirect:/order/form";
    }
    @GetMapping("/order/form")
    public String orderItem(@CurrentUser Account account, Model model) {

        model.addAttribute(new OrderForm());
        model.addAttribute(account);
        return "order/form";
    }
}
