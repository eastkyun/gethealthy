package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.products.Product;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Product product;

    private String orderQuantity;
}
