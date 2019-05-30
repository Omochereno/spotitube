package presentation;

import domain.JSON.Tracks;
import domain.Track;
import service.ITrackService;
import service.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class TrackController implements ITrackController{

    @Inject
    ITrackService trackService;

    @GET
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getTracksFromPlaylist(@PathParam("id") int id){
        return getTracks(id, true);
    }

    @GET
    @Path("tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response getTracksNotFromPlaylist(@QueryParam("forPlaylist") int id){
        return getTracks(id, false);
    }


    /**
     * Helper method for @GET tracks
     * @param id : playlist id
     * @param fromPlaylist : Are we looking for tracks from the given playlist or all the others
     * @return
     */
    private Response getTracks(int id, boolean fromPlaylist) {
        Tracks tracks = trackService.getTracks(id, fromPlaylist);

        if (tracks.getTracks() == null) {
            return Response.status(404).entity("No tracks found").build();
        }

        return Response.ok().entity(tracks).build();
    }

    @POST
    @Path("playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Override
    public Response addTrackToPlaylist(@PathParam("id") int playlistId, Track track){
        if(playlistId == 0 || track == null)
            return Response.status(400).entity("Please supply a playlist and track").build();
        if(trackService.addTrackToPlaylist(playlistId, track) != true){
            return Response.status(404).entity("Couldn't add track to playlist").build();
        }
        return getTracksFromPlaylist(playlistId);
    }

    @Override
    public Response deleteTrackFromPlaylist(int playlistId, int trackId) {
        if(playlistId == 0 || trackId == 0)
            return Response.status(400).entity("Please supply a playlist and track ID").build();

        if(trackService.deleteTrackFromPlaylist(playlistId, trackId) != true)
            return Response.status(400).entity("Couldn't delete track from playlist").build();

        return getTracks(playlistId, true);
    }
}
