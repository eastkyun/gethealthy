package com.gethealthy.gethealthy.order.form;

import com.gethealthy.gethealthy.order.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderForm {

    private String recipient;

    private String local;

    private String phoneNumber;

    private String city;

    private String street;

    private String zipCode;


    private String price;

    private LocalDateTime orderedAt;

    private String email;

    private String requirement;

    private Set<OrderItem> orderItems;

    private String pgKind;

}
