package com.gethealthy.gethealthy.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
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

    @ManyToMany
    private Set<Tag> tags;

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
