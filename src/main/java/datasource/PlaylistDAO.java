package datasource;

import datasource.util.ConnectionManager;
import datasource.util.FileLogger;
import datasource.util.IConnectionManager;
import datasource.util.UtilManager;
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

    @Inject
    public PlaylistDAO(ConnectionManager connectionManager){
        this.connectionManager = connectionManager;
    }

    @Override
    public boolean deletePlaylist(int id) {
        String query = "DELETE FROM playlist WHERE idplaylist = ?";
        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean createPlaylist(String owner, Playlist playlist) {

        String query = "INSERT INTO playlist (name, owner) VALUES (?, ?)";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setString(2, owner);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }


    private List<Playlist> getPlaylistFromResultSet(ResultSet res, String token) throws SQLException {
        List<Playlist> result = new ArrayList<>();

        while(res.next()){

            Playlist playlist = new Playlist();
            playlist.setId(res.getInt("idplaylist"));
            playlist.setName(res.getString("name"));
            String ownerToken = UtilManager.generateToken(res.getString("owner"));
            playlist.setOwner(token.equals(ownerToken)? true : false);
            result.add(playlist);
        }
        res.close();
        return result;
    }

    @Override
    public List<Playlist> getPlaylists(String token) {
        List<Playlist> playlists = null;
        String query = "SELECT * FROM playlist";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            playlists = getPlaylistFromResultSet(preparedStatement.executeQuery(), token);
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, "Query error: " + e.getMessage());
        }

        return playlists;
    }

    @Override
    public boolean editPlaylist(Playlist playlist) {
        String query = "UPDATE playlist SET name = ? WHERE idplaylist = ?";

        try(Connection connection = connectionManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            FileLogger.getInstance().log(getClass().getName(), Level.SEVERE, e.getMessage());
            return false;
        }
        return true;
    }
}
