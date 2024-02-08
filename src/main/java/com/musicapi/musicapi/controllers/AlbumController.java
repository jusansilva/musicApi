package com.musicapi.musicapi.controllers;

import java.io.IOException;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.musicapi.musicapi.entity.Album;
import com.musicapi.musicapi.entity.Artist;
import com.musicapi.musicapi.services.AlbumService;
import com.musicapi.musicapi.services.ArtistService;

@CrossOrigin
@RestController
@RequestMapping(value = "/album")
public class AlbumController {
     @Autowired
    private AlbumService service;

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public Page<Album> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int length ){
        Page<Album> albumies = service.listAll(page, length);
        return albumies;
    }

    @GetMapping(value = "/{id}")
    public Album getById(@PathVariable UUID id){
        Album album = service.getById(id);
        return album;
    }

    @PostMapping
    public Album create(@RequestPart("title") String title, @RequestPart("year") String year,@RequestPart("artistId") String artistId, @RequestPart("file") MultipartFile file) throws IOException{
        Artist artist = artistService.getById(UUID.fromString(artistId));
        Year nowYear = Year.now();
        if(Year.parse(year).isAfter(nowYear)){
            IOException e = new IOException("The year cannot be greater than the current year");
            throw e;
        }
        Album album = new Album();
        album.setTitle(title);
        album.setYeard(year);
        album.setArtist(artist);
        
        Album saveAlbum = service.create(album, file);
        return saveAlbum;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestPart("title") Optional<String> title, @RequestPart("yeard") Optional<String> yeard,@RequestPart("artistId") Optional<String> artistId, @RequestPart("file") Optional<MultipartFile> file) throws IOException {
        Album album = new Album();
        title.ifPresent(titlep -> {
            album.setTitle(titlep);
        });
        yeard.ifPresent(yeardp ->{
            album.setYeard(yeardp);
        });
        artistId.ifPresent((artistIdp) -> {
            Artist artist = artistService.getById(UUID.fromString(artistIdp));
            album.setArtist(artist);
        });

        ResponseEntity<String> updateAlbum = service.update(id, album, file);

        return updateAlbum;
       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id){
        ResponseEntity<String> deleted = service.delete(id);
        return deleted;
    }
}
