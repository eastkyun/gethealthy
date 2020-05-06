package com.gethealthy.gethealthy.account;

import com.gethealthy.gethealthy.products.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @EntityGraph(value = "Account.withAll",type = EntityGraph.EntityGraphType.FETCH)
    Account findByEmail(String s);
    @EntityGraph(value = "Account.withAll",type = EntityGraph.EntityGraphType.FETCH)
    Account findByNickname(String nickname);

    boolean existsByLikedListAndId(Product product, Long id);

}
