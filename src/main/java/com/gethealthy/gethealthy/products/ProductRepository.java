package com.gethealthy.gethealthy.products;

import com.gethealthy.gethealthy.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {
}
