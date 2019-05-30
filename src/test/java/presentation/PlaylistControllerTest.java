package presentation;

import domain.JSON.Playlists;
import domain.Playlist;
import domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.ILoginService;
import service.IPlaylistService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlaylistControllerTest {

    @Mock
    IPlaylistService serviceMock;

    @InjectMocks
    PlaylistController sut;

    private String token;
    private int id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        token = "token";
        id = 1;
    }

    @Test
    void getPlaylistsCorrect() {
        Playlists playlists = getPlaylists();

        when(serviceMock.getPlaylists(token)).thenReturn(playlists);

        Response response = sut.getPlaylists(token);

        assertEquals(playlists, response.getEntity());
        verify(serviceMock,times(1)).getPlaylists(token);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetPlaylistTokenNull() {
        Response response = sut.getPlaylists(null);

        assertEquals(400, response.getStatus());
        assertEquals("No token supplied",response.getEntity());
    }


    @Test
    void testGetPlaylistNotFound() {
        Playlists playlists = getPlaylists();
        playlists.setPlaylists(null);
        when(serviceMock.getPlaylists(token)).thenReturn(playlists);

        Response response = sut.getPlaylists(token);

        assertEquals(404, response.getStatus());
        assertEquals("No playlists found", response.getEntity());
    }

    @Test
    void deletePlaylistCorrect() {
        when(serviceMock.deletePlaylist(id)).thenReturn(true);
        when(serviceMock.getPlaylists(token)).thenReturn(getPlaylists());

        Response response = sut.deletePlaylist(id, token);

        assertEquals(200, response.getStatus());
    }

    @Test
    void testDeletePlaylistDidntExist() {
        when(serviceMock.deletePlaylist(id)).thenReturn(false);

        Response response = sut.deletePlaylist(id, token);
        assertEquals(404, response.getStatus());
        assertEquals("Playlist didn't exist", response.getEntity());
    }

    @Test
    void createPlaylistCorrect() {
        Playlist playlist = getPlaylist1();
        when(serviceMock.createPlaylist(token, playlist)).thenReturn(true);
        when(serviceMock.getPlaylists(token)).thenReturn(getPlaylists());

        Response response = sut.createPlaylist(playlist, token);

        assertEquals(200, response.getStatus());
    }

    @Test
    void testCreatingPlaylistFail() {
        when(serviceMock.createPlaylist(token, getPlaylist1())).thenReturn(false);

        Response response = sut.createPlaylist(getPlaylist1(), token);

        assertEquals(404, response.getStatus());
        assertEquals("Something went wrong with adding a playlist", response.getEntity());
    }

    @Test
    void testEditPlaylistCorrect() {
        Playlist playlist = getPlaylist1();
        when(serviceMock.editPlaylist(playlist)).thenReturn(true);
        when(serviceMock.getPlaylists(token)).thenReturn(getPlaylists());

        Response response = sut.editPlaylist(token, id, playlist);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testEditPlaylistFail() {
        Playlist playlist = getPlaylist1();
        when(serviceMock.editPlaylist(playlist)).thenReturn(false);

        Response response = sut.editPlaylist(token, id, playlist);
        assertEquals(400, response.getStatus());
        assertEquals("Couldn't edit playlist", response.getEntity());
    }

    private Playlists getPlaylists() {
        Playlists playlists = new Playlists();
        playlists.setPlaylists(getPlaylistsDTO());
        playlists.setLength(12345);
        return playlists;
    }

    private List<Playlist> getPlaylistsDTO() {
        List<Playlist> playlist = new ArrayList<>();
        playlist.add(getPlaylist1());
        return playlist;
    }

    private Playlist getPlaylist1() {
        Playlist playlist = new Playlist();
        playlist.setId(1);
        playlist.setName("NAME");
        playlist.setOwner(true);
        playlist.setTracks(new ArrayList<>());
        return playlist;
    }
}