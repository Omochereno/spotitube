package presentation;

import domain.JSON.Tracks;
import domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import service.IPlaylistService;
import service.ITrackService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TrackControllerTest {

    @Mock
    ITrackService serviceMock;

    @InjectMocks
    TrackController sut;

    private int id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        id = 1;
    }
    @Test
    void testGetTracksFromPlaylistCorrect() {

        Tracks tracks = getTracks();
        when(serviceMock.getTracks(id, true)).thenReturn(tracks);

        Response response = sut.getTracksFromPlaylist(id);
        assertEquals(200, response.getStatus());
        assertEquals(tracks, response.getEntity());
    }

    @Test
    void getTracksNotFromPlaylistCorrect() {
        Tracks tracks = getTracks();
        when(serviceMock.getTracks(id, false)).thenReturn(tracks);

        Response response = sut.getTracksNotFromPlaylist(id);
        assertEquals(200, response.getStatus());
        assertEquals(tracks, response.getEntity());
    }

    @Test
    void testGetTracksInPlaylistNotFound() {
        Tracks tracks = getTracks();
        tracks.setTracks(null);
        when(serviceMock.getTracks(id, true)).thenReturn(tracks);

        Response response = sut.getTracksFromPlaylist(id);
        assertEquals(404, response.getStatus());
        assertEquals("No tracks found", response.getEntity());
    }

    @Test
    void testGetTracksNotInPlaylsitNotFount() {
        Tracks tracks = getTracks();
        tracks.setTracks(null);
        when(serviceMock.getTracks(id, false)).thenReturn(tracks);

        Response response = sut.getTracksNotFromPlaylist(id);
        assertEquals(404, response.getStatus());
        assertEquals("No tracks found", response.getEntity());
    }

    @Test
    void testAddTrackToPlaylistCorrect() {
        Tracks tracks = getTracks();
        when(serviceMock.getTracks(id, true)).thenReturn(tracks);
        Track track = getTrack();

        when(serviceMock.addTrackToPlaylist(id, track)).thenReturn(true);

        Response response = sut.addTrackToPlaylist(id ,track);

        assertEquals(200, response.getStatus());
        assertEquals(tracks, response.getEntity());
    }

    @Test
    void testAddTrackToPlaylistFail() {
        when(serviceMock.addTrackToPlaylist(id, getTrack())).thenReturn(false);

        Response response = sut.addTrackToPlaylist(id, getTrack());
        assertEquals(404, response.getStatus());
        assertEquals("Couldn't add track to playlist", response.getEntity());
    }

    @Test
    void testDeleteTrackFromPlaylistCorrect() {
        Tracks tracks = getTracks();
        when(serviceMock.getTracks(id, true)).thenReturn(tracks);
        when(serviceMock.deleteTrackFromPlaylist(id, id)).thenReturn(true);

        Response response = sut.deleteTrackFromPlaylist(id, id);

        assertEquals(200, response.getStatus());
    }

    @Test
    void testDeleteTrackFromPlaylistFail() {
        when(serviceMock.deleteTrackFromPlaylist(id, id)).thenReturn(false);

        Response response = sut.deleteTrackFromPlaylist(id, id);
        assertEquals(400, response.getStatus());
        assertEquals("Couldn't delete track from playlist", response.getEntity());
    }

    @Test
    void testDeleteWithoutSuppliedID() {
        Response response = sut.deleteTrackFromPlaylist(0, 0);

        assertEquals(400, response.getStatus());
        assertEquals("Please supply a playlist and track ID", response.getEntity());
    }

    private Tracks getTracks(){
        Tracks tracks = new Tracks();
        List<Track> trackList = new ArrayList<>();
        trackList.add(getTrack());
        tracks.setTracks(trackList);
        return tracks;
    }

    private Track getTrack(){
        Track track = new Track();
        track.setId(1);
        track.setTitle("test");
        track.setAlbum("Testalbum");
        track.setDuration(22);
        track.setPerformer("testperfomer");
        return track;
    }
}