package com.gethealthy.gethealthy.community;

import com.gethealthy.gethealthy.account.Account;
import com.gethealthy.gethealthy.products.Product;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(name = "Post.withAll",attributeNodes ={
        @NamedAttributeNode("replies")
})
@Entity
@Getter @Setter @EqualsAndHashCode(of="id")
@Builder @AllArgsConstructor @NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private Long liked;

    /*
    * 1 : 공지사항
    * 2 : QnA
    * 3 : 리뷰
    * */
    //deprecated
//    private Long category;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String contents;

    private LocalDateTime created=LocalDateTime.now();

    @ManyToOne
    private Account author;

    @ManyToMany
    private Set<Reply> replies = new HashSet<>();

    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String productImage;

    @ManyToOne
    private Product product;
}
