package com.musicapi.musicapi.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.musicapi.musicapi.entity.Artist;
import com.musicapi.musicapi.services.ArtistService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin
@RestController
@RequestMapping(value = "/artist")
public class ArtistController {

    @Autowired
    private ArtistService service;

    @GetMapping
    public Page<Artist> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int length ){
        Page<Artist> artisties = service.listAll(page, length);
        return artisties;
    }

    @GetMapping(value = "/{id}")
    public Artist getById(@PathVariable UUID id){
        Artist artist = service.getById(id);
        return artist;
    }

    @PostMapping
    public ResponseEntity<Artist> create(@RequestPart("name") String name, @RequestPart("nationality") String nationality,@RequestPart("site") String site, @RequestPart("file") MultipartFile file) throws IOException{
        Artist artist = new Artist();
        artist.setName(name);
        artist.setNationality(nationality);
        artist.setSite(site);
        ResponseEntity<Artist> saveArtist = service.create(artist, file);
        return saveArtist;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id, @RequestPart("name") Optional<String> name, @RequestPart("nationality") Optional<String> nationality,@RequestPart("site") Optional<String> site, @RequestPart("file") Optional<MultipartFile> file) throws IOException {
        Artist artist = new Artist();
        name.ifPresent(namep -> {
            artist.setName(namep);
        });
        nationality.ifPresent(nationalityp ->{
            artist.setNationality(nationalityp);
        });
        site.ifPresent(sitep -> {
            artist.setSite(sitep);
        });

        ResponseEntity<String> updateArtist = service.update(id, artist, file);

        return updateArtist;
       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id){
        ResponseEntity<String> deleted = service.delete(id);
        return deleted;
    }
} 