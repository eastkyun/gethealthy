package com.gethealthy.gethealthy.community;

import com.gethealthy.gethealthy.account.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Reply {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Account author;

    @Lob
    private String contents;

    private Long liked;

    private LocalDateTime created;
}
