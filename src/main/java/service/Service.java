package service;

import com.sun.xml.internal.bind.v2.TODO;
import datasource.IPlaylistDAO;
import datasource.ITrackDAO;
import datasource.LoginDAO;
import domain.JSON.PlaylistJS;
import domain.JSON.TrackJS;
import domain.JSON.UserToken;
import domain.Playlist;
import domain.Track;
import domain.User;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class Service implements IService {

    @Inject
    private ITrackDAO trackDAO;

    @Inject
    private IPlaylistDAO playlistDAO;

    @Inject
    private LoginDAO loginDAO;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user){
        UserToken userToken = null;
        if(user == null || user.getUser() == null || user.getPassword() == null)
            return Response.status(400).entity("Please add user values!").build();

        if(loginDAO.isvalidUser(user)) {
            userToken = new UserToken();
            userToken.setUser(user.getUser());
            userToken.setToken("1234-1234-1234");
        } else {
            return Response.status(404).entity("User not found").build();
        }

        return Response.ok().entity(userToken).build();
    }

    PlaylistJS playlists;

    @GET
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(){
        playlists = new PlaylistJS();
        playlists.setPlaylists(playlistDAO.getPlaylists());
        //#TODO make length variable
        playlists.setLength(123445);

        if(playlists.getPlaylists() == null){
            return Response.status(404).entity("No playslists found").build();
        }
        return Response.ok().entity(playlists).build();
    }

    @DELETE
    @Path("playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id){

        if(playlistDAO.deletePlaylist(id) != true){
            return Response.status(404).entity("Playlist didn't exist").build();
        }

        return getPlaylists();
    }

    @POST
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPlaylist(Playlist playlist){

        if(playlistDAO.createPlaylist(playlist) != true){
            return Response.status(404).entity("Something went wrong with adding a playlist").build();
        }

        return getPlaylists();
    }

    @GET
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@PathParam("id") int id){
        return getResponse(id, true);
    }

    @GET
    @Path("tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksNotFromPlaylist(@QueryParam("forPlaylist") int id){
        return getResponse(id, false);
    }

    /**
     * Helper method for @GET tracks
     * @param id : playlist id
     * @param b : Are we looking for tracks from the given playlist or all the others
     * @return
     */
    private Response getResponse(int id, boolean b) {
        List<Track> tracks = trackDAO.getTracksFromPlaylist(id, b);

        if (tracks == null) {
            return Response.status(404).entity("No tracks found").build();
        }

        TrackJS trackJS = new TrackJS();
        trackJS.setTracks(tracks);

        return Response.ok().entity(trackJS).build();
    }

    @POST
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@PathParam("id") int id){
        // #TODO IN PROGRESS
        Track track = trackDAO.getTrackById(id);

        if(track == null){
            return Response.status(404).entity("No track found").build();
        }


        return Response.ok().build();
    }

}
