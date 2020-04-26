package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Ordering {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<Product> products = new HashSet<>();

    @ManyToOne
    private Account customer;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();


}
