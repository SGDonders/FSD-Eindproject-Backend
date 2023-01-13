package com.fsdeindopdracht.repositories;

import com.fsdeindopdracht.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
