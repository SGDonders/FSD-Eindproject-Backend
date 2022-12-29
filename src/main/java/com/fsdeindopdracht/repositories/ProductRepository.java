package com.fsdeindopdracht.repositories;

import com.fsdeindopdracht.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

