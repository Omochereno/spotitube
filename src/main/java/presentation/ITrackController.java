package presentation;

import domain.Track;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ITrackController {
    @GET
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTracksFromPlaylist(@PathParam("id") int id);

    @GET
    @Path("tracks")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTracksNotFromPlaylist(@QueryParam("forPlaylist") int id);

    @POST
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addTrackToPlaylist(@PathParam("id") int id, Track track);

    @DELETE
    @Path("playlists/{playlistId}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteTrackFromPlaylist(@PathParam("playlistId") int playlistId, @PathParam("trackId") int trackId);
}
