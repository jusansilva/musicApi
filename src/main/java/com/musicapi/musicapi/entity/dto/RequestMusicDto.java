package com.musicapi.musicapi.entity.dto;

import java.util.Optional;

public class RequestMusicDto {
   private Optional<String>  title; 
   private Optional<String>  time;
   private Optional<String>  albumId;
   private Optional<Integer>  track;

   public RequestMusicDto(){

   }

   public RequestMusicDto(String title, String time, String albumId, int track){
      this.title = Optional.ofNullable(title);
      this.time = Optional.ofNullable(time);
      this.albumId = Optional.ofNullable(albumId);
      this.track = Optional.ofNullable(track);
   }

   public Optional<String> getTitle() {
      return title;
   }

   public void setTitle(Optional<String> title) {
      this.title = title;
   }

   public Optional<String> getTime() {
      return time;
   }

   public void setTime(Optional<String> time) {
      this.time = time;
   }

   public Optional<String> getAlbumId() {
      return albumId;
   }

   public void setAlbumId(Optional<String> albumId) {
      this.albumId = albumId;
   }

   public Optional<Integer> getTrack() {
      return track;
   }

   public void setTrack(Optional<Integer> track) {
      this.track = track;
   }

   
}
