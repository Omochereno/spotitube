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
    private FileLogger logger;

    @Inject
    public TrackDAO(IConnectionManager connectionManager, FileLogger logger){
        this.connectionManager = connectionManager;
        this.logger = logger;
    }

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

    public List<Track> findByTitle(String title) throws SQLException {
        List<Track> tracks = null;

        String query = "SELECT * FROM track " +
                "WHERE title LIKE ?";

        try (Connection connection = connectionManager.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, title);
            tracks = getTracksFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }
        return tracks;
    }

    @Override
    public List<Track> getTracksFromPlaylist(int id, boolean in) {
        List<Track> tracks = null;

        String query = null;
        if(in)
            query = "SELECT * FROM track WHERE idtrack IN (SELECT idtrack FROM trackinplaylist where idplaylist = ?)";
        else
            query = "SELECT * FROM track WHERE idtrack NOT IN (SELECT idtrack FROM trackinplaylist where idplaylist = ?)";

        try (Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            tracks = getTracksFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }
        return tracks;
    }

    @Override
    public Track getTrackById(int id) {
        String query = "SELECT * FROM track where idtrack = ?";

        List<Track> track = null;
        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            track = getTracksFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return track.get(0);
    }
}
