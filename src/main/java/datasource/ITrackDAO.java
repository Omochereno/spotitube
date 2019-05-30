package datasource;

import domain.Track;

import java.util.List;

public interface ITrackDAO {

    List<Track> getTracks(int id, boolean inPlaylist);

    boolean deleteTrackFromPlaylist(int playlistId, int trackId);

    boolean addTracktoPlaylist(int playlistId, Track trackId);
}
