package service;

import datasource.IPlaylistDAO;
import datasource.ITrackDAO;
import datasource.TrackDAO;
import datasource.util.ConnectionManager;
import datasource.util.DatabaseProperties;
import datasource.util.FileLogger;
import domain.Playlist;
import domain.Track;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("/items")
@Produces("text/plain")
public class PlaylistService implements IPlaylistService{

    @Inject
    private ITrackDAO trackDAO;

    @Inject
    private IPlaylistDAO playlistDAO;

    @GET
    @Override
    public List<Playlist> getAllPlaylists(String owner) throws SQLException, ClassNotFoundException {
        return null;
    }

    @GET
    @Path("tracks/{searchTerm}")
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public List<Track> getAllTracks(String searchTerm) throws SQLException {
        List<Track> tracks = trackDAO.findByTitle(searchTerm);
        return tracks;
    }
}
