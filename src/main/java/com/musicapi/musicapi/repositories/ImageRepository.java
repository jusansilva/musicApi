package com.musicapi.musicapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicapi.musicapi.entity.Image;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    
} 