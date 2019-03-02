package service;

import domain.Playlist;
import domain.Track;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

public interface IPlaylistService {

    List<Playlist> getAllPlaylists(String owner) throws SQLException, ClassNotFoundException;

    @GET
    @Path("tracks/{searchTerm}")
    @Produces({MediaType.APPLICATION_JSON})
    List<Track> getAllTracks(@PathParam("searchTerm") String searchTerm) throws SQLException;

//     boolean updatePlaylist(Playlist playlist);
//
//    boolean removePlaylist(Playlist playlist);
//
//    String getAllTracksOnPlaylist(@PathParam("playlist") String playlist);
//
//    Playlist getPlaylistByName(@PathParam("name") String name) throws SQLException, ClassNotFoundException;
//
//    void savePlaylist(Playlist playlist) throws SQLException;
}
