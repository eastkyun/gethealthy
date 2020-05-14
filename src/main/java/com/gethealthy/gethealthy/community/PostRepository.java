package com.gethealthy.gethealthy.community;

import com.gethealthy.gethealthy.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post,Long> {
//     Page<Post> findAllByCategoryAndProduct(Long category, Product product , Pageable pageable);
     Page<Post> findAllByPostTypeAndProduct(PostType postType, Product product, Pageable pageable);
     Page<Post> findAllByPostType(PostType postType, Pageable pageable);

     Post findByTitle(String title);
}
