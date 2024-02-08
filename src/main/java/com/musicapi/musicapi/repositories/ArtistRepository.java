package com.musicapi.musicapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.musicapi.musicapi.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    
}
