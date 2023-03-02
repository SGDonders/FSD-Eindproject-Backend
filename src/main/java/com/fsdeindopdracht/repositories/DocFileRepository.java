package com.fsdeindopdracht.repositories;

import com.fsdeindopdracht.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface DocFileRepository extends JpaRepository<Image, Long> {
    Image findByFileName(String fileName);
}