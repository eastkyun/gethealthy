package com.gethealthy.gethealthy.order;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Account account_id;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false, referencedColumnName = "id")
    private Product product_id;

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
}

@Embeddable
class Address{
    private String city;
    private String street;
    @Embedded
    private ZipCode zipCode;
}
@Embeddable
class ZipCode{
    String zip;
    String code;
}
class PhoneNumber{
    String local;
    String phoneNumber;
}
