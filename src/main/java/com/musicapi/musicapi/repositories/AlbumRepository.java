package com.musicapi.musicapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicapi.musicapi.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

    
} 