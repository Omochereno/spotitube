package presentation;

import domain.Playlist;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface IPlaylistController {
    @GET
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPlaylists(@QueryParam("token") String token);

    @DELETE
    @Path("playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token);

    @POST
    @Path("playlists")
    @Produces(MediaType.APPLICATION_JSON)
    Response createPlaylist(Playlist playlist, @QueryParam("token") String token);

    @PUT
    @Path("playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response editPlaylist(@QueryParam("token") String token, @PathParam("id") int id, Playlist playlist);
}
