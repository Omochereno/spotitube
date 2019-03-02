package datasource;

import datasource.util.ConnectionManager;
import datasource.util.FileLogger;
import datasource.util.IConnectionManager;
import domain.Playlist;
import domain.Video;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlaylistDAO implements IPlaylistDAO{


    private IConnectionManager connectionManager;
    private FileLogger logger;

    @Inject
    public PlaylistDAO(ConnectionManager connectionManager, FileLogger logger){
        this.connectionManager = connectionManager;
        this.logger = logger;
    }

    @Override
    public ArrayList<Playlist> list() {
        return null;
    }

    @Override
    public ArrayList<Playlist> findByOwner(String owner) {
        ArrayList<Playlist> playList = null;

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
    public ArrayList<Playlist> findByName(String name) {
        return null;
    }

    private ArrayList<Playlist> getPlaylistFromResultSet(ResultSet res) throws SQLException {
        List<Playlist> result = new ArrayList<>();

        while(res.next()){

        }
        res.close();
        return null;
    }

    @Override
    public void save(Playlist playlist) {

    }

    @Override
    public void update(Playlist playlist) {

    }

    @Override
    public void delete(int playListId) {

    }
}
