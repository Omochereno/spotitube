package service;

import domain.JSON.Tracks;
import domain.Track;

public interface ITrackService {
    Tracks getTracks(int id, boolean fromPlaylist);

    boolean addTrackToPlaylist(int playlistId, Track trackId);

    boolean deleteTrackFromPlaylist(int playlistId, int trackId);
}
