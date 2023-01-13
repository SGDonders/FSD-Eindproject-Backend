package com.fsdeindopdracht.repositories;

import com.fsdeindopdracht.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

