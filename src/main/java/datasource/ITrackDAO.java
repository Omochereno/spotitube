package datasource;

import domain.Track;

import java.sql.SQLException;
import java.util.List;

public interface ITrackDAO {

    List<Track> list() throws SQLException;

    List<Track> findByTitle(String title) throws SQLException;
}
