package com.musicapi.musicapi.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.musicapi.musicapi.entity.Music;

public interface MusicRepository extends JpaRepository<Music, UUID> {
    
    @Query("SELECT m FROM Music m WHERE m.album.artist.id = :artistId")
    Page<Music> findByArtistIPage(@Param("artistId") UUID artistId, PageRequest pageable);

    @Query("SELECT m FROM Music m WHERE m.album.id = :albumId ORDER BY :orderBy")
    Page<Music> findByAlbumIdPage(@Param("albumId") UUID albumId,  @Param("orderBy") String orderBy, PageRequest pageable);
}
