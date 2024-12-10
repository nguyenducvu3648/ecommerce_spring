package com.hendisantika.repository;

import com.hendisantika.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    @Query("SELECT DISTINCT brand FROM Product")
    List<String> findAllBrandsDistincts();

    @Query("SELECT COALESCE(MAX(p.discount.id), 0) FROM Product p WHERE p.discount IS NOT NULL")
    Long findMaxDiscountIdInProduct();
}
