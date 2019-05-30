package service;

import datasource.LoginDAO;
import datasource.PlaylistDAO;
import datasource.TrackDAO;
import domain.JSON.Playlists;
import domain.JSON.UserToken;
import domain.Playlist;
import domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;

class PlaylistServiceTest {

    @Mock
    private TrackDAO trackMock;

    @Mock
    private PlaylistDAO playlistMock;

    @Mock
    private LoginDAO loginMock;

    @InjectMocks
    private PlaylistService sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getPlaylistsCorrect() {

        String token = "token";
        List<Playlist> playlists = getPlayLists();

        when(playlistMock.getPlaylists(token)).thenReturn(playlists);
        when(trackMock.getTracks(playlists.get(0).getId(), true)).thenReturn(getTracks());

        Playlists result = sut.getPlaylists(token);

        assertEquals(result.getLength(), 22);
        assertEquals(result.getPlaylists(), playlists);
    }

    @Test
    void deletePlaylist() {
        int id = 1;
        when(playlistMock.deletePlaylist(id)).thenReturn(true);
        boolean result = sut.deletePlaylist(id);
        verify(playlistMock, times(1)).deletePlaylist(id);
        assertEquals(result, true);
    }

    @Test
    void createPlaylist() {
        String user = getUsers().get(0).getUser();
        Playlist playlist = getPlayLists().get(0);

        when(loginMock.getAllUsers()).thenReturn(getUsers());
        when(playlistMock.createPlaylist(user, playlist)).thenReturn(true);

        boolean result = sut.createPlaylist(user, playlist);
        //assertTrue(result);

        verify(playlistMock, times(1)).createPlaylist(user, playlist);
        verify(loginMock,times(1)).getAllUsers();
    }

    private List<UserToken> getUsers() {
        List<UserToken> result = new ArrayList<>();
        UserToken token = new UserToken();
        token.setToken("token");
        token.setUser("user");
        result.add(token);
        return result;
    }

    @Test
    void editPlaylist() {
        Playlist playlist = getPlayLists().get(0);

        when(playlistMock.editPlaylist(playlist)).thenReturn(true);
        sut.editPlaylist(playlist);
        verify(playlistMock,times(1)).editPlaylist(playlist);
    }

    private List<Playlist> getPlayLists(){

        Playlist playlist = new Playlist();
        playlist.setId(1);
        playlist.setOwner(true);
        playlist.setName("testplaylist");
        playlist.setTracks(new ArrayList<>());
        List<Playlist> playlists1 = new ArrayList<>();
        playlists1.add(playlist);

        return playlists1;
    }

    private List<Track> getTracks(){
        List<Track> tracks = new ArrayList<>();
        Track track = new Track();
        track.setId(1);
        track.setTitle("test");
        track.setAlbum("Testalbum");
        track.setDuration(22);
        track.setPerformer("testperfomer");
        tracks.add(track);
        return tracks;
    }
}