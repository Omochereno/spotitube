package service;

import datasource.TrackDAO;
import domain.JSON.Tracks;
import domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrackServiceTest {

    @Mock
    TrackDAO trackMock;

    @InjectMocks
    TrackService sut;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getTracksCorrect() {
        int id = 1;
        boolean inplaylist = true;

        when(trackMock.getTracks(id, inplaylist)).thenReturn(getTracks().getTracks());
        Tracks result = sut.getTracks(id, true);

        verify(trackMock,times(1)).getTracks(id, inplaylist);
        assertEquals(result.getTracks().get(0).getId(), getTracks().getTracks().get(0).getId());
    }

    @Test
    void addTrackToPlaylist() {
        int playlistId = 1;
        Track track = getTracks().getTracks().get(0);

        when(trackMock.addTracktoPlaylist(playlistId, track)).thenReturn(true);

        boolean result = sut.addTrackToPlaylist(playlistId, track);

        assertTrue(result);
        verify(trackMock,times(1)).addTracktoPlaylist(playlistId, track);
    }

    @Test
    void deleteTrackFromPlaylist() {

        int playlistId = 1;
        int id = getTracks().getTracks().get(0).getId();
        when(trackMock.deleteTrackFromPlaylist(playlistId, id)).thenReturn(true);

        boolean result = sut.deleteTrackFromPlaylist(playlistId, id);

        assertTrue(result);
        verify(trackMock,times(1)).deleteTrackFromPlaylist(playlistId,id);
    }

    private Tracks getTracks(){
        Tracks tracks = new Tracks();
        List<Track> trackList = new ArrayList<>();
        Track track = new Track();
        track.setId(1);
        track.setTitle("test");
        track.setAlbum("Testalbum");
        track.setDuration(22);
        track.setPerformer("testperfomer");
        trackList.add(track);
        tracks.setTracks(trackList);
        return tracks;
    }
}