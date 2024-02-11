package com.musicapi.musicapi.controllers;



import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.musicapi.musicapi.services.AlbumService;
import com.musicapi.musicapi.services.MusicService;

public class MusicControllerTest {

    @InjectMocks
    private MusicController musicController;

    @Mock
    private MusicService musicService;

    @Mock
    private AlbumService albumService;

    @Test
    void testCreate() throws IOException {
        // MockitoAnnotations.openMocks(this);

        // when(albumService.getById(Mockito.any())).thenReturn(Mockito.mock(Album.class));
        // when(musicService.create(Mockito.any())).thenReturn(Mockito.mock(Music.class));

        // Music result = musicController.create(Mockito.mock(RequestMusicDto.class));

        // assertEquals(Mockito.mock(Music.class), result);
    }

    @Test
    void testGetById() {

    }

    @Test
    void testGetMusicByAlbum() {

    }

    @Test
    void testGetMusicByArtist() {
        // when(musicService.listMusicByArtist(Mockito.anyString(), 0, 10)).thenReturn(Mockito.mock(Page.class));

        // Page<Music> result = musicController.getMusicByArtist("artistId", 0, 10);

        // assertEquals(Mockito.mock(Page.class), result);
    }

    @Test
    void testListAll() {
        // when(musicService.listAll(0, 10)).thenReturn(Mockito.mock(Page.class));

        // Page<Music> result = musicController.listAll(0, 10);

        // assertEquals(Mockito.mock(Page.class), result);
    }

    @Test
    void testUpdate() {

    }

    @Test
    void testUpdate2() {

    }
}
