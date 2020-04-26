package com.gethealthy.gethealthy.account;

import com.gethealthy.gethealthy.cart.Cart;
import com.gethealthy.gethealthy.order.Ordering;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt;

    private String bio;

    private String url;

    private String occupation;

    private String location;

    private String liveAround;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    private boolean studyCreatedByEmail;

    private boolean studyCreatedByWeb;

    private boolean studyEnrollmentResultByEmail;

    private boolean studyEnrollmentResultByWeb;

    private boolean studyUpdatedByEmail;

    private boolean studyUpdatedByWeb;

    @OneToOne
    private Cart cart;

    @OneToMany
    private Set<Ordering> orders = new HashSet<>();

    private LocalDateTime emailCheckTokenGenerateAt;
    public void generateEmailCheckToken() {
        this.emailCheckToken= UUID.randomUUID().toString();
        emailCheckTokenGenerateAt=LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified=true;
        this.joinedAt =LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.getEmailCheckToken().equals(token);

    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGenerateAt.isBefore(LocalDateTime.now().minusHours(1));
    }
}
