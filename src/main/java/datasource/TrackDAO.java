package datasource;

import datasource.util.FileLogger;
import datasource.util.IConnectionManager;
import domain.Track;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class TrackDAO implements ITrackDAO{

    private IConnectionManager connectionManager;

    @Inject
    public TrackDAO(IConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    /**
     * Helper method to get track objects from the resultset
     * @param res
     * @return
     * @throws SQLException
     */
    private List<Track> getTracksFromResultSet(ResultSet res) throws SQLException {
        List<Track> result = new ArrayList<>();

        while(res.next()){
            Track track = new Track();
            track.setId(res.getInt("idtrack"));
            track.setTitle(res.getString("title"));
            track.setPerformer(res.getString("performer"));
            track.setDuration(res.getInt("duration"));
            track.setAlbum(res.getString("album"));
            track.setPlaycount(res.getInt("playcount"));
            track.setPublicationDate(res.getString("publicationDate"));
            track.setDescription(res.getString("description"));
            track.setOfflineAvailable(res.getBoolean("offlineAvailable"));

            result.add(track);
        }
        res.close();
        return result;
    }

    @Override
    public List<Track> getTracks(int id, boolean inPlaylist) {
        List<Track> tracks = null;

        String query = null;
        if(!inPlaylist)
            query = "select * from track left outer join trackinplaylist on track.idtrack = trackinplaylist.idtrack where track.idtrack not in (select idtrack from trackinplaylist where idplaylist = ?)";
        else
            query = "select * from track inner join trackinplaylist on track.idtrack = trackinplaylist.idtrack where trackinplaylist.idplaylist = ?";

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            tracks = getTracksFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }
        return tracks;
    }

    @Override
    public boolean deleteTrackFromPlaylist(int playlistId, int trackId) {
        String query = "DELETE FROM trackinplaylist WHERE idtrack = ? AND idplaylist = ?";

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, trackId);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean addTracktoPlaylist(int playlistId, Track track) {
        String query = "INSERT INTO trackinplaylist (idtrack, idplaylist, offlineAvailable) VALUES (?, ?, ?)";

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, track.getId());
            preparedStatement.setInt(2, playlistId);
            preparedStatement.setBoolean(3, track.isOfflineAvailable());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }
}
