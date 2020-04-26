package com.gethealthy.gethealthy.cart;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<Product> products = new HashSet<>();

    @OneToOne
    private Account account;
}
