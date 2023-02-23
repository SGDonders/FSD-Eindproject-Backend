package com.fsdeindopdracht.repositories;

import com.fsdeindopdracht.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findByProductName(String productName);

    @Query("SELECT max(p.id) FROM Product p")
    Long findMaxId();
}
