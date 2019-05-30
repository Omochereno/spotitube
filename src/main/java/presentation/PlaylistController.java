package presentation;

import domain.JSON.Playlists;
import domain.Playlist;
import service.IPlaylistService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class PlaylistController implements IPlaylistController {

    @Inject
    IPlaylistService playlistService;

    @GET
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getPlaylists(@QueryParam("token") String token){
        if(token == null)
            return Response.status(400).entity("No token supplied").build();

        Playlists playlists = playlistService.getPlaylists(token);
        if(playlists.getPlaylists() == null)
            return Response.status(404).entity("No playlists found").build();

        return Response.ok().entity(playlists).build();
    }

    @DELETE
    @Path("playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        if(token == null)
            return Response.status(400).entity("No token supplied").build();

        if(playlistService.deletePlaylist(id) != true){
            return Response.status(404).entity("Playlist didn't exist").build();
        }
        return getPlaylists(token);
    }

    @POST
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response createPlaylist(Playlist playlist, @QueryParam("token") String token){
        if(token == null)
            return Response.status(400).entity("No token supplied").build();
        if(playlistService.createPlaylist(token, playlist) != true){
            return Response.status(404).entity("Something went wrong with adding a playlist").build();
        }
        return getPlaylists(token);
    }

    @Override
    public Response editPlaylist(String token, int id, Playlist playlist) {
        if(token == null)
            return Response.status(400).entity("No token supplied").build();
        if(playlist == null)
            return Response.status(404).entity("Playlist missing").build();
        if(!playlistService.editPlaylist(playlist))
            return Response.status(400).entity("Couldn't edit playlist").build();

        return getPlaylists(token);
    }
}
