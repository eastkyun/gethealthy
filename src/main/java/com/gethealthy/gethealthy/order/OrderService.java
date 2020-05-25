package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.account.AccountRepository;
import com.gethealthy.gethealthy.order.form.OrderForm;
import com.gethealthy.gethealthy.products.Product;
import com.gethealthy.gethealthy.products.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class OrderService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ModelMapper modelMapper;
    public void order(Account account, OrderForm orderForm) {
        Orders orders = new Orders();
        Address address = new Address();
        PhoneNumber phoneNumber = new PhoneNumber();
        Product product = productRepository.findByName(orderForm.getOrderItem());
        System.out.println("======================");
        System.out.println(product.getId());

        orders.setRecipient(orderForm.getRecipient());
        address.setZipCode(orderForm.getZipCode());
        address.setStreet(orderForm.getStreet());
        orders.setAddress(address);

        phoneNumber.setPhoneNumber(orderForm.getPhoneNumber());
        orders.setPhoneNumber(phoneNumber);

        orders.setRequirement(orderForm.getRequirement());

        orders.setEmail(orderForm.getEmail());

        orders.setPgKind(orderForm.getPgKind());
        orders.setBuyer(account);

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setOrderQuantity("1");
        orderItemRepository.save(orderItem);
        orders.getOrderItems().add(orderItem);
        orders.setOrderedAt(LocalDateTime.now());

        orders.setPrice(orderForm.getPrice());

        orderRepository.save(orders);
    }
}
