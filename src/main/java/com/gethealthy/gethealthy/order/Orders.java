package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "buyer", insertable = false, updatable = false, referencedColumnName = "id")
    private Account buyer;

    @ManyToMany
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(nullable = false)
    private String recipient;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private LocalDateTime orderedAt;

    @Column(nullable = false)
    private String email;

    private String requirement;

    @Column(nullable = false)
    @Embedded
    private Address address;

    @Column(nullable = false)
    @Embedded
    private PhoneNumber phoneNumber;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private String pgKind;
}

@Embeddable
class Address{
    private String city;
    private String street;
    private String zipCode;
}
class PhoneNumber{
    String local;
    String phoneNumber;
}
