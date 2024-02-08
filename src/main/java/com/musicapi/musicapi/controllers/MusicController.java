package com.musicapi.musicapi.controllers;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.musicapi.musicapi.entity.Album;
import com.musicapi.musicapi.entity.Music;
import com.musicapi.musicapi.entity.dto.RequestMusicDto;
import com.musicapi.musicapi.services.AlbumService;
import com.musicapi.musicapi.services.MusicService;

@CrossOrigin
@RestController
@RequestMapping(value = "/music")
public class MusicController {
    @Autowired
    private MusicService service;

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public Page<Music> listAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int length ){
        Page<Music> musicies = service.listAll(page, length);
        return musicies;
    }

    @GetMapping("/artist/{artistId}")
    public Page<Music> getMusicByArtist(@PathVariable String artistId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int length ) {
        Page<Music> musicies = service.listMusicByArtist(artistId, page, length);
        return musicies;
    }

    @GetMapping("/album/{albumId}")
    public Page<Music> getMusicByAlbum(@PathVariable String albumId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int length, @RequestParam(defaultValue = "title") String orderBy ) throws IOException {
        Page<Music> musicies = service.listMusicByAlbum(albumId, page,length, orderBy);
        return musicies;
    }
    

    @GetMapping(value = "/{id}")
    public Music getById(@PathVariable UUID id){
        Music music = service.getById(id);
        return music;
    }

    @PostMapping
    public Music create(@RequestBody RequestMusicDto requestMusicDto) throws IOException{
        if(requestMusicDto.getTrack().orElse(0) <=  0){
            IOException e = new IOException("The track cannot be smaller  than the 0");
            throw e;
        }
       
        Album album = albumService.getById(UUID.fromString(requestMusicDto.getAlbumId().orElseThrow()));

        Music music = new Music();
        requestMusicDto.getTitle().ifPresent(title -> {
            music.setTitle(title);
        });
        requestMusicDto.getTime().ifPresent(time -> {
            music.setTime(time);
        });

        requestMusicDto.getTrack().ifPresent(trackp -> {
            music.setTrack((long)trackp);
        });
        music.setAlbum(album);
        Music saveMusic = service.create(music);
        return saveMusic;
    }

    @PutMapping("/{id}")
    public Music update(@PathVariable UUID id, @RequestBody RequestMusicDto requestMusicDto) throws IOException {
        Album album = albumService.getById(UUID.fromString(requestMusicDto.getAlbumId().orElseThrow()));
        Music music = new Music();
        requestMusicDto.getTitle().ifPresent(title -> {
            music.setTime(title);
        });
        requestMusicDto.getTime().ifPresent(time -> {
            music.setTime(time);
        });
        requestMusicDto.getTrack().ifPresent(trackp -> {
            music.setTrack((long)trackp);
        });
        music.setAlbum(album);
       
        Music updateMusic = service.update(id, music);

        return updateMusic;
       
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable UUID id){
        ResponseEntity<String> deleted = service.delete(id);
        return deleted;
    }
}
