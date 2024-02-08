package com.musicapi.musicapi.services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.musicapi.musicapi.entity.Music;
import com.musicapi.musicapi.repositories.MusicRepository;

@Service
public class MusicService {
    @Autowired
    private MusicRepository repository;

    public Page<Music> listAll(int page, int length) {
        PageRequest pageable = PageRequest.of(page, length);
        return repository.findAll(pageable);
    }

    public Music getById(UUID id) {
        Music music = repository.findById(id).get();
        return music;
    }

    public Music create(Music music) throws IOException {
        if (!validFormatTime(music.getTime())) {
            IOException e = new IOException("Time is not valid");
            throw e;
        }
        Music saveAlbum = repository.save(music);
        return saveAlbum;
    }

    public Music update(UUID id, Music entity) throws IOException {
        if (!validFormatTime(entity.getTime())) {
            IOException e = new IOException("Time is not valid");
            throw e;
        }

        Optional<Music> musicExist = repository.findById(id);
        if (musicExist.isPresent()) {
            Music musicUpdate = musicExist.get();

            if (entity.getTitle() != null && !entity.getTitle().isEmpty()) {
                musicUpdate.setTitle(entity.getTitle());
            }
            if (entity.getTime() != null && !entity.getTime().isEmpty()) {
                musicUpdate.setTime(entity.getTime());
            }

            if (entity.getTrack() != null) {
                musicUpdate.setTrack(entity.getTrack());
            }

           Music music =  repository.save(musicUpdate);
            return music;
        } else {
            IOException e = new IOException("Music not found");
            throw e;
        }
    }

    public ResponseEntity<String> delete(UUID id) {
        Music music = repository.findById(id).get();
        repository.deleteById(id);
        return ResponseEntity.ok("Artist is updated with id: " + music.getId());
    }

    public Page<Music> listMusicByArtist(String artistId, int page, int length) {
        PageRequest pageable = PageRequest.of(page, length);
        Page<Music> returnMusics = repository.findByArtistIPage(UUID.fromString(artistId), pageable);
        return returnMusics;
    }

    public Page<Music> listMusicByAlbum(String artistId, int page, int length, String orderBy) throws IOException {
        PageRequest pageable = PageRequest.of(page, length);
        Page<Music> returnMusics = repository.findByAlbumIdPage(UUID.fromString(artistId), orderBy, pageable);
        return returnMusics;
    }

    private static boolean validFormatTime(String input) {
        String regex = "^([01]\\d|2[0-3]):[0-5]\\d$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        return matcher.matches();
    }
}
