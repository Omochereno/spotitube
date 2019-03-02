package datasource;

import datasource.util.ConnectionManager;
import datasource.util.FileLogger;
import datasource.util.IConnectionManager;
import domain.Song;
import domain.Track;
import domain.Video;

import javax.inject.Inject;
import javax.xml.transform.Result;
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


    @Override
    public List list() throws SQLException {
        return null;
    }

    @Override
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

    private List<Track> getTracksFromResultSet(ResultSet res) throws SQLException {
        List<Track> result = new ArrayList<>();

        while(res.next()){
            if(res.getString("album") == null || res.getString("album").isEmpty()){
                result.add(new Video(res.getString("performer"), res.getString("title"), res.getString("url"),

                        res.getInt("duration"), res.getInt("playcount"), res.getDate("publicationDate"), res.getString("description")));

            } else {
                result.add(new Song(res.getString("album"), res.getString("performer"), res.getString("title"), res.getString("url"), res.getInt("duration")));
            }
        }
        res.close();
        return result;
    }

}
