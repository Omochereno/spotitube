package service;

import datasource.TrackDAO;
import domain.JSON.Tracks;
import domain.Track;

import javax.inject.Inject;

public class TrackService implements ITrackService {

    @Inject
    TrackDAO trackDAO;

    @Override
    public Tracks getTracks(int id, boolean fromPlaylist) {
        Tracks tracks = new Tracks();
        tracks.setTracks(trackDAO.getTracks(id,fromPlaylist));
        return tracks;
    }

    @Override
    public boolean addTrackToPlaylist(int playlistId, Track track) {
        return trackDAO.addTracktoPlaylist(playlistId, track);
    }

    @Override
    public boolean deleteTrackFromPlaylist(int playlistId, int trackId) {
        return trackDAO.deleteTrackFromPlaylist(playlistId, trackId);
    }
}
