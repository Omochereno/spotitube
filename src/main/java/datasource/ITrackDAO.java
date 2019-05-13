package datasource;

import domain.Track;

import java.sql.SQLException;
import java.util.List;

public interface ITrackDAO {

    List<Track> getTracksFromPlaylist(int id, boolean in);

    Track getTrackById(int id);
}
