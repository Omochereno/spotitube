package datasource;

import datasource.util.ConnectionManager;
import datasource.util.FileLogger;
import datasource.util.IConnectionManager;
import domain.Playlist;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlaylistDAO implements IPlaylistDAO{

    private IConnectionManager connectionManager;
    private FileLogger logger;

    @Inject
    public PlaylistDAO(ConnectionManager connectionManager, FileLogger logger){
        this.connectionManager = connectionManager;
        this.logger = logger;
    }


    @Override
    public List<Playlist> findByOwner(String owner) {
        List<Playlist> playList = null;

        String query = "SELECT *" +
                "FROM Playlist P" +
                " WHERE owner lIKE ?";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, owner);
            playList = getPlaylistFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playList;
    }

    @Override
    public boolean deletePlaylist(int id) {
        String query = "DELETE FROM playlist WHERE idplaylist = ?";
        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean createPlaylist(Playlist playlist) {

        String query = "INSERT INTO playlist (name, owner) VALUES (?, ?)";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setString(2, "Testowner");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private List<Playlist> getPlaylistFromResultSet(ResultSet res) throws SQLException {
        List<Playlist> result = new ArrayList<>();

        while(res.next()){

            Playlist playlist = new Playlist();
            playlist.setId(res.getInt("idplaylist"));
            playlist.setName(res.getString("name"));
            //#TODO Set owner according to the current user, true if current owner is the user, false otherwise
            playlist.setOwner(true);
            result.add(playlist);

        }
        res.close();
        return result;
    }

    @Override
    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = null;
        String query = "SELECT * FROM playlist";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            playlists = getPlaylistFromResultSet(preparedStatement.executeQuery());
        } catch (SQLException e) {
            logger.log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }

        return playlists;
    }
}
