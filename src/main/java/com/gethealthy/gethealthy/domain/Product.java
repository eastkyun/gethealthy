package com.gethealthy.gethealthy.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String price;

    @Lob
    private String description;


    private String numberOf;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String productImage;
}
