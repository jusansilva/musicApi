package com.musicapi.musicapi.services;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.musicapi.musicapi.entity.Album;
import com.musicapi.musicapi.entity.Image;
import com.musicapi.musicapi.repositories.AlbumRepository;
import com.musicapi.musicapi.repositories.ImageRepository;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository repository;
    
     @Autowired
    private ImageRepository imageRepository;

    public Page<Album> listAll(int page, int length){
        PageRequest pageable = PageRequest.of(page, length);
        Page<Album> albumies = repository.findAll(pageable);
        return albumies;
    }

    public Album getById(UUID id){
        Album album = repository.findById(id).get();
        return album;
    }

    public Album create(Album album, MultipartFile file) throws IOException{
        if(!file.isEmpty()){
            if(file.getBytes().length > 50000){
                IOException e = new IOException("Image to large");
                throw e;
            }
            Image image = new Image();
            image.setDados(file.getBytes());
            image.setNome(file.getOriginalFilename());
            imageRepository.save(image);
            album.setImage(image.getId().toString());
        }
        
        Album saveAlbum = repository.save(album);
        return saveAlbum;
    }

    public ResponseEntity<String> update(UUID id,Album entity, Optional<MultipartFile> file) throws IOException {
       Optional<Album> albumExist = repository.findById(id);
        if (albumExist.isPresent()) {
            Album albumUpdate = albumExist.get();
            file.ifPresent(filep -> {
                try {
                    if(filep.getBytes().length > 50000){
                        IOException e = new IOException("Image to large");
                        throw e;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image image = imageRepository.getReferenceById(UUID.fromString(entity.getImage()));
                    try {
                        image.setDados(filep.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageRepository.save(image);
                    albumUpdate.setImage(image.getId().toString());
                });
                
                if(entity.getTitle() != null && !entity.getTitle().isEmpty()){
                    albumUpdate.setTitle(entity.getTitle());
                }
                if(entity.getYeard() != null && !entity.getYeard().isEmpty()){
                    albumUpdate.setYeard(entity.getYeard());
                }
                
                if(entity.getArtist() != null){
                    albumUpdate.setArtist(entity.getArtist());
                }
              
                repository.save(albumUpdate);
                return ResponseEntity.ok("Album is updated!");
            } else {
                return ResponseEntity.notFound().build();
            }
    }

    public ResponseEntity<String> delete( UUID id){
        Album album = repository.findById(id).get();
        repository.deleteById(id);
        return ResponseEntity.ok("Artist is updated with id: "+ album.getId());
    }
}
