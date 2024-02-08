package com.musicapi.musicapi.services;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.musicapi.musicapi.entity.Artist;
import com.musicapi.musicapi.entity.Image;
import com.musicapi.musicapi.repositories.ArtistRepository;
import com.musicapi.musicapi.repositories.ImageRepository;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository repository;
    
    @Autowired
    private ImageRepository imageRepository;

    public Page<Artist>listAll(int page, int length){
        PageRequest pageable = PageRequest.of(page, length);
        Page<Artist> artist = repository.findAll(pageable);
        return artist;
    }

    public Artist getById(UUID id){
        Artist artist = repository.findById(id).get();
        return artist;
    }

    public ResponseEntity<Artist> create(Artist artist, MultipartFile file)throws IOException{
        if(!file.isEmpty()){
            if(file.getBytes().length > 100000){
                IOException e = new IOException("Image to large");
                throw e;
            }
            Image image = new Image();
            image.setDados(file.getBytes());
            image.setNome(file.getOriginalFilename());
            imageRepository.save(image);
            artist.setImgProgile(image.getId().toString());
        }
        
        if (!isValidSite(artist.getSite())) {
            return ResponseEntity.badRequest().build();
        } 

        Artist saveArtist = repository.save(artist);
       return ResponseEntity.ok(saveArtist);
    }

    public ResponseEntity<String> update(UUID id, Artist entity, Optional<MultipartFile> file) throws IOException{
        Optional<Artist> artistExist = repository.findById(id);
        if (artistExist.isPresent()) {
            Artist artistUpdate = artistExist.get();
            file.ifPresent(filep -> {
                try {
                    if(filep.getBytes().length > 100000){
                        IOException e = new IOException("Image to large");
                        throw e;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Image image = imageRepository.getReferenceById(UUID.fromString(entity.getImgProgile()));
                    try {
                        image.setDados(filep.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageRepository.save(image);
                    artistUpdate.setImgProgile(image.getId().toString());
                });
                
                if(entity.getName() != null && !entity.getName().isEmpty()){
                    artistUpdate.setName(entity.getName());
                }
                if(entity.getNationality() != null && !entity.getNationality().isEmpty()){
                    artistUpdate.setNationality(entity.getNationality());
                }
                
                if(entity.getSite() != null && !entity.getSite().isEmpty()){
                    artistUpdate.setSite(entity.getSite());
                }
                if (!isValidSite(entity.getSite())) {
                    IOException e = new IOException("Site is not valid");
                    throw e;
                } 
              
                repository.save(artistUpdate);
                return ResponseEntity.ok("Artist is updated!");
            } else {
                return ResponseEntity.notFound().build();
            }
       
    }

    public ResponseEntity<String> delete(UUID id){
        Artist artist = repository.getReferenceById(id);
        repository.deleteById(id);
        return ResponseEntity.ok("Artist is updated with id: "+ artist.getId());
    }

    private static boolean isValidSite(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        return responseCode >= 200 && responseCode < 300;
    }

}
